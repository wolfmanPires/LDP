package com.example.damas;

public class Jogadores {
    private int pecasPretas;
    private int pecasBrancas;

    public Jogadores() {
        this.pecasPretas = 12; // Total de peças pretas no início do jogo
        this.pecasBrancas = 12; // Total de peças brancas no início do jogo
    }

    public int getPecasPretas() {
        return pecasPretas;
    }

    public int getPecasBrancas() {
        return pecasBrancas;
    }

    public void decrementarPecasPretas() {
        this.pecasPretas--;
    }

    public void decrementarPecasBrancas() {
        this.pecasBrancas--;
    }

    public void incrementarPecasPretas() {
        this.pecasPretas++;
    }

    public void incrementarPecasBrancas() {
        this.pecasBrancas++;
    }
}
