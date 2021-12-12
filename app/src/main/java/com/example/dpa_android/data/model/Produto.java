package com.example.dpa_android.data.model;

import java.io.Serializable;

public class Produto implements Serializable {
    public String nome;
    public String imagem;
    public float valor;
    public int quantidade;
    public String descricao;

    public Produto(String nome, String imagem, float valor, int quantidade, String descricao) {
        this.nome = nome;
        this.imagem = imagem;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
    }
}
