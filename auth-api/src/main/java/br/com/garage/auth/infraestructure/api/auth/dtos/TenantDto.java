package br.com.garage.auth.infraestructure.api.auth.dtos;

import br.com.garage.auth.domains.enums.EnumStatus;

import java.time.LocalDateTime;

public record TenantDto(Long id, String nome,
        String endereco,
        String cnpj,
        EnumStatus status,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm) {
}