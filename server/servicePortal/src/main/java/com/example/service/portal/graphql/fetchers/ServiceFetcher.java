package com.example.service.portal.graphql.fetchers;

import com.example.service.portal.graphql.GraphQLContext;
import com.example.service.portal.model.Service;
import io.vertx.ext.web.handler.graphql.schema.VertxDataFetcher;

import java.util.UUID;

/**
 * Fetcher to get a single Service instance
 */
public class ServiceFetcher {

  public static VertxDataFetcher<Service> create() {
    return VertxDataFetcher.create(env -> {
      String id = env.getArgument("id");
      GraphQLContext context = env.getContext();

      return context.dbClient.getService(UUID.fromString(id));
    });
  }
}
