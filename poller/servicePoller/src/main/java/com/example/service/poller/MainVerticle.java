package com.example.service.poller;

import com.example.service.poller.db.Client;
import com.example.service.poller.db.PostgresClient;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) {

    // fetching configs from system environment variables
    ConfigStoreOptions envStoreOptions = new ConfigStoreOptions()
      .setType("env");

    ConfigRetrieverOptions retrieverOptions = new ConfigRetrieverOptions()
      .addStore(envStoreOptions);

    ConfigRetriever retriever = ConfigRetriever.create(vertx, retrieverOptions);

    retriever.getConfig()
      .onComplete(ar -> {
        JsonObject options = ar.result();
        Client dbClient = new PostgresClient(vertx, options);
        new ServicesPoller(vertx, dbClient).start();
      })
      .onFailure(ar -> {
        startPromise.fail("Failed to fetch configs");
      });
  }
}
