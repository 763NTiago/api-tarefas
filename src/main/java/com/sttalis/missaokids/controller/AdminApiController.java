package com.sttalis.missaokids.controller;

import com.sttalis.missaokids.dto.FilhoRequest;
import com.sttalis.missaokids.dto.RecompensaRequest;
import com.sttalis.missaokids.dto.TarefaRequest;
import com.sttalis.missaokids.entity.Recompensa;
import com.sttalis.missaokids.entity.Tarefa;
import com.sttalis.missaokids.entity.Usuario;
import com.sttalis.missaokids.repository.RecompensaRepository;
import com.sttalis.missaokids.repository.TarefaRepository;
import com.sttalis.missaokids.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminApiController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TarefaRepository tarefaRepository;
    @Autowired
    private RecompensaRepository recompensaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- ROTA 1: CADASTRAR FILHO ---
    @PostMapping("/filhos")
    public ResponseEntity<?> adicionarFilho(@RequestBody FilhoRequest request) {
        Usuario pai = usuarioRepository.findById(request.getPaiId()).orElse(null);
        if (pai == null) return ResponseEntity.badRequest().body("Pai não encontrado");

        // Cria família se não existir
        if (pai.getFamiliaId() == null || pai.getFamiliaId().isEmpty()) {
            pai.setFamiliaId(UUID.randomUUID().toString());
            usuarioRepository.save(pai);
        }

        if (usuarioRepository.findByLogin(request.getLogin()).isPresent()) {
            return ResponseEntity.badRequest().body("Login já existe!");
        }

        Usuario filho = new Usuario();
        filho.setNomeExibicao(request.getNome());
        filho.setLogin(request.getLogin());
        filho.setSenha(passwordEncoder.encode(request.getSenha()));
        filho.setPerfil("ROLE_CRIANCA");
        filho.setFamiliaId(pai.getFamiliaId());
        filho.setFotoBase64(request.getFotoBase64());

        usuarioRepository.save(filho);
        return ResponseEntity.ok("Filho cadastrado com sucesso!");
    }

    // --- ROTA 2: CADASTRAR TAREFA ---
    @PostMapping("/tarefas")
    public ResponseEntity<?> adicionarTarefa(@RequestBody TarefaRequest request) {
        Usuario pai = usuarioRepository.findById(request.getPaiId()).orElse(null);
        if (pai == null) return ResponseEntity.badRequest().body("Pai não encontrado");

        if (pai.getFamiliaId() == null) {
            pai.setFamiliaId(UUID.randomUUID().toString());
            usuarioRepository.save(pai);
        }

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(request.getTitulo());
        tarefa.setValorEstrelas(request.getValorEstrelas());
        tarefa.setFamiliaId(pai.getFamiliaId());

        if (request.getAtribuidaParaId() != null) {
            tarefa.setAtribuidaParaId(request.getAtribuidaParaId());
        }

        tarefaRepository.save(tarefa);
        return ResponseEntity.ok("Tarefa criada com sucesso!");
    }

    // --- ROTA 3: CADASTRAR RECOMPENSA ---
    @PostMapping("/recompensas")
    public ResponseEntity<?> adicionarRecompensa(@RequestBody RecompensaRequest request) {
        Usuario pai = usuarioRepository.findById(request.getPaiId()).orElse(null);
        if (pai == null) return ResponseEntity.badRequest().body("Pai não encontrado");

        Recompensa recompensa = new Recompensa();
        recompensa.setTitulo(request.getTitulo());
        recompensa.setCustoEstrelas(request.getCustoEstrelas());
        recompensa.setFamiliaId(pai.getFamiliaId());

        recompensaRepository.save(recompensa);
        return ResponseEntity.ok("Recompensa criada com sucesso!");
    }

    // --- ROTA 4: LISTAR FILHOS ---
    @GetMapping("/filhos/{paiId}")
    public ResponseEntity<?> listarFilhos(@PathVariable Long paiId) {
        Usuario pai = usuarioRepository.findById(paiId).orElse(null);
        if (pai == null || pai.getFamiliaId() == null) return ResponseEntity.ok(List.of());

        return ResponseEntity.ok(usuarioRepository.findByFamiliaIdAndPerfil(pai.getFamiliaId(), "ROLE_CRIANCA"));
    }
}