package com.challenge.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "idiomas")
public class Idioma { // Aqui é a declaração correta da classe

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // Adicionei 'unique = true'
    private String abreviacao; // Ex: "en", "pt", "es"

    @ManyToMany(mappedBy = "idioma")
    private List<Livro> livros; // Relacionamento ManyToMany inverso

    // Construtor padrão (obrigatório para JPA)
    public Idioma() {}

    // Construtor para facilitar a criação com abreviação
    public Idioma(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbreviacao() {
        return abreviacao;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        return abreviacao;
    }
}