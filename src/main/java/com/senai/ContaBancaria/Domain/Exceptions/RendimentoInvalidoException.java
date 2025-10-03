package com.senai.ContaBancaria.Domain.Exceptions;

public class RendimentoInvalidoException extends RuntimeException {
    public RendimentoInvalidoException() {
        super("O rendimento aplicado somente em contas do tipo Poupança.");
    }
}
