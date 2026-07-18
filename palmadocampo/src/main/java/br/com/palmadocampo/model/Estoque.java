package br.com.palmadocampo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Estoque {

    private int id;
    private int usuarioId;
    private int produtoId;
    private BigDecimal quantidade;
    private String unidade;
    private int situacaoId;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public Estoque() {
    }

    public Estoque(int usuarioId, int produtoId, BigDecimal quantidade,
                   String unidade, int situacaoId) {
        this.usuarioId = usuarioId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.situacaoId = situacaoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public int getSituacaoId() {
        return situacaoId;
    }

    public void setSituacaoId(int situacaoId) {
        this.situacaoId = situacaoId;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}