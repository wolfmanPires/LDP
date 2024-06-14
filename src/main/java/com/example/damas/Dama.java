package com.example.damas;

public class Dama extends Peca {

    public Dama(String cor) {
        super(cor);
        setDama(true); // Indica que é uma dama
    }

    // Métodos específicos para dama, se necessário

    public void movimentoEspecial() {
        // Lógica para movimento especial da dama
    }

    public void capturarPecaEspecial() {
        // Lógica para captura especial de peça pela dama
    }
}