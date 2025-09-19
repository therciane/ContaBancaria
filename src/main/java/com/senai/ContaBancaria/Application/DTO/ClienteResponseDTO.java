package com.senai.ContaBancaria.Application.DTO;

import java.util.List;

public record ClienteResponseDTO(
        String id,
        String nomeCompleto,
        String cpf,
        List <ContaResumoDTO> contas //Lista porque um cliente pode ter mais de uma conta
) {
}
