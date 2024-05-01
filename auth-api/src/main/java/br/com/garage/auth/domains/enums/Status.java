package br.com.garage.auth.domains.enums;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public enum Status {

	INATIVO(0), ATIVO(1);

	@Getter
	private int value;

	Status(int value) {
		this.value = value;
	}

	public Status valueOf(int value) {
		Map<Integer, Status> map = new HashMap<>();

		for (Status status : Status.values()) {
			map.put(status.value, status);
		}

		return map.get(value);
	}

}
