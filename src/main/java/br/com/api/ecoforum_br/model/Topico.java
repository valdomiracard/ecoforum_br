package br.com.api.ecoforum_br.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Table(name = "topicos") // Mapeia para a tabela 'topicos'
@Entity(name = "Topico") // Define como entidade JPA
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;

    @Column(name = "data_criacao") // Nome da coluna no DB
    private LocalDateTime dataCriacao;

    private String status; // Ex: "ABERTO", "FECHADO", "RESOLVIDO"

    // Muitos tópicos para um autor
    @ManyToOne(fetch = FetchType.LAZY) // Carregamento lazy para o autor
    @JoinColumn(name = "autor_id") // Coluna FK na tabela 'topicos'
    private Usuario autor;

    // Muitos tópicos para um curso
    @ManyToOne(fetch = FetchType.LAZY) // Carregamento lazy para o curso
    @JoinColumn(name = "curso_id") // Coluna FK na tabela 'topicos'
    private Curso curso;

    // Construtor para criação de novo tópico sem ID (para o serviço)
    public Topico(String titulo, String mensagem, Usuario autor, Curso curso) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataCriacao = LocalDateTime.now();
        this.status = "ABERTO";
        this.autor = autor;
        this.curso = curso;
    }

    // Métodos para atualização de campos (bom para encapsular lógica de negócio)
    public void atualizarInformacoes(String titulo, String mensagem) {
        if (titulo != null && !titulo.isBlank()) {
            this.titulo = titulo;
        }
        if (mensagem != null && !mensagem.isBlank()) {
            this.mensagem = mensagem;
        }
    }

    public void fecharTopico() {
        this.status = "FECHADO";
    }

    public void resolverTopico() {
        this.status = "RESOLVIDO";
    }
}


// outros getters e setters
