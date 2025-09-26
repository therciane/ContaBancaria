package com.senai.ContaBancaria.Application.Service;

import com.senai.ContaBancaria.Application.DTO.ClienteCadastroDTO;
import com.senai.ContaBancaria.Application.DTO.ClienteResponseDTO;
import com.senai.ContaBancaria.Domain.Repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

//Isso que é um bean de serviço
//Responsável pela lógica de negócio
@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteResponseDTO registarClienteOuAnexarConta(ClienteCadastroDTO dto) {

        var cliente = repository.findByCpfAndAtivoTrue(dto.cpf()).orElseGet(
                () -> repository.save(dto.toEntity())
        );

        var contas = cliente.getContas();
        var novaConta = dto.contaDTO().toEntity(cliente);

        boolean jaTemTipo = contas.stream()
                .anyMatch(c -> c.getClass().equals(novaConta.getClass()) && c.isAtivo());

        if(jaTemTipo)
            throw new RuntimeException("Cliente já possui uma conta ativa deste tipo.");

        cliente.getContas().add(novaConta);


        return ClienteResponseDTO.fromEntity(repository.save(cliente));
    }

    public List<ClienteResponseDTO> listarClientesAtivos() {
        return repository.findAllByAtivoTrue().stream()
                .map(ClienteResponseDTO::fromEntity)
                .toList();
    }

    public ClienteResponseDTO buscarClienteAtivoPorCpf(String cpf) {
        var cliente = repository.findByCpfAndAtivoTrue(cpf) //Aqui dispensa o uso de if e else, já que se ele não encontrar, já lança a exceção
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return ClienteResponseDTO.fromEntity(cliente);
    }

    public ClienteResponseDTO atualizarCliente(String cpf, ClienteCadastroDTO dto) {
        var cliente = repository.findByCpfAndAtivoTrue(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.setNomeCompleto(dto.nomeCompleto());
        cliente.setCpf(dto.cpf());

        return ClienteResponseDTO.fromEntity(repository.save(cliente));
    }

    public void deletarCliente(String cpf) {
        var cliente = repository.findByCpfAndAtivoTrue(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado")); //Só deleta caso o o cliente pedir, caso contrário ele constinua inativo

        cliente.setAtivo(false);
        cliente.getContas().forEach(c -> c.setAtivo(false));

        repository.save(cliente);
    }
}
