package com.senai.ContaBancaria.Domain.Repository;

import com.senai.ContaBancaria.Domain.Entity.PagamentoEntity;
import com.senai.ContaBancaria.Domain.Enum.StatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<PagamentoEntity, String> {

    List<PagamentoEntity> findAllByContaId(String contaId);

    List<PagamentoEntity> findAllByStatusPagamento(StatusPagamento status);
}

