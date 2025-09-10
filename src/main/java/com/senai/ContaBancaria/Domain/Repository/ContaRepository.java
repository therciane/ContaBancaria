package com.senai.ContaBancaria.Domain.Repository;

import com.senai.ContaBancaria.Domain.Entity.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<ContaEntity, String> {
}
