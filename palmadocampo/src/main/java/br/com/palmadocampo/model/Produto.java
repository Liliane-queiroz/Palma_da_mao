package br.com.palmadocampo.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Produto {

    private int id;
    private String nome;
    private String descricao;
    private BigDecimal precoEstimado;
    private String fotoUrl;
    private LocalDate dataPrevistaEntrega;
    private int categoriaId;
    private int situacaoId;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public Produto() {
    }

    public Produto(String nome, String descricao, BigDecimal precoEstimado,
                   int categoriaId, int situacaoId) {
        this.nome = nome;
        this.descricao = descricao;
        this.precoEstimado = precoEstimado;
        this.categoriaId = categoriaId;
        this.situacaoId = situacaoId;
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

    public LocalDate getDataPrevistaEntrega() {
        return dataPrevistaEntrega;
    }

    public void setDataPrevistaEntrega(LocalDate dataPrevistaEntrega) {
        this.dataPrevistaEntrega = dataPrevistaEntrega;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
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