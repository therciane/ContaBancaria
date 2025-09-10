package com.senai.ContaBancaria.Domain.Repository;

import com.senai.ContaBancaria.Domain.Entity.ContaPoupancaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaPoupancaRepository extends JpaRepository<ContaPoupancaEntity, String> {
}
