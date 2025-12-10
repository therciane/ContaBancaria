package com.senai.ContaBancaria.Interface_UI.Controller;

import com.senai.ContaBancaria.Application.DTO.ClienteCadastroDTO;
import com.senai.ContaBancaria.Application.DTO.ClienteResponseDTO;
import com.senai.ContaBancaria.Application.Service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> registrarCliente(
            @Valid @RequestBody ClienteCadastroDTO dto
    ) {
        ClienteResponseDTO novoCliente = service.registrarClienteOuAdicionarConta(dto);

        return ResponseEntity
                .created(URI.create("/api/clientes/cpf/" + novoCliente.cpf()))
                .body(novoCliente);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientesAtivos() {
        return ResponseEntity.ok(service.listarClientesAtivos());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteResponseDTO> buscarClienteAtivoPorCpf(
            @PathVariable String cpf
    ) {
        return ResponseEntity.ok(service.buscarClienteAtivoPorCpf(cpf));
    }

    @PutMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(
            @PathVariable String cpf,
            @Valid @RequestBody ClienteCadastroDTO dto
    ) {
        return ResponseEntity.ok(service.atualizarCliente(cpf, dto));
    }

    @DeleteMapping("/cpf/{cpf}")
    public ResponseEntity<Void> deletarCliente(
            @PathVariable String cpf
    ) {
        service.deletarCliente(cpf);
        return ResponseEntity.noContent().build();
    }
}