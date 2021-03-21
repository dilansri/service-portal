package com.example.service.poller.model;

import io.vertx.sqlclient.Row;

import java.util.UUID;

/**
 * Represents a scheduled job for a given service entry
 * Uses static create method to instantiate the instances
 */
public class ServiceJob {

  private UUID serviceId;
  private String serviceUrl;

  private ServiceJob(){}

  public UUID getServiceId() {
    return serviceId;
  }

  private ServiceJob setServiceId(UUID serviceId) {
    this.serviceId = serviceId;
    return this;
  }

  public String getServiceUrl() {
    return serviceUrl;
  }

  private ServiceJob setServiceUrl(String serviceUrl) {
    this.serviceUrl = serviceUrl;
    return this;
  }

  public static ServiceJob create(Row row) {
    return new ServiceJob()
      .setServiceId(row.getUUID("id"))
      .setServiceUrl(row.getString("url"));
  }
}
