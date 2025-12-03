package com.senai.ContaBancaria.Domain.Entity;

import com.senai.ContaBancaria.Domain.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected String id;

    @NotBlank
    @Column(nullable = false)
    protected String nomeCompleto;

    @NotBlank
    @Column(nullable = false, unique = true, length = 14)
    protected String cpf;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    protected String email;

    @Column(nullable = false)
    protected boolean ativo = true;

    @NotBlank
    @Column(nullable = false)
    protected String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected Role role;
}
