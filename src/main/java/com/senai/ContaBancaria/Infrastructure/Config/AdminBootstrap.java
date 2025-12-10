package com.senai.ContaBancaria.Infrastructure.Config;

import com.senai.ContaBancaria.Domain.Entity.GerenteEntity;
import com.senai.ContaBancaria.Domain.Enum.Role;
import com.senai.ContaBancaria.Domain.Repository.GerenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminBootstrap implements CommandLineRunner {

    private final GerenteRepository gerenteRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${sistema.admin.email}")
    private String adminEmail;

    @Value("${sistema.admin.senha}")
    private String adminSenha;

    @Override
    public void run(String... args) {

        gerenteRepository.findByEmailAndAtivoTrue(adminEmail).ifPresentOrElse(

                existente -> {
                    if (!existente.isAtivo()) {
                        existente.setAtivo(true);
                        gerenteRepository.save(existente);
                    }
                },

                () -> {
                    GerenteEntity admin = GerenteEntity.builder()
                            .nomeCompleto("Administrador do Sistema")
                            .email(adminEmail)
                            .cpf("000.000.000-00")
                            .senha(passwordEncoder.encode(adminSenha))
                            .ativo(true)
                            .role(Role.ADMIN)
                            .build();

                    gerenteRepository.save(admin);

                    System.out.println("\n⚡ ADMIN PROVISÓRIO CRIADO:");
                    System.out.println("   Email: " + adminEmail);
                    System.out.println("   Role: ADMIN\n");
                }
        );
    }
}
