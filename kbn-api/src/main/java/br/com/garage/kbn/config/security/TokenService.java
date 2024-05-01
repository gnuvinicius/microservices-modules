package br.com.garage.kbn.config.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class TokenService {

	@Value("${jwt.expiration}")
	private String expiration;

	@Value("${jwt.secret}")
	private String secret;

	public Map<String, String> validateToken(String token) {
		Map<String, String> result = new HashMap<>();
		
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			DecodedJWT verify = JWT.require(algorithm).withIssuer("auth").build().verify(token);
			String roles = verify.getClaim("role").asString();
			result.put("role", roles);
			result.put("email", verify.getSubject());
			result.put("tenantId", verify.getClaim("tenant_id").asString());
			result.put("userId", verify.getClaim("user_id").asString());
			return result;
		} catch (JWTVerificationException exception) {
			return result;
		}
	}
}
