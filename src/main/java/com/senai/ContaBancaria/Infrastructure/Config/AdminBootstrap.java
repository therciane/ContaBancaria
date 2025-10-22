package com.senai.ContaBancaria.Infrastructure.Config;

import com.senai.ContaBancaria.Domain.Entity.GerenteEnity;
import com.senai.ContaBancaria.Domain.Enum.Role;
import com.senai.ContaBancaria.Domain.Repository.GerenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminBootstrap implements CommandLineRunner{

    private final GerenteRepository gerenteRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${sistema.admin.email}")
    private String adminEmail;

    @Value("${sistema.admin.senha}")
    private String adminSenha;

    @Override
    public void run(String... args) {
        gerenteRepository.findByEmail(adminEmail).ifPresentOrElse(
                prof -> {
                    if (!prof.isAtivo()) {
                        prof.setAtivo(true);
                        gerenteRepository.save(prof);
                    }
                },
                () -> {
                    GerenteEnity admin = GerenteEnity.builder()
                            .nomeCompleto("Administrador Provisório")
                            .email(adminEmail)
                            .cpf("000.000.000-00")
                            .senha(passwordEncoder.encode(adminSenha))
                            .role(Role.ADMIN)
                            .build();
                    gerenteRepository.save(admin);
                    System.out.println("⚡ Usuário admin provisório criado: " + adminEmail);
                }
        );
    }
}
