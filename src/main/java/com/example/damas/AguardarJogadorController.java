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

public class AguardarJogadorController {

    private Stage stage;

    @FXML
    private void initialize() {
        // Verifica a cada segundo se dois jogadores estão conectados
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (DamasServer.playerCount == 2) {
                goToTabuleiro();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void goToTabuleiro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("tabuleiro.fxml"));
            Parent tabuleiroRoot = loader.load();
            Scene scene = new Scene(tabuleiroRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
