package br.com.api.ecoforum_br.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Indica que esta é uma classe de configuração Spring
@EnableWebSecurity // Habilita a segurança web no Spring
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter; // Filtro personalizado para JWT

    @Bean // Marca o método como um bean gerenciado pelo Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable()) // Desabilita CSRF (comum para APIs stateless)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sessão stateless (não mantém estado)
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll(); // Permite POST em /login sem autenticação
                    // req.requestMatchers(HttpMethod.POST, "/usuarios").permitAll(); // Se você tiver um endpoint de cadastro de usuário, pode liberar
                    req.anyRequest().authenticated(); // Todas as outras requisições exigem autenticação
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona o filtro JWT antes do filtro padrão de autenticação
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager(); // Expõe o AuthenticationManager como um bean
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Define o algoritmo de hash de senha (BCrypt)
    }
}
