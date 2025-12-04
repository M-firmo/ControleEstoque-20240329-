package com.controleestoque.api_estoque.controller;

import com.controleestoque.api_estoque.model.Estoque;
import com.controleestoque.api_estoque.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estoques") // O mapeamento necessário para o seu teste!
public class EstoqueController {

    @Autowired
    private EstoqueRepository estoqueRepository;

    // Rota: GET /api/estoques
    @GetMapping
    public List<Estoque> getAllEstoques() {
        return estoqueRepository.findAll();
    }

    // Rota: GET /api/estoques/{id} - Busca o estoque pelo ID (ou pelo ID da relação Produto)
    @GetMapping("/{id}")
    public ResponseEntity<Estoque> getEstoqueById(@PathVariable Long id) {
        return estoqueRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Rota: POST /api/estoques - Cria ou atualiza uma entrada de estoque
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estoque createEstoque(@RequestBody Estoque estoque) {
        // Na vida real, você buscaria o estoque existente para atualizar,
        // mas para fins de teste, o Spring Data JPA vai gerenciar a criação
        // ou atualização se houver um ID (o que não deve acontecer aqui,
        // pois estamos assumindo uma nova entrada ou atualização simples).
        return estoqueRepository.save(estoque);
    }
}