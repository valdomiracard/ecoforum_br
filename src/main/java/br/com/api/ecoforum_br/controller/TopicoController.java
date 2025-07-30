package br.com.api.ecoforum_br.controller;

import java.net.URI;

import br.com.api.ecoforum_br.dto.TopicoRequestDTO;
import br.com.api.ecoforum_br.dto.TopicoResponseDTO;
import br.com.api.ecoforum_br.model.Topico;
import br.com.api.ecoforum_br.model.Usuario;
import br.com.api.ecoforum_br.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


    @RestController
    @RequestMapping("/topicos")
    public class TopicoController {

        @Autowired
        private TopicoService topicoService;

        @PostMapping
        public ResponseEntity<TopicoResponseDTO> criarTopico(
                @RequestBody @Valid TopicoRequestDTO dadosTopico,
                UriComponentsBuilder uriBuilder,
                @AuthenticationPrincipal UserDetails currentUser // Obtém o utilizador autenticado
        ) {
            Long idAutor = null;
            if (currentUser instanceof Usuario) { // Use o nome completo se houver ambiguidade no import
                idAutor = ((Usuario) currentUser).getId();
            } else {

                try {
                    idAutor = Long.valueOf(currentUser.getUsername());
                } catch (NumberFormatException e) {
                    throw new IllegalStateException("Não foi possível obter o ID numérico " +
                            "do autor a partir do usuário autenticado.");
                }
            }

            Topico topicoCriado = topicoService.criartopicos(dadosTopico, idAutor);

            URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topicoCriado.getId()).toUri();

            return ResponseEntity.created(uri).body(new TopicoResponseDTO(topicoCriado));
        }

        @GetMapping
        public ResponseEntity<Page<TopicoResponseDTO>> listarTopicos(
                @PageableDefault(size = 10, sort = {"dataCriacao"}, direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable paginacao
        ) {
            Page<Topico> paginaDeTopicos = topicoService.listarTopicos(paginacao);
            Page<TopicoResponseDTO> paginaDeDTOs = paginaDeTopicos.map(TopicoResponseDTO::new);
            return ResponseEntity.ok(paginaDeDTOs);
        }

        @GetMapping("/{id}")
        public ResponseEntity<TopicoResponseDTO> detalharTopico(@PathVariable Long id) {
            Topico topico = topicoService.buscarTopicoPorId(id);
            return ResponseEntity.ok(new TopicoResponseDTO(topico));
        }

        @PutMapping("/{id}")
        public ResponseEntity<TopicoResponseDTO>
        atualizarTopico(@PathVariable Long id,
                        @RequestBody @Valid TopicoRequestDTO dadosAtualizacao) {
            Topico topicoAtualizado = topicoService.atualizarTopico(id, dadosAtualizacao);
            return ResponseEntity.ok(new TopicoResponseDTO(topicoAtualizado));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletarTopico(@PathVariable Long id) {
            topicoService.deletarTopico(id);
            return ResponseEntity.noContent().build();
        }
    }

