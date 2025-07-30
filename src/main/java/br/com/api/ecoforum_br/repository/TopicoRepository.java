package br.com.api.ecoforum_br.repository;


import br.com.api.ecoforum_br.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    // Exemplo de método personalizado se você precisar filtrar por título
    // Page<Topico> findByTituloContainingIgnoreCase(String titulo, Pageable paginacao);
}
