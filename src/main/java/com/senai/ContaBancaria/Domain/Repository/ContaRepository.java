package com.senai.ContaBancaria.Domain.Repository;

import com.senai.ContaBancaria.Domain.Entity.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<ContaEntity, String> {

    List<ContaEntity> findAllByAtivoTrue();

    Optional<ContaEntity> findByNumeroAndAtivoTrue(String numero);

    boolean existsByNumero(String numero); // validações de criação
}

