package com.example.damas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class TabuleiroController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private GridPane boardGrid;

    @FXML
    private Label opponentTime;

    @FXML
    private Label playerTime;

    private Circle selectedPiece;
    private int selectedRow = -1;
    private int selectedCol = -1;

    private final int boardSize = 8;
    private final int cellSize = 50;

    private Tabuleiro tabuleiro = new Tabuleiro();
    private String turnoAtual = "branca"; // O turno começa com as peças brancas

    private Timeline timer;
    private int tempoRestante = 30;

    @FXML
    private void initialize() {
        desenharTabuleiro();
        adicionarPecas();
        iniciarTimer();
    }

    private void iniciarTimer() {
        if (timer != null) {
            timer.stop();
        }
        tempoRestante = 30;
        atualizarLabelTempo();
        timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            tempoRestante--;
            atualizarLabelTempo();
            if (tempoRestante <= 0) {
                alternarTurno();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void atualizarLabelTempo() {
        if (turnoAtual.equals("branca")) {
            playerTime.setText("Tempo Restante: " + tempoRestante + "s");
            opponentTime.setText("Tempo Restante: 30s");
        } else {
            opponentTime.setText("Tempo Restante: " + tempoRestante + "s");
            playerTime.setText("Tempo Restante: 30s");
        }
    }

    private void alternarTurno() {
        turnoAtual = turnoAtual.equals("branca") ? "preta" : "branca";
        iniciarTimer();
    }

    private void desenharTabuleiro() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                Color color = (row + col) % 2 == 0 ? Color.valueOf("#D2B48C") : Color.valueOf("#FFE4B5");
                Rectangle square = new Rectangle(cellSize, cellSize, color);
                boardGrid.add(square, col, row);
                square.setOnMouseClicked(event -> moverPeca(GridPane.getRowIndex(square), GridPane.getColumnIndex(square)));
            }
        }
        centralizarTabuleiro();
    }

    private void centralizarTabuleiro() {
        BorderPane.setAlignment(boardGrid, javafx.geometry.Pos.CENTER);
        for (int i = 0; i < boardSize; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / boardSize);
            boardGrid.getColumnConstraints().add(colConst);

            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / boardSize);
            boardGrid.getRowConstraints().add(rowConst);
        }
    }

    private void adicionarPecas() {
        adicionarPecasCor("preta", 0, 3);
        adicionarPecasCor("branca", 5, 8);
        adicionarEventosCliquePeca();
    }

    private void adicionarPecasCor(String cor, int inicio, int fim) {
        for (int row = inicio; row < fim; row++) {
            for (int col = (row + 1) % 2; col < boardSize; col += 2) {
                Circle peca = criarPeca(cor);
                boardGrid.add(peca, col, row);
                tabuleiro.setPeca(row, col, new Peca(cor));
            }
        }
    }

    private Circle criarPeca(String cor) {
        Circle peca = new Circle(cellSize * 0.4);
        peca.setFill(cor.equals("preta") ? Color.BLACK : Color.WHITE);
        return peca;
    }

    private void adicionarEventosCliquePeca() {
        for (Node node : boardGrid.getChildren()) {
            if (node instanceof Circle) {
                node.setOnMouseClicked(event -> selecionarPeca((Circle) node));
            }
        }
    }

    private void selecionarPeca(Circle peca) {
        if (selectedPiece != null) {
            selectedPiece.setStroke(Color.TRANSPARENT);
        }

        selectedPiece = peca;
        selectedRow = GridPane.getRowIndex(peca);
        selectedCol = GridPane.getColumnIndex(peca);
        peca.setStroke(Color.RED);
        peca.setStrokeWidth(3);
    }

    private void moverPeca(int toRow, int toCol) {
        if (selectedPiece != null && validarMovimento(selectedRow, selectedCol, toRow, toCol)) {
            // Remover peça capturada, se houver
            int middleRow = (selectedRow + toRow) / 2;
            int middleCol = (selectedCol + toCol) / 2;

            if (Math.abs(selectedRow - toRow) == 2 && Math.abs(selectedCol - toCol) == 2) {
                Node pieceToRemove = getPieceAt(middleRow, middleCol);
                if (pieceToRemove != null) {
                    boardGrid.getChildren().remove(pieceToRemove);
                    tabuleiro.setPeca(middleRow, middleCol, null); // Remove peça capturada do tabuleiro lógico
                }
            }

            GridPane.setColumnIndex(selectedPiece, toCol);
            GridPane.setRowIndex(selectedPiece, toRow);
            tabuleiro.moverPeca(selectedRow, selectedCol, toRow, toCol);

            // Verificar se a peça deve evoluir para dama
            Peca pecaMovida = tabuleiro.getPeca(toRow, toCol);
            if ((pecaMovida.getCor().equals("branca") && toRow == 0) || (pecaMovida.getCor().equals("preta") && toRow == 7)) {
                tabuleiro.setPeca(toRow, toCol, new Dama(pecaMovida.getCor()));
            }

            selectedPiece.setStroke(Color.TRANSPARENT);
            selectedPiece = null;

            // Alternar o turno após o movimento
            alternarTurno();
        }
    }

    private boolean validarMovimento(int fromRow, int fromCol, int toRow, int toCol) {
        // Verificar se é o turno da cor correta
        Peca peca = tabuleiro.getPeca(fromRow, fromCol);
        if (!peca.getCor().equals(turnoAtual)) {
            return false;
        }

        // Verificar se a casa destino está vazia
        if (tabuleiro.temPeca(toRow, toCol)) {
            return false;
        }

        // Verificar se o movimento é diagonal
        if (Math.abs(fromRow - toRow) != Math.abs(fromCol - toCol)) {
            return false;
        }

        // Verificar se a peça está se movendo apenas uma casa (ou duas no caso de captura)
        int rowDiff = Math.abs(fromRow - toRow);
        int colDiff = Math.abs(fromCol - toCol);

        // Verificar se a peça está se movendo na direção correta (para frente)
        if (peca instanceof Dama) {
            // Dama pode se mover para qualquer direção
            if (rowDiff == 1 && colDiff == 1) {
                return true; // Movimento normal de uma casa
            } else if (rowDiff == 2 && colDiff == 2) {
                // Verificar se há uma peça adversária para capturar
                int middleRow = (fromRow + toRow) / 2;
                int middleCol = (fromCol + toCol) / 2;
                Peca middlePeca = tabuleiro.getPeca(middleRow, middleCol);

                return middlePeca != null && !middlePeca.getCor().equals(peca.getCor());
            }
        } else {
            // Peças normais só podem se mover para frente
            if (peca.getCor().equals("branca") && toRow >= fromRow) {
                return false;
            }
            if (peca.getCor().equals("preta") && toRow <= fromRow) {
                return false;
            }

            if (rowDiff == 1 && colDiff == 1) {
                return true; // Movimento normal de uma casa
            } else if (rowDiff == 2 && colDiff == 2) {
                // Verificar se há uma peça adversária para capturar
                int middleRow = (fromRow + toRow) / 2;
                int middleCol = (fromCol + toCol) / 2;
                Peca middlePeca = tabuleiro.getPeca(middleRow, middleCol);


                return middlePeca != null && !middlePeca.getCor().equals(peca.getCor());
            }
        }

        return false;
    }

    private Node getPieceAt(int row, int col) {
        for (Node node : boardGrid.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof Circle) {
                return node;
            }
        }
        return null;
    }
}

