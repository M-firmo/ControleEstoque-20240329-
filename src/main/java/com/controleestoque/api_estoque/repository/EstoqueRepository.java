package com.controleestoque.api_estoque.repository;

import com.controleestoque.api_estoque.model.Estoque;
import com.controleestoque.api_estoque.model.Produto; // Novo Import
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional; // Novo Import

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    // Adicione esta linha. O Spring entende que deve buscar o Estoque 
    // onde o campo 'produto' seja igual ao Produto passado como parâmetro.
    Optional<Estoque> findByProduto(Produto produto); 
}