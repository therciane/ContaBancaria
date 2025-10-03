package com.senai.ContaBancaria.Domain.Exceptions;

public class SaldoInsuficienteException extends RuntimeException {

    public SaldoInsuficienteException() {
        super("Saldo insuficiente para realizar a operação.");
    }
}
