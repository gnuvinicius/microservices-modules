package br.com.garage.auth.domains;

import br.com.garage.auth.domains.auth.models.Tenant;
import br.com.garage.auth.domains.enums.EnumStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@MappedSuperclass
public abstract class AggregateRoot {

	@Id
	protected UUID id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tenant_id", nullable = false)
	protected Tenant tenant;

	protected LocalDateTime criadoEm;
	protected LocalDateTime atualizadoEm;
	protected EnumStatus status;

	protected AggregateRoot() {
		this.id = UUID.randomUUID();
		this.criadoEm = LocalDateTime.now();
		this.atualizadoEm = LocalDateTime.now();
		this.status = EnumStatus.ATIVO;
	}
}
