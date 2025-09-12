package com.senai.ContaBancaria.Domain.Entity;

import jakarta.validation.constraints.NotNull;

public class ContaEntity {
    @NotNull
    private int numeroConta;
    private double saldo;
}
