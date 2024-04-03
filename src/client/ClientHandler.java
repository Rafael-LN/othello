package src.client;

import src.model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private List<ClientHandler> clients;

    public ClientHandler(Socket clientSocket, List<ClientHandler> clients) {
        this.clientSocket = clientSocket;
        this.clients = clients;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            objectOutputStream.writeObject("Please provide player information: nickname, password, nationality, age, and photo URL");
            objectOutputStream.flush();


            Player player = (Player) objectInputStream.readObject();

            objectOutputStream.writeObject("Received registration: " + player);
            objectOutputStream.flush();

            System.out.println("Received registration: " + player);

            objectOutputStream.close();
            objectInputStream.close();
            clientSocket.close();
            clients.remove(this);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}