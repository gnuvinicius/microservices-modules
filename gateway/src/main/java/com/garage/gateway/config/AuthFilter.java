package com.garage.gateway.config;


import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
  
  private final WebClient.Builder webClientBuider;

  public AuthFilter(WebClient.Builder webClientBuilder) {
    super(Config.class);
    this.webClientBuider = webClientBuilder;
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
        throw new RuntimeException("Missing authorization information");
      }

      String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

      String[] parts = authHeader.split(" ");

      if (parts.length != 2 || !"Bearer".equals(parts[0])) {
        throw new RuntimeException("Incorrect authorization structure");
      }

      return webClientBuider.build()
        .post()
        .uri("http://auth-api/auth/api/v1/validate-token?token=" + parts[1])
        .retrieve().bodyToMono(UserDto.class)
        .map(userDto -> {
          exchange.getRequest().mutate().header("X-auth-user-id", String.valueOf(userDto.getId()));
          return exchange;
        }).flatMap(chain::filter);
    };
  }

  public static class Config {

  }
}
