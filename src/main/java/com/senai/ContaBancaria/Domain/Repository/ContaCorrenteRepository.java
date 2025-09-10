package com.senai.ContaBancaria.Domain.Repository;

import com.senai.ContaBancaria.Domain.Entity.ContaCorrenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaCorrenteRepository extends JpaRepository<ContaCorrenteEntity, String> {
}
