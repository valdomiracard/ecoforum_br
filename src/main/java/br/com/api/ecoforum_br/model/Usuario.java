package br.com.api.ecoforum_br.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios") // Mapeia para a tabela 'usuarios'
@Entity(name = "Usuario") // Define como entidade JPA
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails { // Implementa UserDetails para o Spring Security


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String senha; // Senha do usuário (DEVE ser armazenada criptografada)


    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    // --- Implementação dos métodos de UserDetails ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Conta nunca expira (para este projeto)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Conta nunca bloqueia (para este projeto)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Credenciais nunca expiram (para este projeto)
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getLogin() {
        return login;
    }

    public Long getId() {
        return id;
    }
}

