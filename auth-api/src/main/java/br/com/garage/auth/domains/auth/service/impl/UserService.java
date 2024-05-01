package br.com.garage.auth.domains.auth.service.impl;

import br.com.garage.auth.domains.auth.gateway.IAuthGateway;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

	private IAuthGateway authGateway;

	public UserService(IAuthGateway authGateway) {
		this.authGateway = authGateway;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return authGateway.buscaUsuarioPorEmail(email);
	}

}
