package com.senai.ContaBancaria.Domain.Repository;

import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//JPA consegue interpretar pelo nome do metodo e fazer um pedido no SQL
public interface ClienteRepository extends JpaRepository<ClienteEntity, String> {
    ClienteEntity findByCpfAndAtivoTrue(String cpf); // Método para encontrar um cliente pelo CPF dentro do BD

}
