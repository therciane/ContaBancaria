package com.senai.ContaBancaria.Domain.Repository;

import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//JPA consegue interpretar pelo nomeCompleto do metodo e fazer um pedido no SQL
@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, String> {

   Optional <ClienteEntity> findByCpfAndAtivoTrue(String cpf); // Método para encontrar um cliente pelo CPF dentro do BD

   List <ClienteEntity> findAllByAtivoTrue();
}
