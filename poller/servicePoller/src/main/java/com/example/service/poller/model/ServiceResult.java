package com.example.service.poller.model;

import java.util.UUID;

/**
 * Represents the result received from the ServiceJob completion
 */
public class ServiceResult {
  private UUID serviceId;
  private Integer serviceStatusCode;

  private ServiceResult() {}

  public UUID getServiceId() {
    return serviceId;
  }

  private ServiceResult setServiceId(UUID serviceId) {
    this.serviceId = serviceId;
    return this;
  }

  public Integer getServiceStatusCode() {
    return serviceStatusCode;
  }

  private ServiceResult setServiceStatusCode(Integer serviceStatusCode) {
    this.serviceStatusCode = serviceStatusCode;
    return this;
  }

  public static ServiceResult create(UUID id, Integer result) {
    return new ServiceResult().setServiceId(id).setServiceStatusCode(result);
  }

}
