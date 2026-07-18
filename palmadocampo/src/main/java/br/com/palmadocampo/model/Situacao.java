package br.com.palmadocampo.model;

public class Situacao {

    private int id;
    private String descricao;

    public Situacao() {
    }

    public Situacao(String descricao) {
        this.descricao = descricao;
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
}