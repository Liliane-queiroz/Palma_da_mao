package br.com.palmadocampo.model;

import java.time.LocalDateTime;

public class Usuario {

    private int id;
    private String cpfCnpj;
    private String nome;
    private String telefone;
    private String email;
    private String senhaHash;
    private String endereco;
    private String cidade;
    private String regiao;
    private String nomePropriedade;
    private String tipo;
    private int situacaoId;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public Usuario() {
    }

    public Usuario(String nome, String cpfCnpj, String email, String senhaHash,
                   String telefone, String tipo, int situacaoId) {
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.senhaHash = senhaHash;
        this.telefone = telefone;
        this.tipo = tipo;
        this.situacaoId = situacaoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public String getNomePropriedade() {
        return nomePropriedade;
    }

    public void setNomePropriedade(String nomePropriedade) {
        this.nomePropriedade = nomePropriedade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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