package br.com.garage.auth.infraestructure.rest.dtos;

import br.com.garage.auth.domains.models.Tenant;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class TenantRequestDto {

	private String nome;
	private String endereco;
	private String cnpj;
	private UsuarioRequestDto admin;
	
	public Tenant toModel() throws Exception {
		return new Tenant(this.nome, this.endereco, this.cnpj);
	}
}
