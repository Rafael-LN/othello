package client;

import gui.PlayerRegistration;
import model.Player;
import org.w3c.dom.Document;
import utils.XMLBuilder;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
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

            PlayerRegistration registrationWindow = new PlayerRegistration(out); // Create the registration window once

            while (!clientHandler.isPlayerRegistered() || !clientHandler.isPlayerLogged()) {
                // Read player information from user input

                player = clientHandler.isPlayerRegistered() ? logPlayer(reader) : registerPlayer(reader);

                Document playerXML = XMLBuilder.createPlayerRegistrationXML(player);

                // Transforma o documento XML em uma string
                String playerXMLString = transformDocumentToString(playerXML);

                // Envie o XML para o servidor
                out.writeObject(playerXMLString);
                // Send player information to the server for registration
                out.writeObject(player);
                out.flush();

                if (!clientHandler.isPlayerRegistered()) {
                    new PlayerRegistration(out);
                }
                /*if (!clientHandler.isPlayerRegistered()) {
                    registrationWindow.setVisible(true); // Show the registration window when needed
                }*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
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

    private static String transformDocumentToString(Document doc) throws Exception {
        // Transforma o documento XML em uma string
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.getBuffer().toString();
    }

    private static Player logPlayer(BufferedReader reader) throws IOException {
        System.out.println("Please login\n");
        System.out.println("Nickname: ");
        String nickname = reader.readLine();

        System.out.println("Password: ");
        String password = reader.readLine();

        return new Player(nickname, password);
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
