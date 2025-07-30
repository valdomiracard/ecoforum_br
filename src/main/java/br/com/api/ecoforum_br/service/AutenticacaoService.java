package br.com.api.ecoforum_br.service;

import br.com.api.ecoforum_br.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Marca como um serviço Spring
public class AutenticacaoService implements UserDetailsService { // Implementa UserDetailsService

    @Autowired
    private UsuarioRepository usuarioRepository; // Repositório para buscar usuários

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário pelo login. Se não encontrar, lança exceção.
        // O método findByLogin retorna um UserDetails porque Usuario implementa UserDetails
        return usuarioRepository.findByLogin(username);
    }
}

