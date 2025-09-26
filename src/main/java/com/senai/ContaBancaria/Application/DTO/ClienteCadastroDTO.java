package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;
import com.senai.ContaBancaria.Domain.Entity.ContaEntity;

import java.util.ArrayList;
import java.util.List;

public record ClienteCadastroDTO(
        String nomeCompleto,
        String cpf,
        ContaResumoDTO contaDTO
) {
    public ClienteEntity toEntity() {
        ClienteEntity cliente = ClienteEntity.builder()
                .ativo(true)
                .nomeCompleto(this.nomeCompleto)
                .cpf(this.cpf)
                .build();

        ContaEntity conta = contaDTO.toEntity(cliente);

        cliente.setContas(List.of(conta)); // ou adicionar manualmente

        return cliente;
    }

}
