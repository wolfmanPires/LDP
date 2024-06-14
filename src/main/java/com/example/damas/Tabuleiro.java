package com.example.damas;

public class Tabuleiro {
    private Peca[][] posicoes;
    private Peca pecaSelecionada;


    public Tabuleiro() {
        this.posicoes = new Peca[8][8]; // Inicialmente, nenhuma posição possui peça
        //inicializarTabuleiro(); // Método opcional para inicializar o tabuleiro com peças nas posições corretas
    }

    // Exemplo de inicialização básica
    private void inicializarTabuleiro() {
        // Exemplo de inicialização com algumas peças
        posicoes[0][1] = new Peca("preta"); // Posição (0, 1) tem uma peça preta
        posicoes[1][2] = new Peca("preta"); // Posição (1, 2) tem uma peça preta
        posicoes[3][4] = new Peca("preta"); // Posição (3, 4) tem uma peça preta
        // Exemplo de inicialização com damas
        posicoes[6][5] = new Peca("branca"); // Posição (6, 5) tem uma dama branca
        posicoes[6][5].setDama(true); // Tornar a peça uma dama
    }

    public void selecionarPeca(int row, int col) {
        if (posicoes[row][col] != null) {
            pecaSelecionada = posicoes[row][col];
        } else {
            pecaSelecionada = null;
        }
    }

    public boolean moverPeca(int fromRow, int fromCol, int toRow, int toCol) {
        if (pecaSelecionada != null && posicoes[fromRow][fromCol] == pecaSelecionada) {
            // Implementar lógica de validação do movimento aqui
            // Por exemplo, verificar se o movimento é diagonal e se é válido de acordo com as regras do jogo

            // Se o movimento for válido, realizar o movimento
            posicoes[fromRow][fromCol] = null; // Remover a peça da posição original
            posicoes[toRow][toCol] = pecaSelecionada; // Colocar a peça na nova posição
            pecaSelecionada = null; // Limpar a peça selecionada após o movimento
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
