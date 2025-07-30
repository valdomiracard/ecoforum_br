package br.com.api.ecoforum_br.controller;

import br.com.api.ecoforum_br.dto.LoginRequestDTO;
import br.com.api.ecoforum_br.dto.TokenResponseDTO;
import br.com.api.ecoforum_br.model.Usuario;
import br.com.api.ecoforum_br.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/login")
    public class LoginController {

        @Autowired
        private AuthenticationManager manager; // Gerencia o processo de autenticação

        @Autowired
        private TokenService tokenService; // Serviço para gerar o JWT

        @PostMapping
        public ResponseEntity<TokenResponseDTO> efetuarLogin(@RequestBody @Valid LoginRequestDTO dados) {

            var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());

            var authentication = manager.authenticate(authenticationToken);

            var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

            return ResponseEntity.ok(new TokenResponseDTO(tokenJWT));
        }
    }

