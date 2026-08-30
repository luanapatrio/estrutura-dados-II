package com.company;

public class ItemPedido {
    private Long id;
    private String item;
    private String descricao; // <--- NOVO CAMPO
    private Integer quantidade;
    private Double valorUnitario;

    public ItemPedido() {
    }

    public ItemPedido(Long id, String item, String descricao, Integer quantidade, Double valorUnitario) {
        this.id = id;
        this.item = item;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescricao() {
        return descricao;
    } // <--- NOVO GETTER

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    } // <--- NOVO SETTER

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
}