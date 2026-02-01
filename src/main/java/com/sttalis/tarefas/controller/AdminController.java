package com.sttalis.tarefas.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sttalis.tarefas.entity.Usuario;
import com.sttalis.tarefas.repository.UsuarioRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "admin/dashboard";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        
        if (usuario.getPerfil() == null || usuario.getPerfil().isEmpty()) {
            usuario.setPerfil("ROLE_RESPONSAVEL");
        }
        
        usuario.setResponsavel(null);

        usuarioRepository.save(usuario);
        return "redirect:/admin"; 
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return "redirect:/admin";
    }
}