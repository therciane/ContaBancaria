package com.senai.ContaBancaria.Domain.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "gerente")
public class GerenteEntity extends Usuario {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "gerente_clientes",
            joinColumns = @JoinColumn(name = "gerente_id")
    )
    @Column(name = "cliente")
    private List<String> listaDeClientes;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "gerente_bancos",
            joinColumns = @JoinColumn(name = "gerente_id")
    )
    @Column(name = "banco")
    private List<String> listaDeBancos;
}
