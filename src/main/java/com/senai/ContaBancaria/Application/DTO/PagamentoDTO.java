package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.*;
import com.senai.ContaBancaria.Domain.Enum.StatusPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema

public record PagamentoDTO(
            ContaEntity conta,
            String boleto,
            BigDecimal valorPago,
            LocalDateTime data,
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
