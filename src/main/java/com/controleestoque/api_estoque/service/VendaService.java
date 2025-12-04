package com.controleestoque.api_estoque.service;

import com.controleestoque.api_estoque.model.*;
import com.controleestoque.api_estoque.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class VendaService {

    // Injeção de dependências dos Repositórios necessários
    @Autowired private VendaRepository vendaRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private ProdutoRepository produtoRepository;
    @Autowired private EstoqueRepository estoqueRepository;
    @Autowired private ItemVendaRepository itemVendaRepository;

    @Transactional // Garante o Rollback se a lógica de estoque falhar (Requisito de Tratamento de Erro)
    public Venda realizarVenda(Long clienteId, List<ItemVenda> itensVendaDTO) {
        
        // 1. Busca do Cliente
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));

        Venda novaVenda = new Venda();
        novaVenda.setCliente(cliente);
        novaVenda = vendaRepository.save(novaVenda); // Salva a venda inicial para obter o ID

        // 2. Processar Itens, Verificar Estoque e Dar Baixa
        for (ItemVenda itemDTO : itensVendaDTO) {
            Produto produto = produtoRepository.findById(itemDTO.getProduto().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto ID " + itemDTO.getProduto().getId() + " não encontrado."));
            
            // Requisito: Relacionamento 1:1 com Estoque
            Estoque estoque = estoqueRepository.findByProduto(produto)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque para o produto " + produto.getNome() + " não encontrado."));

            Integer quantidadeVendida = itemDTO.getQuantidade();
            Integer estoqueAtual = estoque.getQuantidade();

            // Lógica Crítica: Verificar a Disponibilidade
            if (estoqueAtual < quantidadeVendida) {
                // Tratamento de Erro: HTTP 400 Bad Request (Requisito)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                    "Estoque insuficiente para o produto " + produto.getNome() + 
                    ". Estoque atual: " + estoqueAtual + ", solicitado: " + quantidadeVendida);
            }

            // Lógica Crítica: Dar Baixa no Estoque
            estoque.setQuantidade(estoqueAtual - quantidadeVendida);
            estoqueRepository.save(estoque);

            // 3. Salvar Item da Venda (Tabela Intermediária)
            ItemVenda itemVenda = new ItemVenda();
            itemVenda.setVenda(novaVenda);
            itemVenda.setProduto(produto);
            itemVenda.setQuantidade(quantidadeVendida);
            // Captura o preço no momento da venda (Requisito)
            itemVenda.setPrecoUnitario(produto.getPreco() != null ? produto.getPreco() : BigDecimal.ZERO); 
            
            itemVendaRepository.save(itemVenda);
            novaVenda.getItens().add(itemVenda);
        }

        return novaVenda;
    }
}