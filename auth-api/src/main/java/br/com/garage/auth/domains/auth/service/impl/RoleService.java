package br.com.garage.auth.domains.auth.service.impl;

import br.com.garage.auth.domains.auth.gateway.IAuthGateway;
import br.com.garage.auth.domains.auth.models.Role;
import br.com.garage.auth.domains.auth.models.Usuario;
import br.com.garage.auth.domains.auth.service.IRoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {

	private IAuthGateway authGateway;

	public RoleService(IAuthGateway authGateway) {
		this.authGateway = authGateway;
	}

	@Override
	public void addRoleAdmin(Usuario usuario) {
		Role adminRole = authGateway.busarRolePorRoleName("ROLE_ADMIN");
		usuario.getRoles().add(adminRole);
	}

	@Override
	public void addRoleCadastro(Usuario usuario) {
		Role adminRole = authGateway.busarRolePorRoleName("ROLE_USER");
		usuario.getRoles().add(adminRole);
	}
}
