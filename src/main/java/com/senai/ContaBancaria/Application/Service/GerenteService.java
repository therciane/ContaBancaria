package com.senai.ContaBancaria.Application.Service;

import com.senai.ContaBancaria.Application.DTO.GerenteDTO;
import com.senai.ContaBancaria.Domain.Entity.GerenteEntity;
import com.senai.ContaBancaria.Domain.Exceptions.ValidacaoException;
import com.senai.ContaBancaria.Domain.Repository.GerenteRepository;
import com.senai.ContaBancaria.Domain.Enum.Role;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GerenteService {

    private final GerenteRepository gerenteRepository;
    private final PasswordEncoder encoder;

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    @Transactional(readOnly = true)
    public List<GerenteDTO> listarTodosGerentes() {
        return gerenteRepository.findAll().stream()
                .map(gerente -> {
                    GerenteDTO dto = GerenteDTO.fromEntity(gerente);
                    // censura senha na resposta (segurança + LGPD vibes)
                    return GerenteDTO.builder()
                            .id(dto.id())
                            .nomeCompleto(dto.nomeCompleto())
                            .cpf(dto.cpf())
                            .email(dto.email())
                            .ativo(dto.ativo())
                            .role(dto.role())
                            .senha(null)
                            .build();
                })
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public GerenteDTO cadastrarGerente(GerenteDTO dto) {

        // valida email duplicado
        gerenteRepository.findByEmailAndAtivoTrue(dto.email())
                .ifPresent(x -> {
                    throw new ValidacaoException("Já existe um gerente com esse email");
                });

        GerenteEntity gerente = dto.toEntity();

        // ID nunca deve vir do cliente
        gerente.setId(null);

        gerente.setSenha(encoder.encode(dto.senha()));
        gerente.setRole(Role.GERENTE);
        gerente.setAtivo(true);

        var salvo = gerenteRepository.save(gerente);

        // censurar senha na resposta
        return GerenteDTO.builder()
                .id(salvo.getId())
                .nomeCompleto(salvo.getNomeCompleto())
                .cpf(salvo.getCpf())
                .email(salvo.getEmail())
                .ativo(salvo.isAtivo())
                .role(salvo.getRole())
                .senha(null)
                .build();
    }
}

