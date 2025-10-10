package com.senai.ContaBancaria.Application.DTO;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ValorSaqueDepositoDTO(
        @NotNull
        BigDecimal valor
) {
}
