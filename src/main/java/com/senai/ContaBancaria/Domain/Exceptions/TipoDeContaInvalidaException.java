package com.senai.ContaBancaria.Domain.Exceptions;

public class TipoDeContaInvalidaException extends RuntimeException {
    public TipoDeContaInvalidaException() {
        super("Tipo de conta inválida. Os tipos válidos são: 'Corrente' e 'Poupança'.");
    }
}
