package com.example.service.portal.db;

import com.example.service.portal.input.ServiceInput;
import com.example.service.portal.model.Service;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Postgres specific database client
 * Connection is created with the system's environment variables
 * Maintains a limit of max pool (connections) so the db does not get run out of connections
 */

public class PostgresClient implements Client {

  private PgPool client;

  public PostgresClient(Vertx vertx, JsonObject options) {
    PgConnectOptions connectOptions = new PgConnectOptions()
      .setPort(options.getInteger("DB_PORT"))
      .setHost(options.getString("DB_HOST"))
      .setDatabase(options.getString("DB_DATABASE"))
      .setUser(options.getString("DB_USER"))
      .setPassword(options.getString("DB_PASSWORD"));

    PoolOptions poolOptions = new PoolOptions()
      .setMaxSize(options.getInteger("DB_POLL_SIZE"));

    this.client = PgPool.pool(vertx, connectOptions, poolOptions);
  }

  public Future<Service> getService(UUID id) {
    Promise<Service> servicePromise = Promise.promise();
    client
      // Retrieving all fields with * can be avoided. Keeping for simplicity.
      .preparedQuery("SELECT * FROM service WHERE id=$1")
      .execute(Tuple.of(id), ar -> {
        if (ar.failed()) {
          servicePromise.fail("Failed to get the service record");
          return;
        }

        RowSet<Row> rows = ar.result();

        if (rows.size() > 0) {
          servicePromise.complete(Service.create(rows.iterator().next()));
          return;
        }

        servicePromise.fail("Service was not found");

      });
    return servicePromise.future();
  }

  public Future<List<Service>> getServices() {
    Promise<List<Service>> servicesPromise = Promise.promise();
    client
      .preparedQuery("SELECT * FROM service")
      .execute(ar -> {
        if (ar.failed()) {
          servicesPromise.fail("Faaileeeed");
          return;
        }

        RowSet<Row> result = ar.result();
        List<Service> servicesResult = new ArrayList<>();

        for (Row row : result) {
          servicesResult.add(Service.create(row));
        }
        servicesPromise.complete(servicesResult);

      });
    return servicesPromise.future();
  }

  public Future<Service> createService(ServiceInput input) {
    Promise<Service> servicePromise = Promise.promise();

    // simple validation for the url.
    // Should be properly implemented either with a graphql constraint
    // or with a well defined Validator.
    try{
      new URL(input.getUrl());
    }catch(MalformedURLException e )  {
      servicePromise.fail("Invalid url");
      return servicePromise.future();
    }

    client
      .preparedQuery("INSERT INTO service (name, url, description) " +
        "VALUES ($1, $2, $3) " +
        "RETURNING *")
      .execute(Tuple.of(input.getName(), input.getUrl(), input.getDescription()), ar -> {
        if (ar.succeeded()) {
          RowSet<Row> rows = ar.result();

          if (rows.size() > 0) {
            servicePromise.complete(Service.create(rows.iterator().next()));
            return;
          }
          servicePromise.fail("Service was not inserted correctly");
          return;
        }
        servicePromise.fail("Failed to execute the insert statement");
      });
    return servicePromise.future();
  }

  public Future<Service> updateService(UUID id, ServiceInput input) {
    Promise<Service> updatePromise = Promise.promise();
    client
      .preparedQuery("UPDATE service SET name=$1, url=$2, description=$3 WHERE id=$4 " +
        "RETURNING *")
      .execute(Tuple.of(input.getName(), input.getUrl(), input.getDescription(), id), ar -> {
        if (ar.succeeded()) {
          RowSet<Row> rows = ar.result();

          if (rows.size() > 0) {
            updatePromise.complete(Service.create(rows.iterator().next()));
            return;
          }
          updatePromise.fail("Service was not updated correctly");
          return;
        }
        updatePromise.fail("Failed to execute the update statement");
      });
    return updatePromise.future();
  }

  public Future<Boolean> deleteService(UUID id){
    Promise<Boolean> deletePromise = Promise.promise();
    client
      .preparedQuery("DELETE FROM service WHERE id=$1 ")
      .execute(Tuple.of(id), ar -> {
        if (ar.succeeded()) {
          deletePromise.complete(true);
          return;
        }
        deletePromise.fail("Failed to execute the delete statement");
      });
    return deletePromise.future();
  }
}
