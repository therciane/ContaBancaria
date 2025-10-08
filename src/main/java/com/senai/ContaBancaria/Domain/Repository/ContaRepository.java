package com.senai.ContaBancaria.Domain.Repository;

import com.senai.ContaBancaria.Domain.Entity.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContaRepository extends JpaRepository<ContaEntity, String> {
    List <ContaEntity> findAllByAtivoTrue();

    Optional <ContaEntity> findByNumeroAndAtivoTrue(String numero);
}
