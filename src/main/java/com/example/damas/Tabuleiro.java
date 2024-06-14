package com.example.damas;

public class Tabuleiro {
    private Peca[][] posicoes;
    private Peca pecaSelecionada;

    public Tabuleiro() {
        this.posicoes = new Peca[8][8];
    }

    public boolean moverPeca(int fromRow, int fromCol, int toRow, int toCol) {
        Peca peca = posicoes[fromRow][fromCol];
        if (peca != null) {
            posicoes[fromRow][fromCol] = null;
            posicoes[toRow][toCol] = peca;
            return true;
        }
        return false;
    }

    public Peca getPeca(int row, int col) {
        return posicoes[row][col];
    }

    public void setPeca(int row, int col, Peca peca) {
        posicoes[row][col] = peca;
    }

    public boolean temPeca(int row, int col) {
        return posicoes[row][col] != null;
    }
}
