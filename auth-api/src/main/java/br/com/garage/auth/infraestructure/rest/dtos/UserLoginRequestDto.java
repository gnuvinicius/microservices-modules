package br.com.garage.auth.infraestructure.rest.dtos;

public record UserLoginRequestDto(String email, String password) {
}
