package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PagamentoDTO(
            String id,
            ContaResumoDTO conta,
            String boleto,
            BigDecimal valorPago,
            LocalDate dataPagamento,
            Boolean status
) {

    public PagamentoDTO fromEntity(PagamentoEntity pagamento, ContaResumoDTO conta) {
        return new PagamentoDTO(
                pagamento.getId(),
                conta,
                pagamento.getBoleto(),
                pagamento.getValorPago(),
                pagamento.getDataPagamento(),
                pagamento.getStatus()
        );
    }

    public PagamentoEntity toEntity(){
        return PagamentoEntity.builder(
                .id(id)
                .
        );
    }
}
