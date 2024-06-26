package br.com.garage.auth.domains.service;

import br.com.garage.auth.domains.gateway.IAuthGateway;
import br.com.garage.auth.domains.models.Role;
import br.com.garage.auth.domains.models.Usuario;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

	private final IAuthGateway authGateway;

	public RoleService(IAuthGateway authGateway) {
		this.authGateway = authGateway;
	}

	public void addRoleAdmin(Usuario usuario) {
		Role adminRole = authGateway.busarRolePorRoleName("ROLE_ADMIN");
		usuario.getRoles().add(adminRole);
	}

	public void addRoleCadastro(Usuario usuario) {
		Role adminRole = authGateway.busarRolePorRoleName("ROLE_USER");
		usuario.getRoles().add(adminRole);
	}
}
