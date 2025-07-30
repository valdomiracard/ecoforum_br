package br.com.api.ecoforum_br.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank // Garante que o campo não seja nulo ou vazio/apenas espaços
        String login,
        @NotBlank
        String senha
) {
}