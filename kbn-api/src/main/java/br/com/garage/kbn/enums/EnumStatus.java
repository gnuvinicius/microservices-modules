package br.com.garage.kbn.enums;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public enum EnumStatus {

	INATIVO(0), ATIVO(1);

	@Getter
	private int value;

	EnumStatus(int value) {
		this.value = value;
	}

	public EnumStatus valueOf(int value) {
		Map<Integer, EnumStatus> map = new HashMap<>();

		for (EnumStatus status : EnumStatus.values()) {
			map.put(status.value, status);
		}

		return map.get(value);
	}

}
