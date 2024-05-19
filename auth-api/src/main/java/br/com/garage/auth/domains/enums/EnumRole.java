package br.com.garage.auth.domains.enums;

import lombok.Getter;

@Getter
public enum EnumRole {

	ROLE_ROOT("ROLE_ROOT"),
	ROLE_ADMIN("ROLE_ADMIN"),
	ROLE_SALE("ROLE_SALE"),
	ROLE_DASHBOARD("ROLE_DASHBOARD"),
	ROLE_CADASTRO("ROLE_CADASTRO");

	private final String value;

	EnumRole(String value) {
		this.value = value;
	}

}
