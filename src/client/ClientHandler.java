package client;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Handles communication with the server for player registration.
 */
public class ClientHandler extends Thread {
    private ObjectInputStream objectInputStream;
    private String isPlayerRegistered = "";
    private String isPlayerLogged = "";

    /**
     * Constructor to initialize the client handler with the client socket.
     */
    public ClientHandler(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    /**
     * The main execution method of the client handler thread.
     * Handles player registration and communication with the server.
     */
    @Override
    public void run() {
        try {

            Object response = objectInputStream.readObject();
            System.out.println("response -> " + response);

            if (response instanceof String) {
                isPlayerRegistered = (String) response;
                isPlayerLogged = (String) response;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean isPlayerRegistered() {
        return isPlayerRegistered.contains("Registration successful");
    }

    public boolean isPlayerLogged() {
        return isPlayerLogged.contains("Login successful");
    }
}
