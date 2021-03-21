package com.example.service.portal.graphql.fetchers;

import com.example.service.portal.graphql.GraphQLContext;
import com.example.service.portal.model.Service;
import io.vertx.ext.web.handler.graphql.schema.VertxDataFetcher;

import java.util.List;

/**
 * Fetcher to get all service instances
 * Does not support the pagination for this example
 */
public class ServicesFetcher {

  public static VertxDataFetcher<List<Service>> create() {
     return VertxDataFetcher.create(env -> {
       GraphQLContext context = env.getContext();

       return context.dbClient.getServices();
     });
  }

}
