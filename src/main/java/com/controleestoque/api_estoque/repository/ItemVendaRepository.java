package com.controleestoque.api_estoque.repository;

import com.controleestoque.api_estoque.model.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
    // Esta interface é usada para salvar os itens individuais de uma venda.
}