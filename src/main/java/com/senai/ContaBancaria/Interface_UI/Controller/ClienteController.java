package com.senai.ContaBancaria.Interface_UI.Controller;

import com.senai.ContaBancaria.Application.DTO.ClienteCadastroDTO;
import com.senai.ContaBancaria.Application.DTO.ClienteResponseDTO;
import com.senai.ContaBancaria.Application.Service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController //Cuida das requisições HTTP.Avisa que a classe é um controlador
@RequestMapping("/api/clientes") //Mapeia a rota
@RequiredArgsConstructor     //Gera um construtor com 1 parâmetro para cada campo final

public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity <ClienteResponseDTO> registrarCliente(@RequestBody ClienteCadastroDTO dto) {
        ClienteResponseDTO novoCliente = service.registarClienteOuAnexarConta(dto);

        return ResponseEntity.created(
                URI.create("api/cliente/cpf" + novoCliente.cpf())
        ).body(novoCliente);
    }

}
