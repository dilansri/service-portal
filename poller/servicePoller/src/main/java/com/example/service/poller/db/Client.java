package com.example.service.poller.db;

import com.example.service.poller.model.ServiceJob;
import com.example.service.poller.model.ServiceResult;
import io.vertx.core.Future;

import java.util.List;

/**
 * Represents the interface of a database client
 * getServiceJobs() Retrieve needed ServiceJob instances for a given time
 * updateServiceStatus(ServiceResult result) update the successful ServiceJob result
 */
public interface Client {
  Future<List<ServiceJob>> getServiceJobs();
  void updateServiceStatus(ServiceResult result);
}
