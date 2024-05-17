package br.com.garage.auth.infraestructure.api.auth.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;


public class TokenDto {

	public @JsonProperty(value = "access_token") String accessToken;
	public String type;
	public String tenantName;

	public TokenDto(String accessToken, String type, String tenantName) {
		this.accessToken = accessToken;
		this.type = type;
		this.tenantName = tenantName;
	}
}
