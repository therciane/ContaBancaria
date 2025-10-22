package com.senai.ContaBancaria.Application.Service;

import com.senai.ContaBancaria.Application.DTO.GerenteDTO;
import com.senai.ContaBancaria.Domain.Entity.GerenteEnity;
import com.senai.ContaBancaria.Domain.Repository.GerenteRepository;
import com.senai.ContaBancaria.Domain.Enum.Role;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GerenteService {
    private final GerenteRepository gerenteRepository;

    private final PasswordEncoder encoder;

    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    public List<GerenteDTO> listarTodosGerentes() {
        return gerenteRepository.findAll().stream()
                .map(GerenteDTO::fromEntity)
                .toList();
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    public GerenteDTO cadastrarGerentes(GerenteDTO dto) {
        GerenteEnity entity = dto.toEntity();
        entity.setSenha(encoder.encode(dto.senha()));
        entity.setRole(Role.GERENTE);
        gerenteRepository.save(entity);
        return GerenteDTO.fromEntity(entity);
    }
}
