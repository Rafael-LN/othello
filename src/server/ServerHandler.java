package server;

import io.PlayerDatabase;
import model.Client;
import model.Player;
import org.w3c.dom.Document;
import utils.XMLReader;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ServerHandler extends Thread {
    public static final String PLAYER_XSD = "src/schemas/player.xsd";
    private Socket connection;
    private List<Client> clientsList;
    private PlayerDatabase playerDatabase;

    private List<Player> playersList;

    public ServerHandler(Socket connection, List<Client> clientsList) {
        this.connection = connection;
        this.clientsList = clientsList;
        this.playerDatabase = new PlayerDatabase();

    }

    public void run() {

        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;

        try {
            System.out.println("Thread " + this.getId() + ": " + connection.getRemoteSocketAddress());

            inputStream = new ObjectInputStream(connection.getInputStream());
            outputStream = new ObjectOutputStream(connection.getOutputStream());

            Client me = new Client();
            me.setName("Cli-" + this.getId());
            me.setSocket(this.connection);
            me.setInputStream(inputStream);
            me.setOutputStream(outputStream);

            clientsList.add(me);
            Player player;

            for (;;) {

                player = registerPlayer(inputStream, outputStream);

            }            // Handle player registration

            // TODO Handle lobby interactions
            // handleLobbyInteraction(player, inputStream, outputStream);

        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Exception encountered " + e.getMessage() + " from " + e.getClass());
        }finally {
            try {
                if (outputStream != null) outputStream.close();
                if (inputStream != null) inputStream.close();
                if (connection != null) connection.close();
            } catch (IOException e) {
                System.err.println("Exception encountered " + e.getMessage() + " from " + e.getClass());
    }
        }
    }

    private Player registerPlayer(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) throws IOException, ClassNotFoundException {
        // Receive XML string from client
        String xmlString = (String) objectInputStream.readObject();
        Player player = null;

        try {
            // Convert XML string to Document
            Document xmlDoc = XMLReader.convertStringToDocument(xmlString);

            // Validate XML against XSD
            if (XMLReader.validateXML(xmlDoc, PLAYER_XSD)) {
                // Extract Player object from XML
                player = XMLReader.extractPlayerFromXML(xmlDoc);

                // Register player in the database
                if (playerDatabase.registerPlayer(player, objectOutputStream)) {
                    // Notify client that registration is successful
                    objectOutputStream.writeObject("Registration successful. Welcome, " + player.getNickname() + "!");
                    objectOutputStream.flush();
                }
            } else {
                // Notify client that the XML is invalid
                objectOutputStream.writeObject("Invalid XML format.");
                objectOutputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            objectOutputStream.writeObject("Error processing registration.");
            objectOutputStream.flush();
        }

        return player;
    }

    //TODO
    private void handleLobbyInteraction(Player player, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) throws IOException {
        // Implement logic to handle lobby interactions
        // Example: player joining lobby, leaving lobby, etc.
    }

    public void broadcastLobby(Client sender) {
        String lobby = "Lobby:\n";
        for (Client client : clientsList) {
            if (!client.equals(sender)) {
                lobby += client.getPlayer() + "\n";
            }
        }
        //sender.sendMessage(lobby);
    }

    public void removeClient(Client client) {
        clientsList.remove(client);
    }
}