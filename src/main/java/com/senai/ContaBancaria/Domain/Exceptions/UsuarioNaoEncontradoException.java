package com.senai.ContaBancaria.Domain.Exceptions;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String message) {
        super("Não possivel encontrar o usuario requerido");
    }
}
