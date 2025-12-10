package com.senai.ContaBancaria.Application.Service;

import com.senai.ContaBancaria.Application.DTO.DispositivoIotDTO;
import com.senai.ContaBancaria.Domain.Entity.ClienteEntity;
import com.senai.ContaBancaria.Domain.Exceptions.EntidadeNaoEncontradaException;
import com.senai.ContaBancaria.Domain.Repository.ClienteRepository;
import com.senai.ContaBancaria.Domain.Repository.DispositivoIotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DispositivoIotService {

    private final DispositivoIotRepository repository;
    private final ClienteRepository clienteRepository;

    @Transactional
    public DispositivoIotDTO salvar(DispositivoIotDTO dto) {

        ClienteEntity cliente = null;
        if (dto.clienteId() != null) {
            cliente = clienteRepository.findById(dto.clienteId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente"));
        }

        var entity = dto.toEntity(cliente);
        var salvo = repository.save(entity);

        return DispositivoIotDTO.fromEntity(salvo);
    }

    @Transactional(readOnly = true)
    public List<DispositivoIotDTO> listar() {
        return repository.findAll()
                .stream()
                .map(DispositivoIotDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public DispositivoIotDTO buscarPorId(UUID id) {
        var device = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Dispositivo IoT"));
        return DispositivoIotDTO.fromEntity(device);
    }

    @Transactional
    public DispositivoIotDTO atualizar(UUID id, DispositivoIotDTO dto) {

        var existente = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Dispositivo IoT"));

        ClienteEntity cliente = null;

        if (dto.clienteId() != null) {
            cliente = clienteRepository.findById(dto.clienteId())
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente"));
        }

        existente.setCodigoSerial(dto.codigoSerial());
        existente.setModelo(dto.modelo());
        existente.setAtivo(dto.ativo());
        existente.setCliente(cliente);

        var atualizado = repository.save(existente);

        return DispositivoIotDTO.fromEntity(atualizado);
    }

    @Transactional
    public void deletar(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Dispositivo IoT");
        }
        repository.deleteById(id);
    }
}

