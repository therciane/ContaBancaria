package com.senai.ContaBancaria.Application.Service;
import com.senai.ContaBancaria.Application.DTO.AuthDTO;
import com.senai.ContaBancaria.Domain.Entity.Usuario;
import com.senai.ContaBancaria.Domain.Exceptions.UsuarioNaoEncontradoException;
import com.senai.ContaBancaria.Domain.Repository.UsuarioRepository;
import com.senai.ContaBancaria.Infrastructure.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public String login(AuthDTO.LoginRequest req) {
        Usuario usuario = usuarios.findByEmail(req.email())
                .orElseThrow(() ->  new UsuarioNaoEncontradoException("Usuário não encontrado"));

        if (!encoder.matches(req.senha(), usuario.getSenha())) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        return jwt.generateToken(usuario.getEmail(), usuario.getRole().name());
    }
}
