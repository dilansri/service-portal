package com.example.service.portal.graphql;

import com.example.service.portal.graphql.fetchers.*;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import io.vertx.core.Vertx;

public class GraphQLServer {

  private GraphQLSchema schema;
  private Vertx vertx;

  public GraphQLServer(Vertx vertx) {
    this.vertx = vertx;
    this.schema = this.setupGraphQLServer();
  }

  // this methods setups the graphql schema and its operations with runtime wiring
  // more info here https://vertx.io/docs/vertx-web-graphql/java/
  // Using static Fetcher.create methods since the schema is relatively small
  // Fetcher concept is used to abstractly resolve data the client needs
  private GraphQLSchema setupGraphQLServer() {
    final String GRAPHQL_SCHEMA_FILE = "services.graphqls";
    final String QUERY_TYPE = "Query";
    final String MUTATION_TYPE = "Mutation";

    String schema = this.vertx.fileSystem().readFileBlocking(GRAPHQL_SCHEMA_FILE).toString();

    SchemaParser schemaParser = new SchemaParser();

    TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

    RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
      .type(QUERY_TYPE, builder -> builder
        .dataFetcher("services", ServicesFetcher.create())
      )
      .type(QUERY_TYPE, builder -> builder
        .dataFetcher("service", ServiceFetcher.create())
      )
      .type(MUTATION_TYPE, builder -> builder
        .dataFetcher("createService", CreateServiceFetcher.create())
      )
      .type(MUTATION_TYPE, builder -> builder
        .dataFetcher("updateService", UpdateServiceFetcher.create())
      )
      .type(MUTATION_TYPE, builder -> builder
        .dataFetcher("deleteService", DeleteServiceFetcher.create())
      )
      .build();

    SchemaGenerator schemaGenerator = new SchemaGenerator();
    return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
  }

  public GraphQLSchema getGraphQLSchema() {
    return this.schema;
  }
}
