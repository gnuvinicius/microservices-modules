package br.com.garage.auth.infraestructure.api.auth.dtos;

import br.com.garage.auth.domains.auth.models.Tenant;
import lombok.Getter;

@Getter
public class TenantRequestDto {

	private String nome;
	private String email;
	private String endereco;
	private String cnpj;
	private UsuarioRequestDto admin;
	
	public Tenant toModel() throws Exception {
		return new Tenant(this.nome, this.endereco, this.email, this.cnpj);
	}
}
