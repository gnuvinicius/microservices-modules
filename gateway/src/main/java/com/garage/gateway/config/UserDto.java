package com.garage.gateway.config;

import java.util.UUID;

public class UserDto {

  private UUID id;
  private String login;
  private String token;

  public UUID getId() {
    return this.id;
  }

  public String getLogin() {
    return this.login;
  }

  public String getToken() {
    return this.token;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public void setToken(String token) {
    this.token = token;
  }

}
