package br.com.garage.auth.infraestructure.rest.dtos;

import br.com.garage.auth.domains.enums.EnumStatus;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class TenantDto {
    private String id;
    private String nome;
    private String endereco;
    private String cnpj;
    private EnumStatus status;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}