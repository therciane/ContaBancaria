package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;
import com.senai.ContaBancaria.Domain.Entity.ContaEntity;

import java.util.ArrayList;

public record ClienteCadastroDTO(
        String nomeCompleto,
        String cpf,
        ContaResumoDTO contaDTO
) {

    public ClienteEntity toEntity() {
        return ClienteEntity.builder()
                .ativo(true)
                .nomeCompleto(this.nomeCompleto)
                .cpf(this.cpf)
                .contas(new ArrayList<ContaEntity>())
                .build();
    }

}
