package br.com.garage.auth.domains.service;

import br.com.garage.auth.domains.gateway.IAuthGateway;
import br.com.garage.auth.domains.models.Tenant;
import br.com.garage.auth.domains.models.Usuario;
import br.com.garage.auth.exceptions.BusinessException;
import br.com.garage.auth.infraestructure.rest.dtos.TenantRequestDto;
import br.com.garage.auth.infraestructure.rest.dtos.UsuarioRequestDto;
import br.com.garage.auth.infraestructure.rest.dtos.UsuarioResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ManagerService {

	private static final String USUARIO_JA_EXISTE = "Usuário já existe";

	private final IAuthGateway authGateway;
	private final RoleService roleService;
	private final PasswordEncoder passwordEncoder;
	private final AuthService authService;
	private final ModelMapper mapper;

	public ManagerService(IAuthGateway authGateway, RoleService roleService,
			PasswordEncoder passwordEncoder, AuthService authService, ModelMapper mapper) {

		this.authGateway = authGateway;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
		this.authService = authService;
		this.mapper = mapper;
	}

	public UsuarioResponseDto cadastraUsuario(UsuarioRequestDto dto) {
		try {
			authGateway.buscaUsuarioPorEmail(dto.getEmail());
			throw new BusinessException(USUARIO_JA_EXISTE);
		} catch (Exception e) {
			//
		}
		authService.validPasswordPolicies(dto.getPassword());
		var usuario = new Usuario(dto, passwordEncoder.encode(dto.getPassword()), authService.getCompanyByUserLogged());
		if (dto.isAdmin()) {
			roleService.addRoleAdmin(usuario);
		}
		roleService.addRoleCadastro(usuario);
		authGateway.salvarUsuario(usuario);
		//if (dto.isSendWelcomeEmail()) {
			// emailService.sendMail(usuario.getEmail(), "", welcomeEmail());
		//}
		return mapper.map(usuario, UsuarioResponseDto.class);
	}

	public void arquiva(UUID id) {
		Usuario usuario = authGateway.buscarUsuarioPorId(id);
		usuario.inativa();
		authGateway.salvarUsuario(usuario);
	}

	public List<UsuarioResponseDto> listaTodosUsuarios() {
		List<Usuario> usuarios = authGateway.buscarUsuariosPorTenant(authService.getCompanyByUserLogged());
		return usuarios.stream().map(u -> mapper.map(u, UsuarioResponseDto.class)).collect(Collectors.toList());
	}

	public List<Tenant> listaTodas() {
		return authGateway.buscarTenants();
	}

	public Tenant cadastraTenant(TenantRequestDto dto) {
		try {
			var tenant = salvaNovaTenant(dto);
			cadastraUsuarioAdminDefault(dto.getAdmin(), tenant);
			return tenant;
		} catch (Exception ex) {
			throw new BusinessException("Não foi possivel concluir o cadastro da sua empresa: " + ex.getMessage());
		}
	}

	private Tenant salvaNovaTenant(TenantRequestDto dto) throws Exception {
		if (authGateway.buscarTenantPorCpfCnpj(dto.getCnpj()) != null) {
			throw new BusinessException("empresa ja cadastrada!");
		}
		Tenant tenant = dto.toModel();
		authGateway.salvarTenant(tenant);
		return tenant;
	}

	private void cadastraUsuarioAdminDefault(UsuarioRequestDto request, Tenant tenant) {
		try {
			if (authGateway.buscarUsuarioPorEmail(request.getEmail()) != null) {
				authGateway.removeTenant(tenant);
				throw new BusinessException("já existe um usuario com esse e-mail");
			}
			var usuario = new Usuario(request, passwordEncoder.encode(request.getPassword()), tenant);
			roleService.addRoleAdmin(usuario);
			roleService.addRoleCadastro(usuario);
			authGateway.salvarUsuario(usuario);
		} catch (Exception ex) {
			authGateway.removeTenant(tenant);
			throw new IllegalArgumentException(ex.getMessage());
		}
	}
}
