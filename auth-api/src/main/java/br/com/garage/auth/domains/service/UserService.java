package br.com.garage.auth.domains.service;

import br.com.garage.auth.domains.gateway.IAuthGateway;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

	private final IAuthGateway authGateway;

	public UserService(IAuthGateway authGateway) {
		this.authGateway = authGateway;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return authGateway.buscaUsuarioPorEmail(email);
	}

}
