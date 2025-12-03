package com.senai.ContaBancaria.Domain.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//Não usar @Data para evitar problemas com herança em entidades JPA

@Entity
//Modela a tabela no banco de dados
@Table(
        name = "cliente",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_cliente_cpf",
                columnNames = "cpf"
        )
)
@Getter
@Setter
@NoArgsConstructor //Gera os construtores sem argumentos
@AllArgsConstructor //Gera os construtores
@Builder //Gera o padrão de projeto Builder
public class ClienteEntity extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 80) //modela tabela
    private String nomeCompleto;

    @Column(nullable = false, length = 11)
    private String cpf;

    @OneToMany(
            mappedBy = "cliente",
            cascade = CascadeType.ALL,
            orphanRemoval = true // Se uma conta for removida da lista, ela também será removida do banco de dados
    )
    @Builder.Default
    private List<ContaEntity> contas = new ArrayList<>();

    @Column(nullable = false)
    private boolean ativo;
}