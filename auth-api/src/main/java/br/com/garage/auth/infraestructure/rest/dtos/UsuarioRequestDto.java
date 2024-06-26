package br.com.garage.auth.infraestructure.rest.dtos;

import lombok.Data;

@Data
public class UsuarioRequestDto {

	private String nome;
	private String email;
	private String password;
	private boolean primeiroAcesso;
	private boolean sendWelcomeEmail;
	private boolean isAdmin;
}
