package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;

import java.util.ArrayList;
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

    public ClienteEntity toEntity(){
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNomeCompleto(this.nomeCompleto);
        cliente.setCpf(this.cpf);
        cliente.setTipoContas(this.tipContas != null ? new ArrayList<>(this.tipContas) : new ArrayList<>());
        return cliente;
    }
}
