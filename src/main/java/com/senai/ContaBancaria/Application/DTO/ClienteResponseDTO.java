package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;

import java.util.List;

public record ClienteResponseDTO(
        String id,
        String nomeCompleto,
        long cpf,
        List <ContaResumoDTO> contas //Lista porque um cliente pode ter mais de uma conta
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
