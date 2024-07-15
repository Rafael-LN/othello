package client;

import gui.MainWindow;
import org.w3c.dom.Document;
import utils.XMLReader;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Handles communication with the server for player registration.
 */
public class ClientHandler extends Thread {
    private ObjectInputStream objectInputStream;
    private MainWindow gui;
    private String isPlayerRegistered = "";
    private String isPlayerLogged = "";

    /**
     * Constructor to initialize the client handler with the client socket.
     */
    public ClientHandler(ObjectInputStream objectInputStream, MainWindow gui) {
        this.objectInputStream = objectInputStream;
        this.gui = gui;
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
                // Parse XML response
                Document xmlDoc = XMLReader.convertStringToDocument((String) response);
                String status = XMLReader.extractValueFromXML(xmlDoc, "//status");
                String message = XMLReader.extractValueFromXML(xmlDoc, "//message");

                if (status != null && status.equals("1")) {
                    if (message.contains("Registration successful")) {
                        isPlayerRegistered = message;
                        gui.showMessageDialog("Registration", message);
                    } else if (message.contains("Login successful")) {
                        isPlayerLogged = message;
                        gui.showMessageDialog("Login", message);
                    }
                } else if (status.equals("2"))  {
                   gui.showMessageDialog("Error", message);
                }

                synchronized (this) {
                    notifyAll();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
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
