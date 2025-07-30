package br.com.api.ecoforum_br.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TopicoRequestDTO(
        @NotBlank  (message = "O título não pode estar em branco")
        @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
        String titulo,

        @NotBlank (message = "A mensagem não pode estar em branco")
        @Size(max = 500, message = "A mensagem deve ter no máximo 500 caracteres")
        String mensagem,

        @NotNull (message = "O ID do curso não pode ser nulo")
        Long cursoId
       // autorId NÃO é incluído aqui, pois é obtido do usuário autenticado"
) {
}
