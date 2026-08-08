package br.com.palmadocampo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProdutoDetalhe {

    // Dados do produto
    private int id;
    private String nome;
    private String descricao;
    private BigDecimal precoEstimado;
    private String fotoUrl;
    private String categoriaDescricao;

    // Dados do produtor (vêm da tabela usuario, via estoque)
    private String produtorNome;
    private String produtorCidade;
    private String produtorRegiao;
    private LocalDateTime produtorDataCadastro;

    public ProdutoDetalhe() {
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

    public String getProdutorNome() {
        return produtorNome;
    }

    public void setProdutorNome(String produtorNome) {
        this.produtorNome = produtorNome;
    }

    public String getProdutorCidade() {
        return produtorCidade;
    }

    public void setProdutorCidade(String produtorCidade) {
        this.produtorCidade = produtorCidade;
    }

    public String getProdutorRegiao() {
        return produtorRegiao;
    }

    public void setProdutorRegiao(String produtorRegiao) {
        this.produtorRegiao = produtorRegiao;
    }

    public LocalDateTime getProdutorDataCadastro() {
        return produtorDataCadastro;
    }

    public void setProdutorDataCadastro(LocalDateTime produtorDataCadastro) {
        this.produtorDataCadastro = produtorDataCadastro;
    }
}