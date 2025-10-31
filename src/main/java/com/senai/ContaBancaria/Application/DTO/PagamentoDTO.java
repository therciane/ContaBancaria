package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.*;
import com.senai.ContaBancaria.Domain.Enum.StatusPagamento;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PagamentoDTO(
            String id,
            ContaResumoDTO conta,
            String boleto,
            BigDecimal valorPago,
            LocalDateTime dataPagamento,
            StatusPagamento statusPagamento
) {

    public PagamentoDTO fromEntity(PagamentoEntity pagamento, ContaResumoDTO conta) {
        return new PagamentoDTO(
                pagamento.getId(),
                conta,
                pagamento.getBoleto(),
                pagamento.getValorPago(),
                pagamento.getStatusPagamento(),
                pagamento.getStatusPagamento()
        );
    }

    public PagamentoEntity toEntity(){
        return PagamentoEntity.builder()
                .id(id)
                .conta(conta)
                .boleto(boleto)
                .valorPago(valorPago)
                .dataPagamento(dataPagamento)
                .statusPagamento(statusPagamento);

    }
}
