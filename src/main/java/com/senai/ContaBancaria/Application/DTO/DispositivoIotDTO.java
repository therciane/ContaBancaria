package com.senai.ContaBancaria.Application.DTO;

import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;
import com.senai.ContaBancaria.Domain.Entity.DispositivoIotEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DispositivoIotDTO(
        UUID id,

        @NotBlank
        String codigoSerial,

        @NotBlank
        String modelo,

        @NotNull
        Boolean ativo,

        @NotNull
        String clienteId
) {

    public static DispositivoIotDTO fromEntity(DispositivoIotEntity disp) {
        return new DispositivoIotDTO(
                disp.getId(),
                disp.getCodigoSerial(),
                disp.getModelo(),
                disp.getAtivo(),
                disp.getCliente() != null ? disp.getCliente().getId() : null
        );
    }

    public DispositivoIotEntity toEntity(ClienteEntity cliente) {
        return DispositivoIotEntity.builder()
                .id(this.id)
                .codigoSerial(this.codigoSerial)
                .modelo(this.modelo)
                .ativo(this.ativo)
                .cliente(cliente)
                .build();
    }
}
