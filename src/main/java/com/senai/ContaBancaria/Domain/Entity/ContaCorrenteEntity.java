package com.senai.ContaBancaria.Domain.Entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class ContaCorrenteEntity extends ContaEntity{
    @NotNull
    @Length(min = 1)
    private double limite;

    private long taxa;
}
