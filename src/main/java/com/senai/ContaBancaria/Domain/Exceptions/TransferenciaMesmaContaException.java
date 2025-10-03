package com.senai.ContaBancaria.Domain.Exceptions;

public class TransferenciaMesmaContaException extends RuntimeException {
    public TransferenciaMesmaContaException() {
        super("Impossivel realizar transferencia para a mesma conta: ");
    }
}
