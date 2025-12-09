package com.senai.ContaBancaria.Application.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ValorSaqueDepositoDTO(

        @Schema(description = "Valor do saque ou depósito", example = "150.00")
        @NotNull(message = "O valor é obrigatório para saque ou depósito")
        @DecimalMin(value = "0.0", inclusive = false, message = "O valor deve ser maior que zero")
        @Digits(integer = 12, fraction = 2, message = "O valor deve ter no máximo duas casas decimais")
        BigDecimal valor
) {}
