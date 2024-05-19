package br.com.garage.auth.infraestructure.rest.dtos;

import lombok.Data;

@Data
public class EnderecoDto {
	private String logradouro;
	private String numero;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;
	private String complemento;
}
