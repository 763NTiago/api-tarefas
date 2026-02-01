package com.sttalis.missaokids.controller;

import com.sttalis.missaokids.dto.LoginRequest;
import com.sttalis.missaokids.entity.Usuario;
import com.sttalis.missaokids.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthApiController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        System.out.println(">>> API: Recebi login para: " + request.getLogin());

        Usuario usuario = usuarioRepository.findByLogin(request.getLogin()).orElse(null);

        if (usuario != null) {
            System.out.println(">>> API: Usuário encontrado. Verificando senha...");
            if (passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
                System.out.println(">>> API: Senha OK! Logado com sucesso.");
                return ResponseEntity.ok(usuario);
            } else {
                System.out.println(">>> API: Senha incorreta.");
            }
        } else {
            System.out.println(">>> API: Usuário não existe.");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
    }
}