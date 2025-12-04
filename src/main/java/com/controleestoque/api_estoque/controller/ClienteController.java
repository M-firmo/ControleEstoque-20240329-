package com.controleestoque.api_estoque.controller;

import com.controleestoque.api_estoque.model.Cliente;
import com.controleestoque.api_estoque.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes") // Define o caminho base para todos os endpoints desta classe
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // Rota: GET /api/clientes - Lista todos os clientes
    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    // Rota: GET /api/clientes/{id} - Busca um cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        return clienteRepository.findById(id)
                .map(ResponseEntity::ok) // Se encontrado, retorna 200 OK com o cliente
                .orElse(ResponseEntity.notFound().build()); // Se não encontrado, retorna 404 Not Found
    }

    // Rota: POST /api/clientes - Cria um novo cliente
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Retorna 201 Created
    public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Rota: PUT /api/clientes/{id} - Atualiza um cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente clienteDetalhes) {
        return clienteRepository.findById(id)
                .map(clienteExistente -> {
                    // Atualiza os campos
                    clienteExistente.setNome(clienteDetalhes.getNome());
                    clienteExistente.setEmail(clienteDetalhes.getEmail());
                    clienteExistente.setTelefone(clienteDetalhes.getTelefone());
                    
                    return ResponseEntity.ok(clienteRepository.save(clienteExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Rota: DELETE /api/clientes/{id} - Deleta um cliente
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retorna 204 No Content
    public void deleteCliente(@PathVariable Long id) {
        clienteRepository.deleteById(id);
    }
}