package services;

import io.PlayerDatabase;
import model.Player;
import org.w3c.dom.Document;
import server.ServerHandler;
import utils.XMLBuilder;
import utils.XMLHandler;
import utils.XMLReader;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Base64;

/**
 * Service class for handling player operations such as registration and login.
 * <p>
 * This class interacts with the XML utilities to create and send XML documents.
 */
public class PlayerService {
    private static final String PLAYER_XSD = "src/schemas/player.xsd";
    private ObjectOutputStream out;
    private PlayerDatabase playerDatabase;

    public PlayerService(ObjectOutputStream out) {
        this.out = out;
    }

    public PlayerService(ObjectOutputStream out, PlayerDatabase playerDatabase) {
        this.out = out;
        this.playerDatabase = playerDatabase;
    }

    /**
     * Registers a player by creating an XML document and sending it to the server.
     *
     * @param player the Player object containing registration details
     * @throws Exception if an error occurs during the registration process
     */
    public void registerPlayer(Player player) throws Exception {
        String[][] elements = {
                {"nickname", player.getNickname()},
                {"password", player.getPassword()},
                {"nationality", player.getNationality()},
                {"age", String.valueOf(player.getAge())},
                {"photo",player.getBase64Photo()}
        };
        Document playerXML = XMLBuilder.createXML("request", elements, new String[][]{{"type", "register"}});
        String playerXMLString = XMLHandler.documentToString(playerXML);

        // Send the XML string to the server
        out.writeObject(playerXMLString);

        out.flush();

        System.out.println("Player registration successful:\n" + player);
    }

    /**
     * Logs in a player by creating an XML document and sending it to the server.
     *
     * @param player the Player object containing login details
     * @throws Exception if an error occurs during the login process
     */
    public void loginPlayer(Player player) throws Exception {
        String[][] elements = {
                {"nickname", player.getNickname()},
                {"password", player.getPassword()}
        };
        Document playerXML = XMLBuilder.createXML("request", elements, new String[][]{{"type", "login"}});
        String playerXMLString = XMLHandler.documentToString(playerXML);

        // Send the XML string to the server
        out.writeObject(playerXMLString);

        // Send the player object to the server
        out.writeObject(player);
        out.flush();

        System.out.println("Player login successful:\n" + player);
    }

    /**
     * Extracts a Player object from an XML document.
     *
     * @param xmlDoc the XML document
     * @return the extracted Player object
     */
    public Player extractPlayerFromXML(Document xmlDoc) {
        String nickname = XMLReader.extractValueFromXML(xmlDoc, "//nickname");
        String password = XMLReader.extractValueFromXML(xmlDoc, "//password");
        String nationality = XMLReader.extractValueFromXML(xmlDoc, "//nationality");
        int age = Integer.parseInt(XMLReader.extractValueFromXML(xmlDoc, "//age"));
        byte[] photo = java.util.Base64.getDecoder().decode(XMLReader.extractValueFromXML(xmlDoc, "//photo"));
        return new Player(nickname, password, nationality, age, photo);
    }

    /**
     * Registers a player by processing the XML string received from the client.
     *
     * @param xmlString the XML string received from the client
     * @return the registered Player object
     * @throws IOException if an I/O error occurs
     */
    public Player registerPlayer(String xmlString) throws IOException {
        Player player = null;
        try {
            // Convert XML string to Document
            Document xmlDoc = XMLReader.convertStringToDocument(xmlString);

            // Validate XML against XSD
            if (XMLReader.validateXML(xmlDoc, PLAYER_XSD)) {
                // Extract Player object from XML using PlayerService
                player = extractPlayerFromXML(xmlDoc);

                // Register player in the database
                if (playerDatabase.registerPlayer(player, out)) {
                    // Notify client that registration is successful
                    out.writeObject("Registration successful. Welcome, " + player.getNickname() + "!");
                    out.flush();
                }
            } else {
                // Notify client that the XML is invalid
                out.writeObject("Invalid XML format.");
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.writeObject("Error processing registration.");
            out.flush();
        }
        return player;
    }
}
