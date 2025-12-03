package com.senai.ContaBancaria.Domain.Exceptions;

import java.math.BigDecimal;

public class SaldoInsuficienteException extends RuntimeException {

    public SaldoInsuficienteException() {
        super("Saldo insuficiente para realizar a operação.");
    }

    public SaldoInsuficienteException(BigDecimal saldoAtual, BigDecimal valorTentado) {
        super("Saldo insuficiente. Saldo atual: " + saldoAtual + ", valor solicitado: " + valorTentado + ".");
    }

    public SaldoInsuficienteException(String mensagem) {
        super(mensagem);
    }

    public SaldoInsuficienteException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

