package com.senai.ContaBancaria.Domain.Repository;

import com.senai.ContaBancaria.Domain.Entity.GerenteEnity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GerenteRepository extends JpaRepository<GerenteEnity, String> {
    Optional<GerenteEnity> findByEmail(String email);
}
