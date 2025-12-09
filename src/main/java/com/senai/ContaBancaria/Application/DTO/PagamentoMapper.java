package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.PagamentoEntity;
import org.springframework.stereotype.Component;

@Component
public class PagamentoMapper {

    public PagamentoDTO toDTO(PagamentoDTO dto) {
        return new PagamentoDTO(
                dto.id(),
                dto.conta(),
                dto.boleto(),
                dto.valorPago(),
                dto.data(),
                dto.statusPagamento(),
                dto.taxas()
        );
    }
}

