package com.senai.ContaBancaria.Application.DTO;

import java.math.BigDecimal;

public record transferenciaDTO(
        BigDecimal valor,
        String contaDestino
) {

}
