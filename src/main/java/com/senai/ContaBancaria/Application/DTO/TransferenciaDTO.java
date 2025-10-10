package com.senai.ContaBancaria.Application.DTO;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record TransferenciaDTO(

        @NotNull(message = "Para realizar uma tranferencia é necessário de um valor")
        @DecimalMin(value = "0.0", inclusive = false)
        @Digits(integer = 12, fraction = 3, message = "O valor deve ter no máximo três casas decimais")
        BigDecimal valor,

        @NotBlank(message = "A conta de destino é obrigatória")
        @Pattern(regexp = "\\d{8,12}", message = "A conta de destino deve conter entre 8 e 12 números")
        String contaDestino
) {

}
