package com.example.service.portal.graphql.fetchers;

import com.example.service.portal.graphql.GraphQLContext;
import com.example.service.portal.input.ServiceInput;
import com.example.service.portal.model.Service;
import io.vertx.ext.web.handler.graphql.schema.VertxDataFetcher;

import java.util.LinkedHashMap;

/**
 * Fetcher to create a service instance and store it.
 */
public class CreateServiceFetcher {

  public static VertxDataFetcher<Service> create() {
    return VertxDataFetcher.create(env -> {
      LinkedHashMap<String, String> serviceInput = env.getArgument("service");
      GraphQLContext context = env.getContext();

      return context.dbClient.createService(new ServiceInput(
        serviceInput.get("name"),
        serviceInput.get("url"),
        serviceInput.get("description"))
      );
    });
  }
}
