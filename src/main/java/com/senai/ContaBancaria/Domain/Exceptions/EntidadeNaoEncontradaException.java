package com.senai.ContaBancaria.Domain.Exceptions;

public class EntidadeNaoEncontradaException extends RuntimeException {
    public EntidadeNaoEncontradaException(String entidade) {
        super(entidade + " não existente ou inativa(o)");
    }
}
