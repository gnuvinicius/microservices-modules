package br.com.garage.kbn.config.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.garage.kbn.shared.JwtRequestAttributes;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthTokenFilter extends OncePerRequestFilter  {

	@Autowired
	private ServletContext servletContext;
	@Autowired
	private TokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter)
			throws ServletException, IOException {

		var token = getTokenByRequest(request);

		if (token != null) {
			var result = tokenService.validateToken(token);
			servletContext.setAttribute("requestAtt", new JwtRequestAttributes(result.get("tenantId"), result.get("userId")));
			
			var auth = new UsernamePasswordAuthenticationToken(result.get("email"), null, getAuthorities(result.get("role")));
			SecurityContextHolder.getContext().setAuthentication(auth);
			filter.doFilter(request, response);
		}
	}

	private String getTokenByRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}

		return token.substring(7, token.length());
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String roles) {
		var list = new ArrayList<String>(Arrays.asList(roles.split(",")));
		
			return list.stream().map(x -> new SimpleGrantedAuthority(x))
			.collect(Collectors.toList());
	}

}
