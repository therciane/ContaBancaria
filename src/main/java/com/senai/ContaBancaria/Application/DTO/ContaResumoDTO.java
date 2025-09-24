package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;
import com.senai.ContaBancaria.Domain.Entity.ContaCorrenteEntity;
import com.senai.ContaBancaria.Domain.Entity.ContaEntity;

import java.math.BigDecimal;

public record ContaResumoDTO(
        String numeroConta,
        String tipoConta,
        BigDecimal saldo
) {
    public ContaEntity toEntity(ClienteEntity cliente) {
        if ("CORRENTE".equalsIgnoreCase(tipoConta)) {
            return ContaCorrenteEntity.builder()
                    .numeroConta(this.numeroConta)
                    .saldo(this.saldo)
                    .ativo(true)
                    .cliente(cliente)
                    .build();

        } else if ("POUPANCA".equalsIgnoreCase(tipoConta)) {
            return ContaCorrenteEntity.builder()
                    .numeroConta(this.numeroConta)
                    .saldo(this.saldo)
                    .ativo(true)
                    .cliente(cliente)
                    .build();
        }
        return null;
    }

    public static ContaResumoDTO fromEntity(ContaEntity c) {
        return new ContaResumoDTO(
                c.getNumeroConta(),
                c.getTipoConta(),
                c.getSaldo()
        );
    }
}
