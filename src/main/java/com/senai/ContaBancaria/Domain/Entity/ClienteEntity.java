package com.senai.ContaBancaria.Domain.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data //Ela não gera construtores
@AllArgsConstructor //Gera os construtores
@NoArgsConstructor //Gera construtores sem argumentos
@Builder //?
//Modela banco de dados
@Table (
        name = "cliente",
        uniqueConstraints = @UniqueConstraint(name = "uk_cliente_cpf", columnNames = "cpf") //Chave unica impedindo nomes iuais

)
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 80) //modela tabela
    private String nomeCompleto;

    @Column(nullable = false, length = 11)
    private String cpf;

    @OneToMany (mappedBy = "cliente", cascade = CascadeType.ALL) //Relacionamento com banco de dados.
    private List <ContaEntity> contas;

    @Column(nullable = false)
    private boolean ativo;
}