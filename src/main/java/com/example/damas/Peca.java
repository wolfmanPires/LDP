package com.example.damas;

public class Peca {
    private String cor;
    private boolean isDama;

    public Peca(String cor) {
        this.cor = cor;
        this.isDama = false;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public boolean isDama() {
        return isDama;
    }

    public void setDama(boolean isDama) {
        this.isDama = isDama;
    }
}
