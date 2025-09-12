package com.senai.ContaBancaria.Domain.Entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ContaEntity {
    @NotNull
    private int numeroConta;

    @Size(min = 0)
    private double saldo;
}