package com.example.service.portal.model;

import io.vertx.sqlclient.Row;

import java.util.UUID;

/**
 * Serves as a representation of Service instance.
 * static create method is added  here since this example does not have an ORM type capabilities.
 *
 * only get methods are invoked by the graphQL field  resolvers
 */
public class Service {

  private UUID id;
  private String name;
  private String url;
  private String description;
  private Integer statusCode;
  private String updatedAt;

  private Service() {
  }

  public UUID getId() {
    return this.id;
  }

  private Service setId(UUID id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  private Service setName(String name) {
    this.name = name;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public Service setUrl(String url) {
    this.url = url;
    return this;
  }

  public String getDescription() {
    return description;
  }

  private Service setDescription(String description) {
    this.description = description;
    return this;
  }


  public Integer getStatusCode() {
    return statusCode;
  }

  private Service setStatusCode(Integer statusCode) {
    this.statusCode = statusCode;
    return this;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  private Service setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  public static Service create(Row tableRow) {
    return new Service().setId(tableRow.getUUID("id"))
      .setName(tableRow.getString("name"))
      .setUrl(tableRow.getString("url"))
      .setDescription(tableRow.getString("description"))
      .setStatusCode(tableRow.getInteger("status_code"))
      .setUpdatedAt(tableRow.getOffsetDateTime("updated_at").toString());
  }

}
