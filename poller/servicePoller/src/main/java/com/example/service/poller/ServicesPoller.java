package com.example.service.poller;

import com.example.service.poller.db.Client;
import com.example.service.poller.model.ServiceJob;
import com.example.service.poller.model.ServiceResult;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.ext.web.codec.BodyCodec;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ServicesPoller {

  private final Integer INTERVAL =  30 * 1000; // 30 seconds
  private final String SERVICE_POLLER_AGENT = "service-poller/0.0.1";
  private final Integer SERVICE_REQUEST_TIMEOUT = 2 * 1000; // 2 seconds
  private final Integer DEFAULT_PORT = 80;

  private Vertx vertx;
  private Client dbClient;
  private WebClient webClient;

  public ServicesPoller(Vertx vertx, Client dbClient) {
    this.vertx = vertx;
    this.dbClient = dbClient;

    this.webClient = WebClient.create(vertx, new WebClientOptions()
      .setUserAgent(SERVICE_POLLER_AGENT)
      .setConnectTimeout(SERVICE_REQUEST_TIMEOUT)
      .setKeepAlive(false)
    );
  }

  private Future<ServiceResult> getServiceStatus(ServiceJob job) {
    Promise<ServiceResult> promise = Promise.promise();

    // constructing the URL instance to get information from the provided url
    // returning early if the construction fails
    URL url;
    try {
      url = new URL(job.getServiceUrl());
    } catch (MalformedURLException e) {
      promise.fail("Invalid url");
      return promise.future();
    }

    Integer port = url.getPort() < 0 ? DEFAULT_PORT : url.getPort();

    HttpRequest<JsonObject> request = this.webClient
      .get(port, url.getHost(), url.getPath())
      .as(BodyCodec.jsonObject());

    request.send()
      .onSuccess(response -> {
        promise.complete(ServiceResult.create(job.getServiceId(),response.statusCode()));
      })
      .onFailure(err -> {
        promise.fail("Failure during the status request");
      });

    return promise.future();
  }

  public void start() {

    // forever executing process
    // Using observable to handle this function might have been better!
    this.vertx.setPeriodic(INTERVAL, id -> {
      this.dbClient.getServiceJobs().onComplete(ar -> {
        List<ServiceJob> services = ar.result();
        for (ServiceJob job : services) {
          getServiceStatus(job)
            .onSuccess(result -> {
              this.dbClient.updateServiceStatus(result);
            })
            // silently failing. Logging will give insights about failures.
            .onFailure(fail -> {});
        }
      }).onFailure(ar -> {
      });
    });

  }
}
