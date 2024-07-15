package server;

import io.PlayerDatabase;
import model.Client;
import model.Player;
import org.w3c.dom.Document;
import services.PlayerService;
import utils.XMLReader;

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

            Client me = new Client();
            me.setName("Cli-" + this.getId());
            me.setSocket(this.connection);
            me.setInputStream(inputStream);
            me.setOutputStream(outputStream);

            clientsList.add(me);

            while (true) {
                // Read the XML string from the client
                String xmlString = (String) inputStream.readObject();
                Document xmlDoc = XMLReader.convertStringToDocument(xmlString);
                String requestType = XMLReader.extractValueFromXML(xmlDoc, "//request/@type");

                // Handle player registration or login based on the request type
                if ("register".equals(requestType)) {
                    playerService.registerPlayer(xmlString);
                } else if ("login".equals(requestType)) {
                    playerService.loginPlayer(xmlString);
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

    // TODO: Implement logic to handle lobby interactions
    private void handleLobbyInteraction(Player player, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) throws IOException {
        // Example: player joining lobby, leaving lobby, etc.
    }

    /**
     * Broadcasts the lobby information to all clients except the sender.
     */
    public void broadcastLobby(Client sender) {
        String lobby = "Lobby:\n";
        for (Client client : clientsList) {
            if (!client.equals(sender)) {
                lobby += client.getPlayer() + "\n";
            }
        }
        //sender.sendMessage(lobby);
    }

    /**
     * Removes a client from the client list.
     */
    public void removeClient(Client client) {
        clientsList.remove(client);
    }
}
