package com.senai.ContaBancaria.Domain.Exceptions;

public class AutenticacaoIoTExpiradaException extends RuntimeException {

    public AutenticacaoIoTExpiradaException() {
        super("O código de autenticação expirou e a operação não pode ser concluída.");
    }

    public AutenticacaoIoTExpiradaException(String mensagem) {
        super(mensagem);
    }
}

