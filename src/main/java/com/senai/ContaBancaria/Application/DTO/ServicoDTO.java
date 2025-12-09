package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.ServicoEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ServicoDTO(
        @Schema(description = "ID da conta bancaria", example = "1")
        String id,

        @NotBlank(message = "Informações da conta bancaria são obrigatórias")
        @Schema(description = "Descrição do conta bancaria", example = "A conta bancaria é do tipo poupança")
        String tipoConta,

        @NotNull(message = "O saldo é obrigatório")
        @DecimalMin(value = "0.0", inclusive = false, message = "saldo deve ser positivo")
        BigDecimal saldo
) {
    public static ServicoDTO fromEntity(ServicoEntity s) {
        return new ServicoDTO(
                s.getId(),
                s.getTipoConta(),
                s.getSaldo()
        );
    }

    public ServicoEntity toEntity() {
        return ServicoEntity.builder()
                .id(id)
                .tipoConta(tipoConta)
                .saldo(saldo)
                .build();
    }
}

