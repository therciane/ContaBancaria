package com.senai.ContaBancaria.Application.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senai.ContaBancaria.Domain.Entity.*;
import com.senai.ContaBancaria.Domain.Enum.StatusPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(
        name = "PagamentoDTO",
        description = "DTO para transportar informações de Taxas"
)

@Builder
public record PagamentoDTO(
            @NotNull
            ContaEntity conta,

            @NotBlank
            String boleto,

            @NotNull
            @Positive
            BigDecimal valorPago,

            @NotNull
            @JsonFormat(pattern = "dd-mm-yyyy'T'HH:mm:ss", timezone = "America/Sao_Paulo")
            LocalDateTime data,

            @NotNull
            StatusPagamento statusPagamento
) {

    public PagamentoDTO fromEntity(PagamentoEntity pagamento) {
        return new PagamentoDTO(
                conta,
                pagamento.getBoleto(),
                pagamento.getValorPago(),
                pagamento.getData(),
                pagamento.getStatusPagamento()
        );
    }

    public PagamentoEntity toEntity(){
        return PagamentoEntity.builder()
                .conta(conta)
                .boleto(boleto)
                .valorPago(valorPago)
                .data(data)
                .statusPagamento(this.statusPagamento != null ? this.statusPagamento : StatusPagamento.PENDENTE)
                .build();
    }
}
