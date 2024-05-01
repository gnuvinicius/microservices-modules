package br.com.garage.auth.infraestructure.api.auth.dtos;

import br.com.garage.auth.domains.auth.models.Role;
import br.com.garage.auth.domains.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UsuarioResponseDto {

	private UUID id;
	private Status status;
	private String nome;
	private String email;
	private TenantDto tenant;
	private Set<Role> roles = new HashSet<>();
	private LocalDateTime ultimoAcesso;
	private LocalDateTime atualizadoEm;
}
