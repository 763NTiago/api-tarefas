package com.sttalis.missaokids.config;

import com.sttalis.missaokids.entity.Usuario;
import com.sttalis.missaokids.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CargaDeDados {

    @Bean
    public CommandLineRunner carregarDados(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (repository.count() == 0) {
                Usuario superAdmin = new Usuario();
                superAdmin.setLogin("admin");
                superAdmin.setSenha(passwordEncoder.encode("admin"));
                superAdmin.setPerfil("ROLE_SUPER_ADMIN");
                superAdmin.setNomeExibicao("Super Admin");
                superAdmin.setFamiliaId(null);

                repository.save(superAdmin);
                System.out.println(">>> Usuário SUPER ADMIN criado com sucesso!");
            }
        };
    }
}