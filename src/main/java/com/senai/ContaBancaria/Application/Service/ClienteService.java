package com.senai.ContaBancaria.Application.Service;

import com.senai.ContaBancaria.Application.DTO.ClienteDTO;
import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;
import com.senai.ContaBancaria.Domain.Entity.ContaEntity;
import com.senai.ContaBancaria.Domain.Repository.ClienteRepository;
import com.senai.ContaBancaria.Domain.Repository.ContaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ContaRepository contaRepository;

    @Transactional
    public List<ClienteDTO> listarClientes(){
        return clienteRepository.findAll().stream().map(ClienteDTO::fromEntity).toList();
    }

    //public ClienteEntity criarAluno(){

    //}

    @Transactional
    public ClienteDTO buscarClientePorId(String id){
        return clienteRepository.findById(id).map(ClienteDTO::fromEntity).orElse(null);
    }

    public ClienteDTO salvarCliente(ClienteDTO dto){
        ContaEntity conta = contaRepository.findById(dto.id()).orElse(null);
        ClienteEntity cliente = dto.toEntity();
        ClienteEntity salvar = clienteRepository.save(cliente);
        return ClienteDTO.fromEntity(salvar);
    }

    public ClienteDTO atualizarCliente(String id, ClienteDTO dto){
        Optional <ClienteEntity> clienteExiste = clienteRepository.findById(id);
        if (clienteExiste.isEmpty()) return null;

        ClienteEntity existente = clienteExiste.get();
        existente.setNomeCompleto(dto.nomeCompleto());
        existente.setCpf(dto.cpf());
        existente.setTipoContas(dto.tipContas());

        ClienteEntity atualizada = clienteRepository.save(existente);
        return ClienteDTO.fromEntity(atualizada);
    }



    //Inativar ou deletra eis a questão
}
