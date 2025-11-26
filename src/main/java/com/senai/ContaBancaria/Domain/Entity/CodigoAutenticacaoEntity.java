package com.senai.ContaBancaria.Domain.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "codigo_autenticacao")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CodigoAutenticacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 10)
    private String codigo;

    @Column(nullable = false)
    private LocalDateTime expiracao; // ou LocalDateTime, dependendo da regra

    @Column(nullable = false)
    private Boolean validado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;

}
