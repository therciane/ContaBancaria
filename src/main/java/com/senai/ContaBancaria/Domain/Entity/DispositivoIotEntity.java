package com.senai.ContaBancaria.Domain.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "dispositivo_iot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DispositivoIotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "codigo_serial", nullable = false, unique = true, length = 60)
    private String codigoSerial;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private Boolean ativo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;
}

