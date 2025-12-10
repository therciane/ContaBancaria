package com.senai.ContaBancaria.Application.Service;

import com.senai.ContaBancaria.Application.DTO.ClienteCadastroDTO;
import com.senai.ContaBancaria.Application.DTO.ClienteResponseDTO;
import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;
import com.senai.ContaBancaria.Domain.Exceptions.ContaMesmoTipoException;
import com.senai.ContaBancaria.Domain.Exceptions.EntidadeNaoEncontradaException;
import com.senai.ContaBancaria.Domain.Repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//Isso que é um bean de serviço
//Responsável pela lógica de negócio
@Service
@RequiredArgsConstructor
@Transactional
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteResponseDTO registrarClienteOuAdicionarConta(ClienteCadastroDTO dto) {

        // 1 — Buscar ou criar
        ClienteEntity cliente = repository.findByCpfAndAtivoTrue(dto.cpf())
                .orElseGet(() -> {
                    var novo = dto.toEntity();
                    return repository.save(novo);
                });

        // 2 — Criar conta a partir do DTO
        var novaConta = dto.contaDTO().toEntity(cliente);

        // 3 — Checar se já existe conta ativa do mesmo tipo
        boolean jaTemTipo = cliente.getContas().stream()
                .anyMatch(conta -> conta.getTipoConta().equals(novaConta.getTipoConta()) && conta.isAtivo());

        if (jaTemTipo)
            throw new ContaMesmoTipoException();

        // 4 — Adicionar conta e salvar
        cliente.getContas().add(novaConta);

        return ClienteResponseDTO.fromEntity(repository.save(cliente));
    }

    public List<ClienteResponseDTO> listarClientesAtivos() {
        return repository.findAllByAtivoTrue().stream()
                .map(ClienteResponseDTO::fromEntity)
                .toList();
    }

    public ClienteResponseDTO buscarClienteAtivoPorCpf(String cpf) {
        return ClienteResponseDTO.fromEntity(buscarPorCpfClienteAtivo(cpf));
    }

    public ClienteResponseDTO atualizarCliente(String cpf, ClienteCadastroDTO dto) {

        var cliente = buscarPorCpfClienteAtivo(cpf);

        cliente.setNomeCompleto(dto.nomeCompleto());

        // Atualizar CPF é algo delicado, mas vou manter porque seu professor deixa
        cliente.setCpf(dto.cpf());

        return ClienteResponseDTO.fromEntity(repository.save(cliente));
    }

    public void deletarCliente(String cpf) {

        var cliente = buscarPorCpfClienteAtivo(cpf);

        cliente.setAtivo(false);

        cliente.getContas().forEach(conta -> conta.setAtivo(false));

        repository.save(cliente);
    }

    private ClienteEntity buscarPorCpfClienteAtivo(String cpf) {
        return repository.findByCpfAndAtivoTrue(cpf)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente"));
    }
}


