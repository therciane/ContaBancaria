package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.ContaCorrenteEntity;
import com.senai.ContaBancaria.Domain.Entity.ContaEntity;

import java.math.BigDecimal;

public record ContaResumoDTO(
        String numeroConta,
        String tipoConta,
        BigDecimal saldo
) {
    public ContaEntity toEntity() {
        if ("CORRENTE".equalsIgnoreCase(tipoConta)) {
            return ContaCorrenteEntity.builder()
                    .numeroConta(this.numeroConta)
                    .saldo(this.saldo)
                    .ativo(true)
                    .saldo(this.saldo)
                    .build();
        } else () {

        }
    }
}
