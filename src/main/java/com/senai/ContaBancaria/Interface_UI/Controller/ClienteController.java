package com.senai.ContaBancaria.Interface_UI.Controller;

import com.senai.ContaBancaria.Application.DTO.ClienteDTO;
import com.senai.ContaBancaria.Application.Service.ClienteService;
import com.senai.ContaBancaria.Application.Service.ContaService;
import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class ClienteController {
    @Autowired
    ClienteService service;
    @Autowired
    ContaService contaService;

    @GetMapping
    public List<ClienteDTO> listarClientes(){
        return service.listarClientes();
    }
    //@PostMapping
    //criar cliente

    @GetMapping("{/id}")
    public ClienteEntity buscarCliente(String id){
        return service.buscarClientePorId(id).toEntity();
    }

    public ClienteEntity atualizarCliente(String id, ClienteDTO dto){
        return service.atualizarCliente(id, dto).toEntity();
    }
}
