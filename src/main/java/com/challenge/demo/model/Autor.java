package com.challenge.demo.model;

import jakarta.persistence.*;
import java.util.ArrayList; // Importação necessária
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(name = "data_nascimento")
    private String dataNascimento;

    @Column(name = "data_falecimento")
    private String dataMorte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livro = new ArrayList<>(); // <-- AQUI ESTÁ A CORREÇÃO

    // Construtor padrão (é bom ter um, mesmo que vazio)
    public Autor() {}


    public List<Livro> getLivro() {
        return livro;
    }

    public void setLivro(List<Livro> livro) {
        // Adicionei uma verificação para garantir que a lista passada não seja null antes de iterar
        if (livro != null) {
            livro.forEach(l -> l.setAutor(this));
        }
        this.livro = livro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDataMorte() {
        return dataMorte;
    }

    public void setDataMorte(String dataMorte) {
        this.dataMorte = dataMorte;
    }

    @Override
    public String toString() {
        return "\n=========- AUTORES -========="  + "\n" +
                "Nome: " + this.nome + "\n" +
                "Data de nascimento: " + this.dataNascimento  + "\n" +
                "Data do falecimento: " + this.dataMorte  + "\n" +
                "Livros: " + this.livro.stream().map(Livro::getTitulo).toList() + "\n" +
                "=============================\n";
    }
}