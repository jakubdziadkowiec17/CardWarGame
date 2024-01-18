package com.example.cardgame;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class Server
{
    public static void main(String[] args)
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("The server is running and waiting for connections.");
            while (true)
            {
                Socket socket1 = serverSocket.accept();
                System.out.println("Player number 1 joined the game.");
                Socket socket2 = serverSocket.accept();
                System.out.println("Player number 2 joined the game.");
                GameHandler gameHandler = new GameHandler(socket1, socket2);
                new Thread(gameHandler).start();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}