package br.edu.utfpr.gabrielcau.fitcontrol.entities;

import java.io.Serializable;

public class Exercicio implements Serializable {
    private int id;
    private String nome;
    private String tipo;
    private boolean concluido;
    private int duracao;

    public Exercicio(String nome, String tipo, boolean concluido, int duracao) {
        this.nome = nome;
        this.tipo = tipo;
        this.concluido = concluido;
        this.duracao = duracao;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }
}
