package com.example.damas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DamasServer {
    static Vector<ClientHandler> players = new Vector<>();
    static int playerCount = 0;
    static boolean gameStarted = false;

    public static void main(String[] args) throws IOException {
        System.out.println("Servidor aceita conexões.");
        ServerSocket ss = new ServerSocket(1234);

        while (true) {
            Socket s = ss.accept();
            System.out.println("Novo client recebido : " + s);

            // Verifica se já tem dois jogadores
            if (playerCount >= 2) {
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF("Server is full. Try again later.");
                s.close();
                continue;
            }

            System.out.println("Novo client recebido : " + s);

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            ClientHandler player = new ClientHandler(s, "Player " + (playerCount + 1), dis, dos);
            players.add(player);
            Thread t = new Thread(player);
            t.start();

            playerCount++;

            // Verifica se há dois jogadores para iniciar o jogo
            if (playerCount == 2) {
                gameStarted = true;
                broadcast("Game start. Player 1 (White) moves first.");
                players.get(0).setTurn(true); // Player 1 starts
            } else if (playerCount == 1) {
                // Se ainda tem apenas um jogador, envia mensagem para aguardar pelo segundo
                player.dos.writeUTF("Waiting for the second player...");
            }
        }

    }

    // Método para enviar mensagem para todos os jogadores
    static void broadcast(String message) {
        for (ClientHandler player : players) {
            try {
                player.dos.writeUTF(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static class ClientHandler implements Runnable {
        private String name;
        final DataInputStream dis;
        final DataOutputStream dos;
        Socket s;
        boolean isloggedin;
        boolean turn;

        public ClientHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos) {
            this.s = s;
            this.dis = dis;
            this.dos = dos;
            this.name = name;
            this.isloggedin = true;
            this.turn = false;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String received = dis.readUTF();

                    // Se receber "logout", desconecta o jogador
                    if (received.equals("logout")) {
                        this.isloggedin = false;
                        this.s.close();
                        break;
                    }

                    // Se o jogo ainda não começou, aguarda
                    if (!DamasServer.gameStarted) {
                        dos.writeUTF("Waiting for the second player...");
                        continue;
                    }

                    // Se não for o turno do jogador, avisa e aguarda
                    if (!turn) {
                        dos.writeUTF("Not your turn.");
                        continue;
                    }

                    // Processa o movimento do jogador e envia para o outro jogador
                    // Exemplo: enviar movimento recebido para o outro jogador
                    DamasServer.broadcast(name + " : " + received);

                    // Muda o turno
                    for (ClientHandler player : DamasServer.players) {
                        if (player != this && player.isloggedin) {
                            player.setTurn(true); // Define o próximo jogador como ativo
                            this.setTurn(false); // Desativa o turno deste jogador
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void setTurn(boolean turn) {
            this.turn = turn;
        }
    }
}

