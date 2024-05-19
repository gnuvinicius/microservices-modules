package br.com.garage.auth.infraestructure.rest.dtos;

import br.com.garage.auth.domains.models.Role;
import br.com.garage.auth.domains.enums.EnumStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class UsuarioResponseDto {

	private UUID id;
	private EnumStatus status;
	private String nome;
	private String email;
	private TenantDto tenant;
	private Set<Role> roles = new HashSet<>();
	private LocalDateTime ultimoAcesso;
	private LocalDateTime atualizadoEm;
}
