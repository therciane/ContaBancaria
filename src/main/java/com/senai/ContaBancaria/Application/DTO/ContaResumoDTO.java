package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;
import com.senai.ContaBancaria.Domain.Entity.ContaCorrenteEntity;
import com.senai.ContaBancaria.Domain.Entity.ContaEntity;
import com.senai.ContaBancaria.Domain.Entity.ContaPoupancaEntity;

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
                    .limite(new BigDecimal("500.00"))
                    .taxa(new BigDecimal("0.05"))
                    .build();

        } else if ("POUPANCA".equalsIgnoreCase(tipoConta)) {
            return ContaPoupancaEntity.builder()
                    .numeroConta(this.numeroConta)
                    .saldo(this.saldo)
                    .ativo(true)
                    .cliente(cliente)
                    .rendimento(new BigDecimal("0.01"))
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
