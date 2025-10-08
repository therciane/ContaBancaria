package com.senai.ContaBancaria.Interface_UI.Exception;

import com.senai.ContaBancaria.Domain.Exceptions.ValoresNegativosException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdviceHandler {

    //Ele trata a exceção ValoresNegativosException e retorna uma resposta HTTP com o status 400 (Bad Request) e a mensagem da exceção.
    @ExceptionHandler(ValoresNegativosException.class)
    public ResponseEntity<String> handleValoresNegativos (ValoresNegativosException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }



}
