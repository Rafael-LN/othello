package server;

import io.PlayerDatabase;
import model.Client;
import model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ServerHandler extends Thread {
    private Socket connection;
    private List<Client> clientsList;
    private PlayerDatabase playerDatabase;

    private List<Player> playersList;
    private Player player;

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

            for (;;) {

                player = registerPlayer(inputStream, outputStream);

                System.out.println(me.getInputStream().readObject());

            }            // Handle player registration


            // Handle lobby interactions
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

        // Receive player information from client

        Player player = (Player) objectInputStream.readObject();

        System.out.println(player);

        System.out.println(player.toString());


        if (playerDatabase.registerPlayer(player, objectOutputStream)) {

            // Notify client that registration is successful
            objectOutputStream.writeObject("Registration successful. Welcome, " + player.getNickname() + "!");

            objectOutputStream.flush();

            System.out.println("Received registration: " + player);
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