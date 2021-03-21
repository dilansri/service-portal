package com.example.service.portal.input;

/**
 * Represents a client input for creating and updating a service
 * Validations and mitigating the incoming risks can be done here.
 */
public class ServiceInput {

  private String name;
  private String url;
  private String description;

  public ServiceInput(String name, String url, String description) {
    this.name = name;
    this.url = url;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }

  public String getDescription() {
    return description;
  }
}
