package br.com.garage.auth.domains.auth.service.impl;

import br.com.garage.auth.domains.auth.gateway.IAuthGateway;
import br.com.garage.auth.domains.auth.models.Tenant;
import br.com.garage.auth.domains.auth.models.Usuario;
import br.com.garage.auth.domains.auth.service.IAuthService;
import br.com.garage.auth.domains.auth.service.IManagerService;
import br.com.garage.auth.domains.auth.service.IRoleService;
import br.com.garage.auth.exceptions.BusinessException;
import br.com.garage.auth.infraestructure.api.auth.dtos.TenantRequestDto;
import br.com.garage.auth.infraestructure.api.auth.dtos.UsuarioRequestDto;
import br.com.garage.auth.infraestructure.api.auth.dtos.UsuarioResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ManagerService implements IManagerService {

	private static final String USUARIO_JA_EXISTE = "Usuário já existe";

	private final IAuthGateway authGateway;
	private final IRoleService roleService;
	private final PasswordEncoder passwordEncoder;
	private final IAuthService authService;
	private final ModelMapper mapper;

	public ManagerService(IAuthGateway authGateway, IRoleService roleService,
			PasswordEncoder passwordEncoder, IAuthService authService, ModelMapper mapper) {

		this.authGateway = authGateway;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
		this.authService = authService;
		this.mapper = mapper;
	}

	public UsuarioResponseDto cadastraUsuario(UsuarioRequestDto dto) throws Exception {
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
		if (dto.isSendWelcomeEmail()) {
			// emailService.sendMail(usuario.getEmail(), "", welcomeEmail());
		}
		return mapper.map(usuario, UsuarioResponseDto.class);
	}

	@Override
	public void arquiva(UUID id) {
		Usuario usuario = authGateway.buscarUsuarioPorId(id);
		usuario.inativa();
		authGateway.salvarUsuario(usuario);
	}

	@Override
	public List<UsuarioResponseDto> listaTodosUsuarios() {
		List<Usuario> usuarios = authGateway.buscarUsuariosPorTenant(authService.getCompanyByUserLogged());
		return usuarios.stream().map(u -> mapper.map(u, UsuarioResponseDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<Tenant> listaTodas() throws Exception {
		return authGateway.buscarTenants();
	}

	@Override
	public Tenant cadastraTenant(TenantRequestDto dto) throws Exception {
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
