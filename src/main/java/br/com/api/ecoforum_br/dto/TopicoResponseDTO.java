package br.com.api.ecoforum_br.dto;

import br.com.api.ecoforum_br.model.Topico;

import java.time.LocalDateTime;

public record TopicoResponseDTO(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        String status,
        String autorLogin, // Mudado para autorLogin para consistência com LoginRequestDTO
        String cursoNome
) {
    public TopicoResponseDTO(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor() != null ? topico.getAutor().getLogin() : null,
                topico.getCurso() != null ? topico.getCurso().getNome() : null
        );
    }
}

