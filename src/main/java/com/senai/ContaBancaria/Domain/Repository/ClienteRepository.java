package com.senai.ContaBancaria.Domain.Repository;

import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity, String> {
}
