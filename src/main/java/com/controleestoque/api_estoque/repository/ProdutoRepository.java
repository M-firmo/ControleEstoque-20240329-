package com.controleestoque.api_estoque.repository;

import com.controleestoque.api_estoque.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}