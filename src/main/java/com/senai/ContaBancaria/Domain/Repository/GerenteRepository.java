package com.senai.ContaBancaria.Domain.Repository;

import com.senai.ContaBancaria.Domain.Entity.GerenteEnity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface GerenteRepository extends JpaRepository<GerenteEnity, String> {
    Optional<GerenteEnity> findByEmail(String email);
}
