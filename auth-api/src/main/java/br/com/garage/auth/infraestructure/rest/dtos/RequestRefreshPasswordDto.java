package br.com.garage.auth.infraestructure.rest.dtos;

import lombok.Data;

@Data
public class RequestRefreshPasswordDto {
    private String email;
    private String tokenRefreshPassword;
    private String newPassword;
}