package com.example.damas;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static MainApp instance;

    @Override
    public void start(Stage stage) throws Exception {
        instance = this;
        Parent root = FXMLLoader.load(getClass().getResource("main-menu.fxml"));
        // Configurar a cena para tela cheia
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);  // Configura a tela para iniciar em tela cheia
        stage.show();
    }



    public static MainApp getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }
}