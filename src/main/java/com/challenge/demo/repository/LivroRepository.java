package com.challenge.demo.repository;

import com.challenge.demo.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    @Query("SELECT l FROM Livro l JOIN l.idioma i WHERE i.abreviacao ILIKE %:linguagem")
    List<Livro> findByIdioma(String linguagem);

    Optional<Livro> findByTitulo(String titulo);
}
