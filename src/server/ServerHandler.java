package server;

import io.PlayerDatabase;
import model.Client;
import model.Player;
import org.w3c.dom.Document;
import services.LobbyService;
import services.PlayerService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * Handles the communication with the client for player registration and login.
 */
public class ServerHandler extends Thread {
    private Socket connection;
    private List<Client> clientsList;
    private PlayerDatabase playerDatabase;
    private PlayerService playerService;
    private LobbyService lobbyService;

    public ServerHandler(Socket connection, List<Client> clientsList) {
        this.connection = connection;
        this.clientsList = clientsList;
        this.playerDatabase = new PlayerDatabase();
    }

    @Override
    public void run() {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;

        try {
            System.out.println("Thread " + this.getId() + ": " + connection.getRemoteSocketAddress());

            inputStream = new ObjectInputStream(connection.getInputStream());
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            playerService = new PlayerService(outputStream, playerDatabase);
            lobbyService = new LobbyService(outputStream, playerDatabase);

            Client me = new Client();
            me.setName("Cli-" + this.getId());
            me.setSocket(this.connection);
            me.setInputStream(inputStream);
            me.setOutputStream(outputStream);

            clientsList.add(me);
            Player player = null;

            while (true) {
                // Read the XML string from the client
                String xmlString = (String) inputStream.readObject();
                Document xmlDoc = XMLReader.convertStringToDocument(xmlString);
                String requestType = XMLReader.extractValueFromXML(xmlDoc, "//request/@type");

                // Handle requests based on the request type
                switch (requestType) {
                    case "register":
                        player = playerService.registerPlayer(xmlString);
                        break;
                    case "login":
                        player = playerService.loginPlayer(xmlString);
                        break;
                    case "editInfo":
                        playerService.editPlayerInfo(xmlString);
                        break;
                    case "enterLobby":
                        if (me.getPlayer() != null) {
                            lobbyService.enterLobby(me.getPlayer());
                        }
                        break;
                    // Add additional cases for other request types
                    default:
                        System.out.println("Unknown request type: " + requestType);
                }

                if (player != null) {
                    me.setPlayer(player);
                }
            }

        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Exception encountered " + e.getMessage() + " from " + e.getClass());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (outputStream != null) outputStream.close();
                if (inputStream != null) inputStream.close();
                if (connection != null) connection.close();
            } catch (IOException e) {
                System.err.println("Exception encountered " + e.getMessage() + " from " + e.getClass());
            }
        }
    }

    /**
     * Broadcasts the lobby information to all clients except the sender.
     */
    public void broadcastLobby() {
       /* String lobbyMessage = lobbyService.createLobbyMessage();
        for (Client client : clientsList) {
            try {
                client.getOutputStream().writeObject(lobbyMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    /**
     * Removes a client from the client list.
     */
    public void removeClient(Client client) {
        clientsList.remove(client);
    }
}
