package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.ContaPoupancaEntity;

public record ContaPoupancaDTO(
        String id,
        int numeroConta,
        double saldo,
        long rendimento
) {

    public static ContaPoupancaDTO fromEntity(ContaPoupancaEntity contaPoupanca){
        if (contaPoupanca == null) return null;
        return new ContaPoupancaDTO(
                contaPoupanca.getId(),
                contaPoupanca.getNumeroConta(),
                contaPoupanca.getSaldo(),
                contaPoupanca.getRendimento()
        );
    }

    public ContaPoupancaEntity toEntity(){
        ContaPoupancaEntity contaPoupanca = new ContaPoupancaEntity();
        contaPoupanca.setNumeroConta(this.numeroConta);
        contaPoupanca.setSaldo(this.saldo);
        contaPoupanca.setRendimento(this.rendimento);
        return contaPoupanca;
    }
}
