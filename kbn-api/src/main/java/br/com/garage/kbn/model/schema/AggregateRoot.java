package br.com.garage.kbn.model.schema;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.garage.kbn.enums.EnumStatus;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class AggregateRoot {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	protected UUID id;
	protected UUID tenantId;
	protected UUID userId;
	protected LocalDateTime criadoEm;
	protected LocalDateTime atualizadoEm;
	protected EnumStatus status;

	protected AggregateRoot() {
		this.criadoEm = LocalDateTime.now();
		this.atualizadoEm = LocalDateTime.now();
		this.status = EnumStatus.ATIVO;
	}
}
