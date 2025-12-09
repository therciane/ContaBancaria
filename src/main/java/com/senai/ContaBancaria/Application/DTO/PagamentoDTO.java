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
import java.util.List;
import java.util.UUID;

@Schema(
        name = "PagamentoDTO",
        description = "DTO para transportar informações de Taxas"
)

@Builder
public record PagamentoDTO(
            UUID id,

            @NotNull
            ContaEntity conta,

            @NotBlank
            String boleto,

            @NotNull
            @Positive
            BigDecimal valorPago,

            @NotNull
            @JsonFormat(pattern = "dd-mm-yyyy'T'HH:MM:ss", timezone = "America/Sao_Paulo")
            LocalDateTime data,

            @NotNull
            StatusPagamento statusPagamento,

            List<TaxaEntity> taxas
) {

    public PagamentoDTO fromEntity(PagamentoEntity pagamento) {
        return new PagamentoDTO(
                id,
                conta,
                pagamento.getBoleto(),
                pagamento.getValorPago(),
                pagamento.getDataPagamento(),
                pagamento.getStatus(),
                taxas
        );
    }

    public PagamentoDTO toEntity(){
        return PagamentoDTO.builder()
                .conta(conta)
                .boleto(boleto)
                .valorPago(valorPago)
                .data(data)
                .statusPagamento(this.statusPagamento != null ? this.statusPagamento : StatusPagamento.PENDENTE)
                .taxas(taxas)
                .build();
    }
}
