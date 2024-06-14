package com.example.damas;

public class Peca {
    private boolean isDama;
    private String cor;

    public Peca(String cor) {
        this.cor = cor;
        this.isDama = false;
    }

    public boolean isDama() {
        return isDama;
    }

    public void setDama(boolean dama) {
        isDama = dama;
    }

    public String getCor() {
        return cor;
    }
}

