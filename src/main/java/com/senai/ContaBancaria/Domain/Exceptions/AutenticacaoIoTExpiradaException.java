package com.senai.ContaBancaria.Domain.Exceptions;

public class AutenticacaoIoTExpiradaException extends RuntimeException {
    public AutenticacaoIoTExpiradaException() {
        super("tempo para realização do pagamento expirou, por favor entre em contato com o banco.");
    }
}
