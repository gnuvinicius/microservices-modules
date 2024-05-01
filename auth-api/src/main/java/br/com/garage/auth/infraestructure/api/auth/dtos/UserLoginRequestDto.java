package br.com.garage.auth.infraestructure.api.auth.dtos;

public record UserLoginRequestDto(String email, String password) {
}
