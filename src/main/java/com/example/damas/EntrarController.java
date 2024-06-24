    package com.example.damas;

    import javafx.animation.KeyFrame;
    import javafx.animation.Timeline;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.Alert;
    import javafx.scene.control.TextField;
    import javafx.stage.Stage;
    import javafx.util.Duration;
    import java.io.DataInputStream;
    import java.io.DataOutputStream;
    import java.io.IOException;
    import java.net.Socket;

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

/*
        @FXML
        private TextField usernameField;

        @FXML
        private TextField ipField;

        @FXML
        private TextField portField;

        @FXML
        private void handleContinue(ActionEvent event) throws IOException {
            String username = usernameField.getText();
            String ip = ipField.getText();
            int port = Integer.parseInt(portField.getText());

            // Simulando lógica de verificação e conexão
            if (username.isEmpty() || ip.isEmpty() || port == 0) {
                System.out.println("Preencha todos os campos.");
                return;
            }

            // Verifica se já há dois jogadores conectados (simulado)
            boolean twoPlayersConnected = DamasServer.playerCount == 2;

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            if (!twoPlayersConnected) {
                // Se ainda não há dois jogadores, mostra a tela de espera
                Parent root = FXMLLoader.load(getClass().getResource("aguardar-jogador.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                // Se já houver dois jogadores, mostra a tela de jogador encontrado
                Parent root = FXMLLoader.load(getClass().getResource("jogador-encontrado.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

                // Aguarda 2 segundos antes de avançar para o tabuleiro
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
        }*/
