package com.senai.ContaBancaria.Domain.Entity;

import com.senai.ContaBancaria.Domain.Enum.StatusPagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pagamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", nullable = false)
    private ContaEntity conta;

    @NotNull
    @Column(nullable = false, length = 48)
    private String boleto;

    @NotNull
    @Positive
    @Column(name = "valor_pago", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorPago;

    @NotNull
    @Column(name = "data_pagamento", nullable = false)
    private LocalDateTime dataPagamento;

    @NotNull
    @Column(nullable = false)
    private StatusPagamento status;

    @ManyToMany @JoinTable(
            name = "pagamento_taxas",
            joinColumns = @JoinColumn(name = "pagamento_id"),
            inverseJoinColumns = @JoinColumn(name = "taxa_id")
    )
    private List<TaxaEntity> taxas;
}
