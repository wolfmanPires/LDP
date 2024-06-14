package com.example.damas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

public class MultiServer {
    static Vector<ClientHandler> ar = new Vector<>();
    static int i = 0;

    public static void main(String[] args) throws IOException {
        System.out.println("Servidor aceita conexões.");
        ServerSocket ss = new ServerSocket(1234);

        Socket s;

        while (true) {
            s = ss.accept();
            System.out.println("Novo client recebido : " + s);

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            ClientHandler mtch = new ClientHandler(s, "client " + i, dis, dos);
            Thread t = new Thread(mtch);

            System.out.println("Adiciona cliente " + i + " à lista ativa.");

            ar.add(mtch);
            t.start();

            i++;
        }
    }

    static class ClientHandler implements Runnable {
        private String name;
        final DataInputStream dis;
        final DataOutputStream dos;
        Socket s;
        boolean isloggedin;

        public ClientHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos) {
            this.s = s;
            this.dis = dis;
            this.dos = dos;
            this.name = name;
            this.isloggedin = true;
        }

        @Override
        public void run() {
            String received;

            while (true) {
                try {
                    received = dis.readUTF();
                    System.out.println(received);

                    if (received.equals("logout")) {
                        this.isloggedin = false;
                        this.s.close();
                        break;
                    }

                    StringTokenizer st = new StringTokenizer(received, "#");
                    String MsgToSend = st.nextToken();
                    String recipient = st.nextToken();

                    for (ClientHandler mc : MultiServer.ar) {
                        if (mc.name.equals(recipient) && mc.isloggedin) {
                            mc.dos.writeUTF(this.name + " : " + MsgToSend);
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}