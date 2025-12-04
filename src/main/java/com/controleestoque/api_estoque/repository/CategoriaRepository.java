package com.controleestoque.api_estoque.repository;

import com.controleestoque.api_estoque.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}