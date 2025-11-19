package com.senai.ContaBancaria.Domain.Exceptions;

public class TaxaInvalidaException extends RuntimeException {
    public TaxaInvalidaException() {
        super("A taxa aplicada é inválida");
    }
}
