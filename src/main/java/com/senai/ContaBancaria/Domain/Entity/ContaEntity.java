package com.senai.ContaBancaria.Domain.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //Ela é uma herança, que usamos quando haverá uma tabela. Parrecido com a MappedSuperClass
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING, length = 20) //Discrimina o tipo de classe que estu salvando
@Table(
        name = "conta",
        uniqueConstraints = { @UniqueConstraint(name = "uk_conta_numero", columnNames = "numero"),
        @UniqueConstraint(name = "uk_cliente_tipo", columnNames = {"cliente_id", "tipo_conta"})
        }
)
@SuperBuilder //para heranças

public abstract class ContaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String numeroConta; //Associação de informações.

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal saldo; //BigDecimal para numeros decimais, como dinheiro

    @Column(nullable = false)
    private boolean ativo;

    @ManyToOne(fetch = FetchType.LAZY) // Busca preguiçosa, quando achat tal coisa at
    @JoinColumn (
            name = "cliente_id",
            foreignKey = @ForeignKey(name = "fk_conta_cliente")
    )
    private ClienteEntity cliente;
}