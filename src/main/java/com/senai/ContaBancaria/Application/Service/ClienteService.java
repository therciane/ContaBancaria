package com.senai.ContaBancaria.Application.Service;

import com.senai.ContaBancaria.Application.DTO.ClienteCadastroDTO;
import com.senai.ContaBancaria.Application.DTO.ClienteResponseDTO;
import com.senai.ContaBancaria.Domain.Repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//Isso que é um bean de serviço
//Responsável pela lógica de negócio
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    //Criar clente
    public ClienteResponseDTO registrarClienteOuAnexarConta(ClienteCadastroDTO dto) {

        //var cria variavel sem ter que definir o tipo
        var cliente = repository.findByCpfAndAtivoTrue(dto.cpf()).orElseGet(() -> repository.save(dto.toEntity()));

        var contas = cliente.getContas();
        var novaConta = dto.toContaEntity(cliente);
        return null;
    }
}
