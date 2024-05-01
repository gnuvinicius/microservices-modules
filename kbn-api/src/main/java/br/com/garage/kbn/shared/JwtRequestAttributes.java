package br.com.garage.kbn.shared;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class JwtRequestAttributes implements Serializable {
   
  private String tenantId;
  private String userId;

  public JwtRequestAttributes(String tenantId, String userId) {
    this.tenantId = tenantId;
    this.userId = userId;
  }
  
}
