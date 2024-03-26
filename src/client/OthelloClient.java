package src.client;

import src.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class OthelloClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 8080;

    public static Player registerPlayer(BufferedReader reader) throws IOException {
        // Solicitar ao jogador que forneça os dados de registro
        System.out.println("Nickname: ");
        String nickname = reader.readLine();

        System.out.println("Password: ");
        String password = reader.readLine();

        System.out.println("Nacionalidade: ");
        String nationality = reader.readLine();

        System.out.println("Idade:");
        int age = Integer.parseInt(reader.readLine());

        System.out.println("URL da sua foto: ");
        String photoUrl = reader.readLine();

        return new Player(nickname, password, nationality, age, photoUrl);
    }

    public static void main(String[] args) {
        try {
            // Connect to the server
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Create a BufferedReader to read user input
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            Player player = registerPlayer(reader);
            // Send player information to the server for registration
            out.writeObject(player);
            out.flush();

            // Receber e exibir resposta do servidor
            String response = reader.readLine();
            System.out.println("Server response: " + response);

            // Close connections
            out.close();
            in.close();
            socket.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

