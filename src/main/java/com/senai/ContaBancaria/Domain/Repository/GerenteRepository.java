package com.senai.ContaBancaria.Domain.Repository;

import com.senai.ContaBancaria.Domain.Entity.GerenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface GerenteRepository extends JpaRepository<GerenteEntity, String> {
    Optional<GerenteEntity> findByEmail(String email);
}
