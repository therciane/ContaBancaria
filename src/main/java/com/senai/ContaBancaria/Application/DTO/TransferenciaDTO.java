package com.senai.ContaBancaria.Application.DTO;

import java.math.BigDecimal;

public record TransferenciaDTO(
        BigDecimal valor,
        String contaDestino
) {

}
