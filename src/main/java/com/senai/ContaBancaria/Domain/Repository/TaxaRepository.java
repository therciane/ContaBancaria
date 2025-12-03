package com.senai.ContaBancaria.Domain.Repository;

import com.senai.ContaBancaria.Domain.Entity.TaxaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaxaRepository extends JpaRepository<TaxaEntity, UUID> {
    Optional<TaxaEntity> findByDescricao(String descricao);
}
