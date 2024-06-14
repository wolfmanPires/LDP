package com.example.damas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class EntrarController {

    @FXML
    private void handleContinue(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("jogador-encontrado.fxml"));
        stage.setScene(new Scene(root));
        stage.show();

        // Aguardar 2 segundos antes de avançar para o tabuleiro
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("tabuleiro.fxml"));
                Parent tabuleiroRoot = loader.load();
                stage.setScene(new Scene(tabuleiroRoot));
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }));
        timeline.play();
    }
}