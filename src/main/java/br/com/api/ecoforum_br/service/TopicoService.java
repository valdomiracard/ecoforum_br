package br.com.api.ecoforum_br.service;

import br.com.api.ecoforum_br.dto.TopicoRequestDTO;
import br.com.api.ecoforum_br.model.Curso;
import br.com.api.ecoforum_br.model.Topico;
import br.com.api.ecoforum_br.model.Usuario;
import br.com.api.ecoforum_br.repository.CursoRepository;
import br.com.api.ecoforum_br.repository.TopicoRepository;
import br.com.api.ecoforum_br.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException; // Para exceções de "não encontrado"

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    public Topico criartopicos(TopicoRequestDTO dadosTopico, Long idAutor) {
        Usuario autor = usuarioRepository.findById(idAutor)
                .orElseThrow(() -> new NoSuchElementException("Autor não encontrado com ID: " + idAutor));

        Curso curso = cursoRepository.findById(dadosTopico.cursoId())
                .orElseThrow(() -> new NoSuchElementException("Curso não encontrado com ID: " + dadosTopico.cursoId()));

        Topico topico = new Topico();
        topico.setTitulo(dadosTopico.titulo());
        topico.setMensagem(dadosTopico.mensagem());
        topico.setDataCriacao(LocalDateTime.now());
        topico.setStatus("ABERTO");
        topico.setAutor(autor);
        topico.setCurso(curso);

        return topicoRepository.save(topico);
    }

    public Page<Topico> listarTopicos(Pageable paginacao) {
        return topicoRepository.findAll(paginacao);
    }

    public Topico buscarTopicoPorId(Long id) {
        return topicoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tópico não encontrado com ID: " + id));
    }

    @Transactional
    public void deletarTopico(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new NoSuchElementException("Tópico não encontrado para exclusão com ID: " + id);
        }
        topicoRepository.deleteById(id);
    }

    @Transactional
    public Topico atualizarTopico(Long id, TopicoRequestDTO dadosAtualizacao) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tópico não encontrado para atualização com ID: " + id));

        if (dadosAtualizacao.titulo() != null && !dadosAtualizacao.titulo().isBlank()) {
            topico.setTitulo(dadosAtualizacao.titulo());
        }
        if (dadosAtualizacao.mensagem() != null && !dadosAtualizacao.mensagem().isBlank()) {
            topico.setMensagem(dadosAtualizacao.mensagem());
        }

        return topico;
    }

}

