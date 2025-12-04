package com.controleestoque.api_estoque.controller;

import com.controleestoque.api_estoque.model.*;
import com.controleestoque.api_estoque.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    // Rota: GET /api/produtos
    @GetMapping
    public List<Produto> getAllProdutos() {
        return produtoRepository.findAll();
    }

    // Rota: GET /api/produtos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(produto -> ResponseEntity.ok(produto))
                .orElse(ResponseEntity.notFound().build());
    }

    // Rota: POST /api/produtos
    @PostMapping
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
        
        // 1. Validação e Vinculação da Categoria (N:1)
        if (produto.getCategoria() == null || produto.getCategoria().getId() == null) {
            return ResponseEntity.badRequest().body(null); 
        }
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(produto.getCategoria().getId());
        if (!categoriaOptional.isPresent()) {
            return ResponseEntity.badRequest().body(null); 
        }
        produto.setCategoria(categoriaOptional.get());

        // 2. Vinculação do Estoque (1:1)
        Estoque novoEstoque = produto.getEstoque();
        if (novoEstoque != null) {
            // Garante a referência bidirecional
            novoEstoque.setProduto(produto);
            produto.setEstoque(novoEstoque);
        } else {
             return ResponseEntity.badRequest().body(null); 
        }

        // 3. Validação e Vinculação dos Fornecedores (N:M)
        if (produto.getFornecedores() != null) {
            Set<Fornecedor> fornecedoresValidos = produto.getFornecedores().stream()
                .map(f -> fornecedorRepository.findById(f.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
            
            // Verifica se todos os fornecedores passados existem.
            if (fornecedoresValidos.size() != produto.getFornecedores().size()) {
                 return ResponseEntity.badRequest().body(null);
            }
            produto.setFornecedores(fornecedoresValidos);
        }
        
        Produto produtoSalvo = produtoRepository.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo); 
    }

    // Rota: PUT /api/produtos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produtoDetalhes) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        
        if (!produtoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        Produto produtoExistente = produtoOptional.get();
        produtoExistente.setNome(produtoDetalhes.getNome());
        produtoExistente.setPreco(produtoDetalhes.getPreco());
        
        Produto produtoAtualizado = produtoRepository.save(produtoExistente);
        return ResponseEntity.ok(produtoAtualizado);
    }
    
    // Rota: DELETE /api/produtos/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduto(@PathVariable Long id) {
        produtoRepository.deleteById(id);
    }
}