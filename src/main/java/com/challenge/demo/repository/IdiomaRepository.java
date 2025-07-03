package com.challenge.demo.repository;

import com.challenge.demo.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IdiomaRepository extends JpaRepository<Idioma, Long> {
    Optional<Idioma> findByAbreviacao(String abreviacao);
}