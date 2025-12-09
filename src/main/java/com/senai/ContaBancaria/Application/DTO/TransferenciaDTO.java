package com.senai.ContaBancaria.Application.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record TransferenciaDTO(

        @Schema(description = "Valor a ser transferido", example = "2500.00")
        @NotNull(message = "Para realizar uma transferência é necessário informar um valor")
        @DecimalMin(value = "0.0", inclusive = false, message = "O valor deve ser maior que zero")
        @Digits(integer = 12, fraction = 2, message = "O valor deve ter no máximo duas casas decimais")
        BigDecimal valor,

        @Schema(description = "Número da conta de destino", example = "89716123-X")
        @NotBlank(message = "A conta de destino é obrigatória")
        @Pattern(regexp = "\\d{8,12}", message = "A conta de destino deve conter entre 8 e 12 números")
        String contaDestino
) {}

