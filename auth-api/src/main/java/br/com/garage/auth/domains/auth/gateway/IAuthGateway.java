package br.com.garage.auth.domains.auth.gateway;

import br.com.garage.auth.domains.auth.models.Role;
import br.com.garage.auth.domains.auth.models.Tenant;
import br.com.garage.auth.domains.auth.models.Usuario;
import br.com.garage.auth.exceptions.NotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.UUID;

public interface IAuthGateway {

    Usuario buscaUsuarioPorEmail(String email) throws UsernameNotFoundException;
    void salvarUsuario(Usuario usuario);
    Tenant buscarTenantPorId(UUID tenantId) throws NotFoundException;
    Usuario buscarUsuarioPorId(UUID id) throws NotFoundException;
    List<Usuario> buscarUsuariosPorTenant(Tenant tenant);
    List<Tenant> buscarTenants();
    void salvarTenant(Tenant tenant);
    void removeTenant(Tenant tenant);
    Role busarRolePorRoleName(String roleName);
    Tenant buscarTenantPorCpfCnpj(String cnpj);
    Usuario buscarUsuarioPorEmail(String email);
}
