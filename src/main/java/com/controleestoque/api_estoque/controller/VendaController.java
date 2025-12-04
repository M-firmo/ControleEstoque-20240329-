package com.controleestoque.api_estoque.controller;

import com.controleestoque.api_estoque.model.Venda;
import com.controleestoque.api_estoque.service.VendaService;
import com.controleestoque.api_estoque.model.ItemVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Classe DTO (Data Transfer Object) para receber o corpo da requisição POST
// O Spring usa isso para mapear o JSON enviado no Postman/Insomnia
class VendaRequest {
    public Long clienteId;
    public List<ItemVenda> itens;

    // Getters e Setters para o Jackson (necessário para desserialização do JSON)
    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }
}

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    // Rota: POST /api/vendas
    // Endpoint para registrar a transação de venda.
    @PostMapping
    public ResponseEntity<Venda> realizarVenda(@RequestBody VendaRequest request) {
        
        // Chama o Service, que contém a lógica de estoque, baixa e tratamento de erro (400)
        Venda venda = vendaService.realizarVenda(request.clienteId, request.itens);
        
        // Se o Service for bem-sucedido, retorna o status 201 Created com o objeto Venda.
        return new ResponseEntity<>(venda, HttpStatus.CREATED);
    }
}