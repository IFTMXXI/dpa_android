package com.example.dpa_android.data.model;

import java.io.Serializable;

public class Produto implements Serializable {
    public String nome;
    public int imagem;
    public float valor;
    public String descricao;

    public Produto(String nome, int imagem, float valor, String descricao) {
        this.nome = nome;
        this.imagem = imagem;
        this.valor = valor;
        this.descricao = descricao;
    }
}
