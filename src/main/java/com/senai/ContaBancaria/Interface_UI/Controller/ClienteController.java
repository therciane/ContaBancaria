package com.senai.ContaBancaria.Interface_UI.Controller;

import com.senai.ContaBancaria.Application.DTO.ClienteCadastroDTO;
import com.senai.ContaBancaria.Application.DTO.ClienteResponseDTO;
import com.senai.ContaBancaria.Application.Service.ClienteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController //Cuida das requisições HTTP.Avisa que a classe é um controlador
@RequestMapping("/api/clientes") //Mapeia a rota
@RequiredArgsConstructor     //Gera um construtor com 1 parâmetro para cada campo final
@Transactional

public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity <ClienteResponseDTO> registrarCliente(@RequestBody ClienteCadastroDTO dto) {
        ClienteResponseDTO novoCliente = service.registarClienteOuAnexarConta(dto);

        return ResponseEntity.created(
                URI.create("api/cliente/cpf" + novoCliente.cpf())
        ).body(novoCliente);
    }

    @GetMapping
    public ResponseEntity <List <ClienteResponseDTO>> listarClientesAtivos(){

        return ResponseEntity.ok(service.listarClientesAtivos());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteResponseDTO> buscarClienteAtivoPorCpf(@PathVariable String cpf){ //Caminho que será passado na URL (endpoint)
        return ResponseEntity.ok(service.buscarClienteAtivoPorCpf(cpf));
    }

    @PutMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(@PathVariable String cpf, @RequestBody ClienteCadastroDTO dto){
        return ResponseEntity.ok(service.atualizarCliente(cpf, dto));
    }

    @DeleteMapping("/cpf/{cpf}")
    public ResponseEntity<Void> deletarCliente(@PathVariable String cpf) {
        service.deletarCliente(cpf);
        return ResponseEntity.noContent().build();
    }
}
