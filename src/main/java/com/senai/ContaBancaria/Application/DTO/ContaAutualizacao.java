package com.senai.ContaBancaria.Application.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ContaAutualizacao(

        @NotNull(message = "O saldo não pode ser negativo e/ou ficar em branco")
        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal saldo,

        @DecimalMin(value = "0.0", inclusive = false, message = "O limite de sua conta corrente, não pode ficar vazio")
        BigDecimal limite,

        @DecimalMin(value = "0.1", inclusive = false, message = "O rendimento de sua conta poupança é de 10% ao mês")
        BigDecimal rendimento,

        @DecimalMin(value = "0.1", inclusive = false, message = "A taxa de sua conta, não pode ficar vazio")
        BigDecimal taxa
) {

    }
