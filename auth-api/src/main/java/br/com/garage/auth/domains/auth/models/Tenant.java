package br.com.garage.auth.domains.auth.models;

import br.com.garage.auth.domains.enums.Status;
import br.com.garage.auth.infraestructure.api.auth.dtos.TenantRequestDto;
import com.garage.auth.utils.AssertionConcern;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "tb_tenant")
public class Tenant {

	private static final String NULO_OU_VAZIO = "o campo %s não pode ser nulo ou vazio";

	@Id
	private UUID id;
	private Status status;
	private String nome;
	private String endereco;
	private String email;
	private LocalDateTime criadoEm;
	private LocalDateTime atualizadoEm;

	@Column(unique = true)
	private String cnpj;

	public Tenant(String nome, String endereco, String cnpj) throws Exception {
		this.id = UUID.randomUUID();
		this.status = Status.INATIVO;
		this.nome = nome;
		this.endereco = endereco;
		this.cnpj = cnpj;
		this.criadoEm = LocalDateTime.now();
		valida();
	}

	public Tenant(TenantRequestDto dto, String endereco) throws Exception {
		this(dto.getNome(), endereco, dto.getCnpj());
	}

	public void ativaTenant() {
		this.status = Status.ATIVO;
		this.atualizadoEm = LocalDateTime.now();
	}

	public void valida() throws Exception {
		AssertionConcern.ValideIsNotEmptyOrBlank(nome, String.format(NULO_OU_VAZIO, "nome"));
		AssertionConcern.ValideIsNotEmptyOrBlank(cnpj, String.format(NULO_OU_VAZIO, "CNPJ"));
	}

}
