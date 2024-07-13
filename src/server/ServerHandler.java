package server;

import io.PlayerDatabase;
import model.Client;
import model.Player;
import services.PlayerService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ServerHandler extends Thread {
    private Socket connection;
    private List<Client> clientsList;
    private PlayerDatabase playerDatabase;
    private PlayerService playerService;

    public ServerHandler(Socket connection, List<Client> clientsList) {
        this.connection = connection;
        this.clientsList = clientsList;
        playerDatabase = new PlayerDatabase();
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
            Player player;

            while (true) {
                // Handle player registration
                player = registerPlayer(inputStream, playerService);
            }

            // TODO Handle lobby interactions
            // handleLobbyInteraction(player, inputStream, outputStream);

        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Exception encountered " + e.getMessage() + " from " + e.getClass());
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

    private Player registerPlayer(ObjectInputStream objectInputStream, PlayerService playerService) throws IOException, ClassNotFoundException {
        // Receive XML string from client
        String xmlString = (String) objectInputStream.readObject();
        return playerService.registerPlayer(xmlString);
    }

    // TODO
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
