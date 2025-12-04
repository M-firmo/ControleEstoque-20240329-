package com.controleestoque.api_estoque.repository;

import com.controleestoque.api_estoque.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
}