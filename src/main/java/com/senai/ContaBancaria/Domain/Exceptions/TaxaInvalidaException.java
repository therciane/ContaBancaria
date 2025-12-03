package com.senai.ContaBancaria.Domain.Exceptions;

import java.math.BigDecimal;

public class TaxaInvalidaException extends RuntimeException {

    public TaxaInvalidaException() {
        super("A taxa informada é inválida.");
    }

    public TaxaInvalidaException(String descricao) {
        super("A taxa '" + descricao + "' é inválida ou está configurada incorretamente.");
    }

    public TaxaInvalidaException(String descricao, BigDecimal percentual, BigDecimal valorFixo) {
        super("Taxa inválida: " + descricao +
                ". Percentual: " + percentual +
                ", Valor fixo: " + valorFixo + ".");
    }

    public TaxaInvalidaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

