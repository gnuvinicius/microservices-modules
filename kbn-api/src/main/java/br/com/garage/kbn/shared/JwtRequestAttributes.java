package br.com.garage.kbn.shared;

import java.io.Serializable;

public record JwtRequestAttributes(String tenantId, String userId) implements Serializable {

}
