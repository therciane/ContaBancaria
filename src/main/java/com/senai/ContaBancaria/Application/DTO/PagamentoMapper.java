package com.senai.ContaBancaria.Application.DTO;


import com.senai.ContaBancaria.Domain.Entity.PagamentoEntity;
import org.springframework.stereotype.Component;

@Component
public class PagamentoMapper {

    public PagamentoDTO toDTO(PagamentoEntity entity) {
        return new PagamentoDTO(
                entity.getId(),
                entity.getConta(),
                entity.getBoleto(),
                entity.getValorPago(),
                entity.getDataPagamento(),
                entity.getStatus(),
                entity.getTaxas()
        );
    }

    public PagamentoEntity toEntity(PagamentoDTO dto) {
        return PagamentoEntity.builder()
                .conta(dto.conta())
                .boleto(dto.boleto())
                .valorPago(dto.valorPago())
                .dataPagamento(dto.data())
                .status(dto.statusPagamento())
                .build();
    }
}


