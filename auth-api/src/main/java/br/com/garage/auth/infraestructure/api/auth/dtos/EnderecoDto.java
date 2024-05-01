package br.com.garage.auth.infraestructure.api.auth.dtos;

public record EnderecoDto(String logradouro,
		String numero,
		String bairro,
		String cidade,
		String estado,
		String cep,
		String complemento) {
}
