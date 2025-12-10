package com.senai.ContaBancaria.Domain.Repository;

import com.senai.ContaBancaria.Domain.Entity.CodigoAutenticacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface CodigoAutenticacaoRepository extends JpaRepository<CodigoAutenticacaoEntity, UUID> {
    Optional<CodigoAutenticacaoEntity> findByClienteIdAndValidadoIsFalse(String clienteId);
}

