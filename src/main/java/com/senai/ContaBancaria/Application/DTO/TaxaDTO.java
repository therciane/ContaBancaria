package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.ContaEntity;
import com.senai.ContaBancaria.Domain.Entity.TaxaEntity;
import com.senai.ContaBancaria.Domain.Enum.Descricao;
import java.math.BigDecimal;

public record TaxaDTO(
        String id,
        ContaEntity conta,
        Descricao descricao,
        BigDecimal percentual,
        BigDecimal valorFixo
) {

    public TaxaDTO fromEntity(TaxaEntity taxa) {
        return new TaxaDTO(
                taxa.getId(),
                conta,
                taxa.getDescricao(),
                taxa.getPercentual(),
                taxa.getValorFixo()
        );
    }
}
