package br.com.garage.kbn.shared;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.ServletContext;

public abstract class AbstractResource {
  
  @Autowired
  private ServletContext servletContext;

  protected JwtRequestAttributes request;

  protected void loadJwtRequest() {
    this.request = (JwtRequestAttributes)servletContext.getAttribute("requestAtt");
  }

}
