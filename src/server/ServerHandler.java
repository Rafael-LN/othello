package src.server;

import src.io.PlayerXmlHandler;
import src.model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ServerHandler implements Runnable {
    private Socket serverSocket;
    private List<ServerHandler> clients;
    private PlayerXmlHandler playerXmlHandler;

    public ServerHandler(Socket serverSocket, List<ServerHandler> clients) {
        this.serverSocket = serverSocket;
        this.clients = clients;
        this.playerXmlHandler = new PlayerXmlHandler();
    }

    @Override
    public void run() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(serverSocket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(serverSocket.getOutputStream());

            // Handle player registration
            Player player = registerPlayer(objectInputStream, objectOutputStream);

            // Handle lobby interactions
            handleLobbyInteraction(player, objectInputStream, objectOutputStream);


            objectOutputStream.close();
            objectInputStream.close();
            serverSocket.close();
            clients.remove(this);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Player registerPlayer(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) throws IOException, ClassNotFoundException {
        // Prompt player to provide information for registration
        objectOutputStream.writeObject("Please provide player information: nickname, password, nationality, age, and photo URL");
        objectOutputStream.flush();

        // Receive player information from client
        Player player = (Player) objectInputStream.readObject();

        // Simulate registration process (e.g., store player data in database)
        // Example: PlayerDatabase.register(player);
        if (playerXmlHandler.registerPlayer(player)){
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
}