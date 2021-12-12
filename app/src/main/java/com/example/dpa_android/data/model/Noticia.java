package com.example.dpa_android.data.model;

import java.io.Serializable;

public class Noticia implements Serializable {
    public String nome;
    public String imagem;
    public String categoria;
    public String sintese;
    public String texto;

    public Noticia(String nome, String imagem, String categoria, String sintese, String texto) {
        this.nome = nome;
        this.imagem = imagem;
        this.categoria = categoria;
        this.sintese = sintese;
        this.texto = texto;
    }
}
