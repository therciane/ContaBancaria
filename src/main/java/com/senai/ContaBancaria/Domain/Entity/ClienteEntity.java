package com.senai.ContaBancaria.Domain.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Size(max = 5)
    private String id;

    @NotBlank(message = "Está area não pode ficar em branco")
    @Size(min = 3, max = 80)
    private String nomeCompleto;

    @NotNull(message = "Area Obrigatoria")
    @Column(nullable = false, length = 11)
    private long cpf;

    @NotBlank(message = "Area que indica qual a sua conta")
    private List <String> tipoContas;
}
