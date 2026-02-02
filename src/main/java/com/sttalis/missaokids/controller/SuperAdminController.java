package com.sttalis.missaokids.controller;

import com.sttalis.missaokids.dto.FilhoRequest;
import com.sttalis.missaokids.entity.Usuario;
import com.sttalis.missaokids.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/super")
public class SuperAdminController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/criar-pai")
    public ResponseEntity<?> criarPai(@RequestBody FilhoRequest request) {

        if (usuarioRepository.findByLogin(request.getLogin()).isPresent()) {
            return ResponseEntity.badRequest().body("Erro: Login já existe!");
        }

        Usuario pai = new Usuario();
        pai.setNomeExibicao(request.getNome());
        pai.setLogin(request.getLogin());
        pai.setSenha(passwordEncoder.encode(request.getSenha()));

        pai.setPerfil("ROLE_ADMIN");
        pai.setFamiliaId(UUID.randomUUID().toString());

        usuarioRepository.save(pai);

        return ResponseEntity.ok("Pai '" + pai.getNomeExibicao() + "' criado com a família ID: " + pai.getFamiliaId());
    }

    @GetMapping("/usuarios")
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }
}