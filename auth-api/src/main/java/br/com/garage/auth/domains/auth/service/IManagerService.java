package br.com.garage.auth.domains.auth.service;

import br.com.garage.auth.domains.auth.models.Tenant;
import br.com.garage.auth.infraestructure.api.auth.dtos.TenantRequestDto;
import br.com.garage.auth.infraestructure.api.auth.dtos.UsuarioRequestDto;
import br.com.garage.auth.infraestructure.api.auth.dtos.UsuarioResponseDto;

import java.util.List;
import java.util.UUID;

public interface IManagerService {

	UsuarioResponseDto cadastraUsuario(UsuarioRequestDto dto) throws Exception;

	void arquiva(UUID id);

	List<UsuarioResponseDto> listaTodosUsuarios();

	List<Tenant> listaTodas() throws Exception;

	Tenant cadastraTenant(TenantRequestDto request) throws Exception;
}
