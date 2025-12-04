package com.controleestoque.api_estoque.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private BigDecimal preco;

    // 1. Relacionamento 1:1 (Lado Inverso)
    @OneToOne(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Estoque estoque;

    // 2. Relacionamento N:1 (Lado Proprietário da FK - chave estrangeira)
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    // 3. Relacionamento N:M (Lado Proprietário da Tabela de Junção)
    @ManyToMany
    @JoinTable(
        name = "tb_produto_fornecedor", // Nome da tabela intermediária
        joinColumns = @JoinColumn(name = "produto_id"), // FK que aponta para Produto
        inverseJoinColumns = @JoinColumn(name = "fornecedor_id") // FK que aponta para Fornecedor
    )
    private Set<Fornecedor> fornecedores = new HashSet<>();

    // 1. Construtor Padrão (Obrigatório pelo JPA)
    public Produto() {
    }

    // 2. Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() { // <--- Adicionado!
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() { // <--- Adicionado!
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Estoque getEstoque() { // <--- Adicionado!
        return estoque;
    }

    public void setEstoque(Estoque estoque) { // <--- Adicionado!
        this.estoque = estoque;
    }

    public Categoria getCategoria() { // <--- Adicionado!
        return categoria;
    }

    public void setCategoria(Categoria categoria) { // <--- Adicionado!
        this.categoria = categoria;
    }

    public Set<Fornecedor> getFornecedores() { // <--- Adicionado!
        return fornecedores;
    }

    public void setFornecedores(Set<Fornecedor> fornecedores) { // <--- Adicionado!
        this.fornecedores = fornecedores;
    }
}