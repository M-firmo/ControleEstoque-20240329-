package com.controleestoque.api_estoque.controller;

import com.controleestoque.api_estoque.model.Fornecedor;
import com.controleestoque.api_estoque.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    // Rota: GET /api/fornecedores
    // Busca e retorna todos os fornecedores
    @GetMapping
    public List<Fornecedor> getAllFornecedores() {
        return fornecedorRepository.findAll();
    }

    // Rota: GET /api/fornecedores/{id}
    // Busca um fornecedor pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> getFornecedorById(@PathVariable Long id) {
        return fornecedorRepository.findById(id)
                .map(fornecedor -> ResponseEntity.ok(fornecedor))
                .orElse(ResponseEntity.notFound().build());
    }

    // Rota: POST /api/fornecedores
    // Cria um novo fornecedor
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fornecedor createFornecedor(@RequestBody Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    // Rota: PUT /api/fornecedores/{id}
    // Atualiza um fornecedor existente
    @PutMapping("/{id}")
    public ResponseEntity<Fornecedor> updateFornecedor(@PathVariable Long id, @RequestBody Fornecedor fornecedorDetalhes) {
        return fornecedorRepository.findById(id)
                .map(fornecedorExistente -> {
                    fornecedorExistente.setNome(fornecedorDetalhes.getNome());
                    Fornecedor fornecedorAtualizado = fornecedorRepository.save(fornecedorExistente);
                    return ResponseEntity.ok(fornecedorAtualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Rota: DELETE /api/fornecedores/{id}
    // Deleta um fornecedor
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFornecedor(@PathVariable Long id) {
        fornecedorRepository.deleteById(id);
    }
}