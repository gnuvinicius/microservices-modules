package br.com.garage.auth.infraestructure.rest.dtos;

public record RequestRefreshPasswordDto(String email, String tokenRefreshPassword, String newPassword) {
}