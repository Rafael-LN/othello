package src.client;

import src.model.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * The main client class responsible for connecting to the server.
 */
public class OthelloClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 8080;

    /**
     * The main method that connects to the server.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {

        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            // Connect to the server
            socket = new Socket(SERVER_IP, SERVER_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            ClientHandler clientHandler = new ClientHandler(in);
            clientHandler.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Player player = null;

            while (!clientHandler.isPlayerRegistered()) {
                // Read player information from user input
                player = registerPlayer(reader);

                // Send player information to the server for registration
                out.writeObject(player);
                out.flush();
            }




        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close connections
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Reads player information from the console input for registration.
     *
     * @return The player object containing the registration information.
     * @throws IOException If an I/O error occurs while reading user input.
     */
    private static Player registerPlayer(BufferedReader reader) throws IOException {
        // Solicit player registration information from the console
        System.out.println("Please provide player information: nickname, password, nationality, age, and photo URL\n");
        System.out.println("Nickname: ");
        String nickname = reader.readLine();

        System.out.println("Password: ");
        String password = reader.readLine();

        System.out.println("Nationality: ");
        String nationality = reader.readLine();

        System.out.println("Age:");
        int age = Integer.parseInt(reader.readLine());

        System.out.println("URL of your photo: ");
        String photoUrl = reader.readLine();

        return new Player(nickname, password, nationality, age, photoUrl);
    }
}
