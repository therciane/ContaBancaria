package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;
import com.senai.ContaBancaria.Domain.Entity.DispositivoIotEntity;

import java.util.UUID;

public record DispositivoIotDTO(
        UUID id,
        String codigoSerial,
        String modelo,
        Boolean ativo,
        String clienteId
) {

    public static DispositivoIotDTO fromEntity(DispositivoIotDTO dispIot) {
        return new DispositivoIotDTO(
                dispIot.id(),
                dispIot.codigoSerial(),
                dispIot.modelo(),
                dispIot.ativo(),
                dispIot.clienteId()
        );
    }

    public DispositivoIotEntity toEntity(ClienteEntity cliente){
        return DispositivoIotEntity.builder()
                .id(this.id)
                .codigoSerial(this.codigoSerial)
                .modelo(this.modelo)
                .ativo(this.ativo)
                .relacionamentoCliente(cliente)
                .build();
    }
}
