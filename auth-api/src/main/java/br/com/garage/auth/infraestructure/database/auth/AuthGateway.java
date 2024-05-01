package br.com.garage.auth.infraestructure.database.auth;

import br.com.garage.auth.domains.auth.gateway.IAuthGateway;
import br.com.garage.auth.domains.auth.models.Role;
import br.com.garage.auth.domains.auth.models.Tenant;
import br.com.garage.auth.domains.auth.models.Usuario;
import br.com.garage.auth.domains.enums.Status;
import br.com.garage.auth.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthGateway implements IAuthGateway {
    
    private static final String EMAIL_NAO_ENCONTRADO = "e-mail não encontrado";
    private static final String EMPRESA_NAO_ENCONTRADA = "empresa não encontrada";
    private static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
    private static final String ROLE_NAO_ENCONTRADA = "Role não encontrada";

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ITenantRepository tenantRepository;

    @Autowired
    private IRoleRepository roleRepository;



    @Override
    public Usuario buscaUsuarioPorEmail(String email) throws UsernameNotFoundException {
        return userRepository.buscaPorEmail(email, Status.ATIVO)
            .orElseThrow(() -> new UsernameNotFoundException(EMAIL_NAO_ENCONTRADO));
    }

    @Override
    public void salvarUsuario(Usuario usuario) {
        userRepository.save(usuario);
    }

    @Override
    public Tenant buscarTenantPorId(UUID tenantId) throws NotFoundException {
		return tenantRepository.findById(tenantId)
				.orElseThrow(() -> new NotFoundException(EMPRESA_NAO_ENCONTRADA));
	}

    public Usuario buscarUsuarioPorId(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(USUARIO_NAO_ENCONTRADO));
    }

    @Override
    public List<Usuario> buscarUsuariosPorTenant(Tenant tenant) {
        return userRepository.buscaPorTenant(Status.ATIVO, tenant);
    }

    @Override
    public List<Tenant> buscarTenants() {
        return tenantRepository.findAll();
    }

    @Override
    public void salvarTenant(Tenant tenant) {
        tenantRepository.save(tenant);
    }

    @Override
    public void removeTenant(Tenant tenant) {
        tenantRepository.delete(tenant);
    }

    @Override
    public Role busarRolePorRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName)
            .orElseThrow(() -> new NotFoundException(ROLE_NAO_ENCONTRADA));
    }

    @Override
    public Tenant buscarTenantPorCpfCnpj(String cnpj) {
        Optional<Tenant> opt = tenantRepository.buscarPorCnpj(cnpj);

        return opt.isPresent() ? opt.get() : null; 
    }
}
