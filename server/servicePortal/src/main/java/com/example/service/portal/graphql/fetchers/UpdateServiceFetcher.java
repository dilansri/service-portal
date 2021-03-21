package com.example.service.portal.graphql.fetchers;

import com.example.service.portal.graphql.GraphQLContext;
import com.example.service.portal.input.ServiceInput;
import com.example.service.portal.model.Service;
import io.vertx.ext.web.handler.graphql.schema.VertxDataFetcher;

import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * Fetcher to update a service instance
 * The client needs to supply all three name, url, description fields
 */
public class UpdateServiceFetcher {

  public static VertxDataFetcher<Service> create() {
    return VertxDataFetcher.create(env -> {

      // updated service fields sent from the client
      // validations for the fields needs to be performed in a production environment
      LinkedHashMap<String, String> serviceInput = env.getArgument("service");

      String id = env.getArgument("id");
      GraphQLContext context = env.getContext();

      return context.dbClient.updateService(
        UUID.fromString(id),
        new ServiceInput(
          serviceInput.get("name"),
          serviceInput.get("url"),
          serviceInput.get("description")
        )
      );
    });
  }
}
