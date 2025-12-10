package com.senai.ContaBancaria.Infrastructure.Security;

import com.senai.ContaBancaria.Domain.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        var usuario = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado ou inativo."));

        String role = "ROLE_" + usuario.getRole().name();

        return User
                .withUsername(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(usuario.getRole().name()) // já adiciona ROLE_ automaticamente
                .accountLocked(!usuario.isAtivo())
                .disabled(!usuario.isAtivo())
                .build();
    }
}
