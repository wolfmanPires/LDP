package com.example.damas;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class TabuleiroController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private GridPane boardGrid;

    // Variáveis para armazenar a peça selecionada e sua posição original
    private Circle selectedPiece;
    private int selectedRow = -1;
    private int selectedCol = -1;

    // Instância do Tabuleiro
    private Tabuleiro tabuleiro = new Tabuleiro();

    // Método para inicializar o tabuleiro com peças
    @FXML
    private void initialize() {
        desenharTabuleiro();
        adicionarPecas();
    }

    private void desenharTabuleiro() {
        int size = 8; // tamanho do tabuleiro (8x8)

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Color color = (row + col) % 2 == 0 ? Color.valueOf("#D2B48C") : Color.valueOf("#FFE4B5");
                Rectangle square = new Rectangle(50, 50, color);
                boardGrid.add(square, col, row);
            }
        }

        centralizarTabuleiro();
    }

    private void centralizarTabuleiro() {
        // Centralizar o GridPane no BorderPane
        BorderPane.setAlignment(boardGrid, javafx.geometry.Pos.CENTER);

        // Adicionar constraints para ajustar o tamanho das células ao redimensionar
        for (int i = 0; i < 8; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / 8);
            boardGrid.getColumnConstraints().add(colConst);

            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / 8);
            boardGrid.getRowConstraints().add(rowConst);
        }
    }

    private void adicionarPecas() {
        int size = 8; // tamanho do tabuleiro (8x8)
        adicionarEventosCliquePeca();

        // Adicionar peças pretas
        for (int row = 0; row < 3; row++) {
            for (int col = (row + 1) % 2; col < size; col += 2) {
                Circle peca = new Circle(20, Color.BLACK);
                boardGrid.add(peca, col, row);
                tabuleiro.setPeca(row, col, new Peca("preta")); // Peça preta
            }
        }

        // Adicionar peças brancas
        for (int row = 5; row < size; row++) {
            for (int col = (row + 1) % 2; col < size; col += 2) {
                Circle peca = new Circle(20, Color.WHITE);
                boardGrid.add(peca, col, row);
                tabuleiro.setPeca(row, col, new Peca("branca")); // Peça branca
            }
        }
    }

    private void adicionarEventosCliquePeca() {
        for (Node node : boardGrid.getChildren()) {
            if (node instanceof Circle) {
                node.setOnMouseClicked(event -> selecionarPeca((Circle) node));
            }
        }
    }

    private void selecionarPeca(Circle peca) {
        int row = GridPane.getRowIndex(peca);
        int col = GridPane.getColumnIndex(peca);

        if (selectedPiece == null) {
            selectedPiece = peca;
            selectedRow = row;
            selectedCol = col;
            peca.setStroke(Color.RED); // Adiciona contorno vermelho
            peca.setStrokeWidth(3); // Define a largura do contorno
        } else {
            moverPeca(selectedRow, selectedCol, row, col);
            selectedPiece.setStroke(Color.TRANSPARENT); // Remove contorno ao mover
            selectedPiece = null;
        }
    }

    private void moverPeca(int fromRow, int fromCol, int toRow, int toCol) {
        if (isMovimentoValido(fromRow, fromCol, toRow, toCol)) {
            GridPane.setColumnIndex(selectedPiece, toCol);
            GridPane.setRowIndex(selectedPiece, toRow);
            tabuleiro.moverPeca(fromRow, fromCol, toRow, toCol);
        }
    }

    private boolean isMovimentoValido(int fromRow, int fromCol, int toRow, int toCol) {
        // Implemente aqui a lógica para validar o movimento da peça
        return true; // Por enquanto, permitimos qualquer movimento
    }
}
