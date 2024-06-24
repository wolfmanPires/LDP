package com.example.damas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DamasClient extends Application {
    private static String serverAddress;
    private static int serverPort;
    private static String username;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    @Override
    public void start(Stage primaryStage) throws Exception {
        socket = new Socket(serverAddress, serverPort);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Tabuleiro.fxml"));
        Parent root = loader.load();
        TabuleiroController controller = loader.getController();
       /* controller.initData(socket, in, out, username);*/

        primaryStage.setTitle("Damas");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        new Thread(() -> {
            try {
                while (true) {
                    String msg = in.readUTF();
                    System.out.println(msg);
                  /*  controller.processServerMessage(msg);*/
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        in.close();
        out.close();
        socket.close();
    }

    public static void setServerAddress(String serverAddress) {
        DamasClient.serverAddress = serverAddress;
    }

    public static void setServerPort(int serverPort) {
        DamasClient.serverPort = serverPort;
    }

    public static void setUsername(String username) {
        DamasClient.username = username;
    }

    public static void launchClient() {
        launch();
    }

    public static void main(String[] args) {
        // Aqui você pode lidar com argumentos da linha de comando, se necessário
        launch(args);
    }
}
