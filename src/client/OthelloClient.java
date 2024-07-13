package client;

import gui.PlayerAuthentication;

import java.io.*;
import java.net.Socket;

/**
 * The main client class responsible for connecting to the server.
 */
public class OthelloClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 8080;

    /**
     * The main method that connects to the server.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {

        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            // Connect to the server
            socket = new Socket(SERVER_IP, SERVER_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            ClientHandler clientHandler = new ClientHandler(in);
            clientHandler.start();

            PlayerAuthentication playerAuthentication = new PlayerAuthentication(out);
            playerAuthentication.setVisible(true);

            while (true) {
                // Wait for the player to be registered and logged in
                synchronized (clientHandler) {
                    while (!clientHandler.isPlayerRegistered() || !clientHandler.isPlayerLogged()) {
                        clientHandler.wait();
                    }
                }

                // Now the player is registered and logged in
                // Proceed with the next steps, like entering the game lobby or starting a game
                // Example:
                System.out.println("Player registered and logged in. Proceeding to the game lobby...");
                // TODO: Add logic for game lobby or game start
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // Close connections
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
