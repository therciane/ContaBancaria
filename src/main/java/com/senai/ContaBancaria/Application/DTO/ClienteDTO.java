package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;

import java.util.List;

public record ClienteDTO(
        String id,
        String nomeCompleto,
        long cpf,
        List<String> tipContas
) {

    public static ClienteDTO fromEntity(ClienteEntity cliente){
        if (cliente == null) return null;

        return new ClienteDTO(
                cliente.getId(),
                cliente.getNomeCompleto(),
                cliente.getCpf(),
                cliente.getTipoContas()
        );
    }
}
