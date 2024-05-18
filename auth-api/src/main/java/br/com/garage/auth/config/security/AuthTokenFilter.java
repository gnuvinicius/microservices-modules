package br.com.garage.auth.config.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.garage.auth.domains.enums.EnumStatus;
import br.com.garage.auth.infraestructure.database.IUserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthTokenFilter extends OncePerRequestFilter  {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private IUserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter)
			throws ServletException, IOException {

		var token = getTokenByRequest(request);

		if (token != null) {
			var email = tokenService.validateToken(token);
			
			if (!email.isEmpty()) {				
				var optional = userRepository.buscaPorEmail(email, EnumStatus.ATIVO);
				
				if (optional.isPresent()) {
					var user = optional.get();
					var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
		}
		
		filter.doFilter(request, response);
	}

	private String getTokenByRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || !token.startsWith("Bearer ")) {
			return null;
		}

		return token.substring(7, token.length());
	}

}
