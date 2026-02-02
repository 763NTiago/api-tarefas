package com.sttalis.missaokids.config;

import com.sttalis.missaokids.entity.Usuario;
import com.sttalis.missaokids.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CargaDeDados {

    // Lê as variáveis do application.properties (que vêm do .env ou Docker)
    @Value("${app.admin.login:admin}") // Se não achar, usa "admin"
    private String adminLogin;

    @Value("${app.admin.senha:admin}") // Se não achar, usa "admin"
    private String adminSenha;

    @Bean
    public CommandLineRunner carregarDados(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (repository.count() == 0) {
                Usuario superAdmin = new Usuario();

                superAdmin.setLogin(adminLogin);
                superAdmin.setSenha(passwordEncoder.encode(adminSenha));

                superAdmin.setPerfil("ROLE_SUPER_ADMIN");
                superAdmin.setNomeExibicao("Super Admin (" + adminLogin + ")");
                superAdmin.setFamiliaId(null);

                repository.save(superAdmin);
                System.out.println(">>> Usuário SUPER ADMIN criado: " + adminLogin);
            }
        };
    }
}