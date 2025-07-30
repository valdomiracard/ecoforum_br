package br.com.api.ecoforum_br.repository;


import br.com.api.ecoforum_br.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Método para buscar um usuário pelo login (usado pelo Spring Security)
    Usuario findByLogin(String login); // Spring Data JPA automaticamente implementa este método
}

