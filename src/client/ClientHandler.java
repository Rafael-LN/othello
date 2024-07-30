package client;

import enums.PanelType;
import gui.MainWindow;
import org.w3c.dom.Document;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Handles communication with the server for player registration.
 */
public class ClientHandler extends Thread {
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private MainWindow gui;
    private boolean isPlayerRegistered = false;
    private boolean isPlayerLogged = false;

    /**
     * Constructor to initialize the client handler with the client socket.
     */
    public ClientHandler(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, MainWindow gui) {
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        this.gui = gui;
    }

    /**
     * The main execution method of the client handler thread.
     * Handles player registration and communication with the server.
     */
    @Override
    public void run() {
        try {
            while (true) {
                Object response = objectInputStream.readObject();
                System.out.println("response -> " + response);

                if (response instanceof String) {
                    // Parse XML response
                    Document xmlDoc = XMLReader.convertStringToDocument((String) response);
                    String status = XMLReader.extractValueFromXML(xmlDoc, "//status");
                    String message = XMLReader.extractValueFromXML(xmlDoc, "//message");

                    if (status != null && status.equals("success")) {
                        if (message.contains("Registration successful")) {
                            isPlayerRegistered = true;
                            gui.showMessageDialog("Registration", message);
                        } else if (message.contains("Login successful")) {
                            isPlayerLogged = true;
                            gui.showMessageDialog("Login", message);
                        }
                        sendEnterLobbyRequest();
                        gui.changePanel(PanelType.LOBBY);
                    } else if (status.equals("error")) {
                        gui.showMessageDialog("Error", message);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void sendEnterLobbyRequest() throws Exception {
        String username = "your_username"; // Replace with the actual username
        String[][] elements = {
                {"username", username}
        };
        Document lobbyRequestXML = XMLBuilder.createXML("enterLobbyRequest", elements, new String[][]{{"type", "enterLobby"}});
        String lobbyRequestXMLString = XMLHandler.documentToString(lobbyRequestXML);

        // Send the XML string to the server
        objectOutputStream.writeObject(lobbyRequestXMLString);
        objectOutputStream.flush();
    }

    public boolean isPlayerRegistered() {
        return isPlayerRegistered;
    }

    public boolean isPlayerLogged() {
        return isPlayerLogged;
    }
}
