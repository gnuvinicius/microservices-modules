package br.com.garage.auth.infraestructure.rest.dtos;

import lombok.Data;
import lombok.Getter;

@Data
public class UsuarioRequestDto {

	private String nome;
	private String email;
	private String password;
	private boolean primeiroAcesso;
	private boolean sendWelcomeEmail;
	private boolean isAdmin;
}
