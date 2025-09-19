package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.ContaEntity;

import java.math.BigDecimal;

public record ContaResumoDTO(
        String numeroConta,
        String tipoConta,
        BigDecimal saldo
) {
    public ContaEntity toEntity() {

    }
}
