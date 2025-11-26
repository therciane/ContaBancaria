package com.senai.ContaBancaria.Domain.Repository;

import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;
import com.senai.ContaBancaria.Domain.Entity.DispositivoIotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DispositivoIotRepository  extends JpaRepository<DispositivoIotEntity, UUID>  {
}
