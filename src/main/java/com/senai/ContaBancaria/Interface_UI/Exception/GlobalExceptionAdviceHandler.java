package com.senai.ContaBancaria.Interface_UI.Exception;

import com.senai.ContaBancaria.Domain.Exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdviceHandler {

    @ExceptionHandler(ContaMesmoTipoException.class)
    public ResponseEntity<String> handleContaMesmoTipo(ContaMesmoTipoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<String> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RendimentoInvalidoException.class)
    public ResponseEntity<String> handleRendimentoInvalido(RendimentoInvalidoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<String> handleSaldoInsuficiente(SaldoInsuficienteException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(TipoDeContaInvalidaException.class)
    public ResponseEntity<String> handleTipoDeContaInvalida(TipoDeContaInvalidaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransferenciaMesmaContaException.class)
    public ResponseEntity<String> handleTransferenciaParaMesmaConta(TransferenciaMesmaContaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    //Ele trata a exceção ValoresNegativosException e retorna uma resposta HTTP com o status 400 (Bad Request) e a mensagem da exceção.
    @ExceptionHandler(ValoresNegativosException.class)
    public ResponseEntity<String> handleValoresNegativos(ValoresNegativosException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("Ocorreu um erro inesperado: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}