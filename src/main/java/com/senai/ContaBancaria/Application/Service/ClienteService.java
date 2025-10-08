package com.senai.ContaBancaria.Application.Service;

import com.senai.ContaBancaria.Application.DTO.ClienteCadastroDTO;
import com.senai.ContaBancaria.Application.DTO.ClienteResponseDTO;
import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;
import com.senai.ContaBancaria.Domain.Exceptions.ContaMesmoTipoException;
import com.senai.ContaBancaria.Domain.Exceptions.EntidadeNaoEncontradaException;
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
            throw new ContaMesmoTipoException();

        cliente.getContas().add(novaConta);


        return ClienteResponseDTO.fromEntity(repository.save(cliente));
    }

    public List<ClienteResponseDTO> listarClientesAtivos() {
        return repository.findAllByAtivoTrue().stream()
                .map(ClienteResponseDTO::fromEntity)
                .toList();
    }

    public ClienteResponseDTO buscarClienteAtivoPorCpf(String cpf) {
        var cliente = buscarPorCpfClienteAtivo(cpf);
        return ClienteResponseDTO.fromEntity(cliente);
    }

    public ClienteResponseDTO atualizarCliente(String cpf, ClienteCadastroDTO dto) {
        var cliente = buscarPorCpfClienteAtivo(cpf);

        cliente.setNomeCompleto(dto.nomeCompleto());
        cliente.setCpf(dto.cpf());

        return ClienteResponseDTO.fromEntity(repository.save(cliente));
    }

    public void deletarCliente(String cpf) {
        var cliente = buscarPorCpfClienteAtivo(cpf);

        cliente.setAtivo(false);
        cliente.getContas().forEach(c -> c.setAtivo(false));

        repository.save(cliente);
    }

    private ClienteEntity buscarPorCpfClienteAtivo(String cpf) {
        var cliente = repository.findByCpfAndAtivoTrue(cpf)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente")); //Só deleta caso o cliente pedir, caso contrário ele constinua inativo
        return cliente;
    }
}
