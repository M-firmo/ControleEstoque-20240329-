package com.controleestoque.api_estoque.model;

import com.fasterxml.jackson.annotation.JsonIgnore; // Import necessário para o @JsonIgnore
import jakarta.persistence.*;

@Entity
@Table(name = "tb_estoques")
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    // Relacionamento Um-para-Um: Lado Proprietário da FK
    @OneToOne(fetch = FetchType.LAZY)
    // Define que a chave estrangeira (FK) 'produto_id' está nesta tabela.
    @JoinColumn(name = "produto_id", nullable = false) 
    @JsonIgnore // CRÍTICO: Impede que o JSON entre em loop (Produto -> Estoque -> Produto...)
    private Produto produto;

    // Construtor Padrão (Obrigatório pelo JPA)
    public Estoque() {
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}