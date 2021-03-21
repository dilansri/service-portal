package com.example.service.portal;

import com.example.service.portal.graphql.GraphQLContext;
import com.example.service.portal.graphql.GraphQLServer;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.graphql.GraphQLHandler;

public class MainVerticle extends AbstractVerticle {

  private GraphQL setupGraphql() {
    GraphQLServer server = new GraphQLServer(vertx);
    GraphQLSchema schema = server.getGraphQLSchema();
    return GraphQL.newGraphQL(schema).build();
  }

  private void startServer(Promise<Void> startPromise, JsonObject options) {
    final String CONTENT_TYPE_HEADER = "Content-Type";
    final String GRAPHQL_ENDPOINT_PATH = "/graphql";

    Router router = Router.router(vertx);

    // adding the CORS handler to simplifying the running of all services in one host
    // for the example purposes
    router.route().handler(CorsHandler.create("*")
      .allowedMethod(HttpMethod.GET)
      .allowedMethod(HttpMethod.POST)
      .allowedHeader(CONTENT_TYPE_HEADER));

    router.route().handler(BodyHandler.create());

    router.route(GRAPHQL_ENDPOINT_PATH).handler(GraphQLHandler.create(setupGraphql())
      .queryContext(routingContext -> {
        GraphQLContext context = new GraphQLContext(vertx, options);
        return context;
      }));

    vertx.createHttpServer().requestHandler(router).listen(8888).onFailure(ar -> {
      startPromise.fail("Fail to start the server");
    });
  }

  @Override
  public void start(Promise<Void> startPromise) {

    // getting the configs from the system environment variables
    ConfigStoreOptions envStoreOptions = new ConfigStoreOptions()
      .setType("env");

    ConfigRetrieverOptions retrieverOptions = new ConfigRetrieverOptions()
      .addStore(envStoreOptions);

    ConfigRetriever retriever = ConfigRetriever.create(vertx, retrieverOptions);
    retriever.getConfig(ar -> {
      if (ar.failed()) {
        startPromise.fail("Failed to fetch configs");
        return;
      }
      JsonObject options = ar.result();
      startServer(startPromise, options);
    });

  }
}
