package com.senai.ContaBancaria.Domain.Entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class ContaPoupancaEntity extends ContaEntity {
    private long rendimento;
}
