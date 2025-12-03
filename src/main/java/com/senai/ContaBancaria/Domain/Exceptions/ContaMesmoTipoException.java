package com.senai.ContaBancaria.Domain.Exceptions;

public class ContaMesmoTipoException extends RuntimeException {

    public ContaMesmoTipoException() {
        super("O cliente já possui uma conta desse tipo.");
    }
}
