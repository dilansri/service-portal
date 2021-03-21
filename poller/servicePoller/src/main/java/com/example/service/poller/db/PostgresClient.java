package com.example.service.poller.db;

import com.example.service.poller.model.ServiceJob;
import com.example.service.poller.model.ServiceResult;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Postgres specific implementation of the database Client
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

  public Future<List<ServiceJob>> getServiceJobs() {
    Promise<List<ServiceJob>> servicesPromise = Promise.promise();
    client
      .preparedQuery("SELECT id, url FROM service")
      .execute(ar -> {
        if (ar.failed()) {
          servicesPromise.fail("Failed to query the service table");
          return;
        }

        RowSet<Row> results = ar.result();
        List<ServiceJob> servicesResult = new ArrayList<>();

        for (Row row : results) {
          servicesResult.add(ServiceJob.create(row));
        }

        servicesPromise.complete(servicesResult);

      });
    return servicesPromise.future();
  }

  public void updateServiceStatus(ServiceResult result) {
    // Updating the service result in both service, and service_healthcheck_log tables in a transaction to have consistent state
    client.getConnection().onSuccess(conn -> {
      conn.begin().compose(tx ->
        conn.preparedQuery("UPDATE service SET status_code=$1, updated_at=NOW() WHERE id=$2")
          .execute(Tuple.of(result.getServiceStatusCode(), result.getServiceId()))
          .compose(res2 -> conn
            .preparedQuery("INSERT INTO service_healthcheck_log VALUES($1,$2)")
            .execute(Tuple.of(result.getServiceId(), result.getServiceStatusCode())))
          .compose(res3 -> tx.commit())
      ).eventually(v -> conn.close());
    });
  }
}
