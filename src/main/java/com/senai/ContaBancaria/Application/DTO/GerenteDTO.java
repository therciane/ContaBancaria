package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.GerenteEntity;
import com.senai.ContaBancaria.Domain.Enum.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record GerenteDTO(
        String id,

        @NotBlank(message = "O nome não pode estar vazio.")
        @Size(min = 3)
        @Schema(example = "Maria Eduarda Oliveira")
        String nomeCompleto,

        @NotBlank(message = "O CPF é obrigatório.")
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "O CPF deve estar no formato 000.000.000-00")
        @Schema(example = "123.456.789-01")
        String cpf,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email
        @Schema(example = "usuario@email.com")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6)
        @Schema(description = "sua senha")
        String senha,

        @NotNull
        @Schema(example = "true")
        Boolean ativo,

        @NotNull
        @Schema(example = "ADMIN")
        Role role
){

    public static GerenteDTO fromEntity(GerenteEntity gerente) {
        return GerenteDTO.builder()
                .id(gerente.getId())
                .nomeCompleto(gerente.getNomeCompleto())
                .cpf(gerente.getCpf())
                .email(gerente.getEmail())
                .ativo(gerente.isAtivo())
                .role(gerente.getRole())
                .build();
    }

    public GerenteEntity toEntity() {
        return GerenteEntity.builder()
                .id(this.id)
                .nomeCompleto(this.nomeCompleto)
                .cpf(this.cpf)
                .email(this.email)
                .senha(this.senha)
                .ativo(this.ativo != null ? this.ativo : true)
                .role(this.role != null ? this.role : Role.GERENTE)
                .build();
    }
}

