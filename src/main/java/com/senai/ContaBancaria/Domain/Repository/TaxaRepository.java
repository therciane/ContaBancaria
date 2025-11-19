package com.senai.ContaBancaria.Domain.Repository;

import com.senai.ContaBancaria.Domain.Entity.TaxaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxaRepository extends JpaRepository<TaxaEntity, String> {
}
