package client;

import gui.MainWindow;

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

            MainWindow gui = new MainWindow(out);

            ClientHandler clientHandler = new ClientHandler(in, out, gui);
            clientHandler.start();
            gui.setVisible(true);

            while (true) {
                // Wait for the player to be registered and logged in
                while (!clientHandler.isPlayerRegistered() || !clientHandler.isPlayerLogged()) {
                }

                // Now the player is registered and logged in
                // Proceed with the next steps, like entering the game lobby or starting a game
                System.out.println("Player registered and logged in. Proceeding to the game lobby...");

                // After exiting the lobby, the game should start
                System.out.println("Both players ready. Starting the game...");

                // TODO: Add logic for game start
            }
        } catch (IOException e) {
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
