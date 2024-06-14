package com.example.damas;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MultiClient {
    final static int ServerPort = 1234;

    public static void main(String[] args) throws UnknownHostException, IOException {
        InetAddress ip = InetAddress.getByName("localhost");
        Socket s = new Socket(ip, ServerPort);
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        Thread sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner scn = new Scanner(System.in);
                while (true) {
                    String msg = scn.nextLine();
                    try {
                        dos.writeUTF(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String msg = dis.readUTF();
                        System.out.println(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        sendMessage.start();
        readMessage.start();
    }
}