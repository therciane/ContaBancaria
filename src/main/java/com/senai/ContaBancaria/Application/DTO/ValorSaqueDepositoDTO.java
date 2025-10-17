package com.senai.ContaBancaria.Application.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ValorSaqueDepositoDTO(
        @NotNull(message = "Para realizar uma transferencia é necessário de um valor")
        @DecimalMin(value = "0.0", inclusive = false)
        @Digits(integer = 12, fraction = 3, message = "O valor deve ter no máximo três casas decimais")
        BigDecimal valor
) {
}
