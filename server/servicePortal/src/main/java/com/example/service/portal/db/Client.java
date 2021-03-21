package com.example.service.portal.db;

import com.example.service.portal.input.ServiceInput;
import com.example.service.portal.model.Service;
import io.vertx.core.Future;

import java.util.List;
import java.util.UUID;


/**
 * Interface for the database Client
 * to support being agnostic from the specific database vendor
 */
public interface Client {

  Future<Service> getService(UUID id);
  Future<List<Service>> getServices();
  Future<Service> createService(ServiceInput serviceInput);
  Future<Service> updateService(UUID id, ServiceInput serviceInput);
  Future<Boolean> deleteService(UUID id);

}
