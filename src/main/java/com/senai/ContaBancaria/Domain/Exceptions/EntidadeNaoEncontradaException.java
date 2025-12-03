package com.senai.ContaBancaria.Domain.Exceptions;

public class EntidadeNaoEncontradaException extends RuntimeException {

    public EntidadeNaoEncontradaException(String entidade) {
        super("Não foi possível localizar a entidade: " + entidade + ".");
    }
}

