package src.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class OthelloServer {
    private static final int PORT = 8080;
    private static List<ServerHandler> clients;

    public static void main(String[] args) {

        clients = new ArrayList<>();

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Listening on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);
                Thread serverHandler = new ServerHandler(clientSocket, clients);
                serverHandler.start();
            }
        } catch (IOException e) {
            System.err.println("Exception in server: " + e.getMessage());
        }
    }
}
