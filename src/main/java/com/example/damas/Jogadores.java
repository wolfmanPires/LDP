package com.example.damas;

public class Jogadores {
    private String nome;
    private boolean vez;

    public Jogadores(String nome) {
        this.nome = nome;
        this.vez = false; // Inicialmente não é a vez do jogador
    }

    public String getNome() {
        return nome;
    }

    public boolean isVez() {
        return vez;
    }

    public void setVez(boolean vez) {
        this.vez = vez;
    }
}