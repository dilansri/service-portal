package com.example.service.portal.graphql;

import com.example.service.portal.db.Client;
import com.example.service.portal.db.PostgresClient;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * This class Represents the GraphQL context using for Fetchers.
 * Here we initialize the dbClient once so that it does not get recreated for each request
 */
public class GraphQLContext {

  public Vertx vertx;
  public Client dbClient;

  public GraphQLContext(Vertx vertx, JsonObject options) {
    this.vertx = vertx;
    this.dbClient = new PostgresClient(vertx, options);
  }
}
