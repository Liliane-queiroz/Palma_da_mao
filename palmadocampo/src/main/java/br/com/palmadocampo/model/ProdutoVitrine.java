package br.com.palmadocampo.model;

import java.math.BigDecimal;

public class ProdutoVitrine {

    private int id;
    private String nome;
    private String descricao;
    private BigDecimal precoEstimado;
    private String fotoUrl;
    private String categoriaDescricao;

    public ProdutoVitrine() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPrecoEstimado() {
        return precoEstimado;
    }

    public void setPrecoEstimado(BigDecimal precoEstimado) {
        this.precoEstimado = precoEstimado;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getCategoriaDescricao() {
        return categoriaDescricao;
    }

    public void setCategoriaDescricao(String categoriaDescricao) {
        this.categoriaDescricao = categoriaDescricao;
    }
}