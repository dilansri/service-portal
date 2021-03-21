package com.example.service.portal.graphql.fetchers;

import com.example.service.portal.graphql.GraphQLContext;
import io.vertx.ext.web.handler.graphql.schema.VertxDataFetcher;

import java.util.UUID;

/**
 * Fetcher to delete an instance of Service
 */
public class DeleteServiceFetcher {

  public static VertxDataFetcher<Boolean> create() {
    return VertxDataFetcher.create(env -> {
      String id = env.getArgument("id");
      GraphQLContext context = env.getContext();

      return context.dbClient.deleteService(UUID.fromString(id));
    });
  }
}
