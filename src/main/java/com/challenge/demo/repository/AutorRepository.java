package com.challenge.demo.repository;

import com.challenge.demo.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

// A interface AutorRepository deve ser a única declaração de nível superior aqui.
public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Spring Data JPA vai implementar este método automaticamente com base no nome
    Optional<Autor> findByNome(String nome); // Mudei 'name' para 'nome' para consistência com o campo da entidade

    // Consulta JPQL personalizada para encontrar autores vivos em um determinado ano
    @Query("SELECT a FROM Autor a WHERE a.dataMorte > :ano AND a.dataNascimento <= :ano")
    List<Autor> findByAutorVivo(String ano);
}