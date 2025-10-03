package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;

import java.util.List;

public record ClienteResponseDTO(
        String id,
        String nomeCompleto,
        String cpf,
        List<ContaResumoDTO> contas
) {
    public static ClienteResponseDTO fromEntity(ClienteEntity cliente) {
        List<ContaResumoDTO> contas = cliente.getContas().stream()
                .map(ContaResumoDTO::fromEntity)
                .toList();

        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNomeCompleto(),
                cliente.getCpf(),
                contas
        );
    }
}
