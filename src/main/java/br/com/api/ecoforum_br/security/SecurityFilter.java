package br.com.api.ecoforum_br.security;

import br.com.api.ecoforum_br.repository.UsuarioRepository;
import br.com.api.ecoforum_br.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Marca como um componente Spring
public class SecurityFilter extends OncePerRequestFilter { // Garante que o filtro seja executado uma vez por requisição

    @Autowired
    private TokenService tokenService; // Serviço para validar o token JWT

    @Autowired
    private UsuarioRepository usuarioRepository; // Repositório para buscar o usuário

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request); // Recupera o token do cabeçalho da requisição

        if (tokenJWT != null) {
           var subject = tokenService.getSubject(tokenJWT); // Obtém o "subject" (login do usuário) do token
            var usuario = usuarioRepository.findByLogin(subject); // Busca o usuário no banco de dados

            // Cria um objeto de autenticação para o Spring Security
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            // Define o usuário como autenticado no contexto do Spring Security
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response); // Continua a cadeia de filtros
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", ""); // Remove o prefixo "Bearer "
        }
        return null;
    }
}

