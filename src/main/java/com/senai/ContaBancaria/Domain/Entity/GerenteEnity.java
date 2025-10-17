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
@Table(name="gerentes")
public class GerenteEnity extends Usuario {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="gerentes_clientes", joinColumns=@JoinColumn(name="gerentes_id"))
    @Column(name="Usuario")
    private List<String > listaDeClientes;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="gerentes_bancos", joinColumns=@JoinColumn(name="gerentes_id"))
    @Column(name="bancos")
    private List<String> listaDeBancos;
}
