package com.senai.ContaBancaria.Domain.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class ContaEntity {
    @Id
    private String id;

    @NotNull
    private int numeroConta;

    @Size(min = 0)
    private double saldo;
}