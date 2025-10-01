package com.senai.ContaBancaria.Application.DTO;

import java.math.BigDecimal;

public record ContaAutualizacao(
        BigDecimal saldo,
        BigDecimal limite,
        BigDecimal rendimento,
        BigDecimal taxa
) {

    }
