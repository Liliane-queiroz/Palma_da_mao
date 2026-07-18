package br.com.palmadocampo.model;

import java.time.LocalDateTime;

public class Categoria {

    private int id;
    private String descricao;
    private int situacaoId;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public Categoria() {
    }

    public Categoria(String descricao, int situacaoId) {
        this.descricao = descricao;
        this.situacaoId = situacaoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
