package com.controleestoque.api_estoque.repository;

import com.controleestoque.api_estoque.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // O Spring Data JPA fornece todos os métodos CRUD básicos (salvar, buscar, deletar) automaticamente.
}