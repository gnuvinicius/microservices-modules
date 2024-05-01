package br.com.garage.auth.infraestructure.api.auth.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class TokenDto {

	@JsonProperty(value = "access_token")
	private String accessToken;
	private String type;
	private String tenantName;

	public TokenDto(String accessToken, String type, String tenantName) {
		super();
		this.accessToken = accessToken;
		this.type = type;
		this.tenantName = tenantName;
	}

	public void atualizaNomeEmpresa(String nome) {
		this.tenantName = nome;
	}
}
