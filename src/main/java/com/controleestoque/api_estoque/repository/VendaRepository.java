package com.controleestoque.api_estoque.repository;

import com.controleestoque.api_estoque.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
    // Os métodos para operações com Vendas são herdados aqui.
}