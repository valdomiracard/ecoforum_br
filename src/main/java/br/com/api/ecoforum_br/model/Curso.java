package br.com.api.ecoforum_br.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "cursos") // Mapeia para a tabela 'cursos'
@Entity(name = "Curso") // Define como entidade JPA
@Getter @Setter // Gera getters e setters
@NoArgsConstructor // Construtor sem argumentos
@AllArgsConstructor // Construtor com todos os argumentos
@EqualsAndHashCode(of = "id") // Gera equals e hashCode baseado no ID
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String categoria;

    // Construtor para criação sem ID
    public Curso(String nome, String categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }
}