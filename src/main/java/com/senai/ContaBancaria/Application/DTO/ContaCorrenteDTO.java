package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.ContaCorrenteEntity;

public record ContaCorrenteDTO(
        int numeroConta,
        double saldo,
        double limite,
        long taxa
) {

    public static ContaCorrenteDTO fromEntity(ContaCorrenteEntity contaCorrente){
        if (contaCorrente == null) return null;
        return new ContaCorrenteDTO(
                contaCorrente.getNumeroConta(),
                contaCorrente.getSaldo(),
                contaCorrente.getLimite(),
                contaCorrente.getTaxa()
        );
    }

    public ContaCorrenteEntity toEntity(){
        ContaCorrenteEntity contaCorrente = new ContaCorrenteEntity();
        contaCorrente.setNumeroConta(this.numeroConta);
        contaCorrente.setSaldo(this.saldo);
        contaCorrente.setLimite(this.limite);
        contaCorrente.setTaxa(this.taxa);
        return contaCorrente;
    }
}
