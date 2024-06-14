package com.example.damas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private void handleEnterGame(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("entrar.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleViewRules(ActionEvent event) {
        MainApp.getInstance().getHostServices().showDocument("http://example.com/rules");
    }

    @FXML
    private void handleHowToPlay(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("como-jogar.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}