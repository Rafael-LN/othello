package services;

import io.PlayerDatabase;
import model.Player;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.XMLDoc;
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
    private static final String LOGIN_XSD = "src/schemas/login.xsd";
    private static final String EDIT_XSD = "src/schemas/editInfo.xsd";
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
        String namespace = "http://www.example.org/player";
        String[][] elements = {
                {"nickname", player.getNickname()},
                {"password", player.getPassword()},
                {"nationality", player.getNationality()},
                {"age", String.valueOf(player.getAge())},
                {"photo", player.getBase64Photo()}
        };

        // Create the XML document
        Document doc = XMLDoc.createDocument();
        Element root = XMLDoc.createRootElement(doc, "playerRequest", namespace);
        root.setAttribute("type", "requestType");

        // Create the playerInfo element and append child elements
        Element playerInfo = doc.createElement("playerInfo");
        for (String[] element : elements) {
            playerInfo.appendChild(XMLDoc.createElement(doc, element[0], element[1]));
        }
        root.appendChild(playerInfo);

        // Convert the document to a string
        String playerXMLString = XMLDoc.documentToString(doc);

        // Send the XML string to the server
        out.writeObject(playerXMLString);
        out.flush();

        System.out.println("Player registration sent:\n" + playerXMLString);
    }

    /**
     * Logs in a player by creating an XML document and sending it to the server.
     *
     * @param player the Player object containing login details
     * @throws Exception if an error occurs during the login process
     */
    public void loginPlayer(Player player) throws Exception {
        String namespace = "http://www.example.org/login";
        String[][] elements = {
                {"username", player.getNickname()},
                {"password", player.getPassword()}
        };

        // Create the XML document
        Document doc = XMLDoc.createDocument();
        Element root = XMLDoc.createRootElement(doc, "loginRequest", namespace);
        root.setAttribute("type", "login");

        // Append child elements to the root
        for (String[] element : elements) {
            root.appendChild(XMLDoc.createElement(doc, element[0], element[1]));
        }

        // Convert the document to a string
        String playerXMLString = XMLDoc.documentToString(doc);

        // Send the XML string to the server
        out.writeObject(playerXMLString);
        out.flush();
    }

    /**
     * Extracts a Player object from an XML document.
     *
     * @param xmlDoc the XML document
     * @return the extracted Player object
     */
    private Player extractPlayerFromXML(Document xmlDoc) {
        String nickname = XMLReader.extractValueFromXML(xmlDoc, "//nickname");
        String password = XMLReader.extractValueFromXML(xmlDoc, "//password");
        String nationality = XMLReader.extractValueFromXML(xmlDoc, "//nationality");
        int age = Integer.parseInt(XMLReader.extractValueFromXML(xmlDoc, "//age"));
        byte[] photo = Base64.getDecoder().decode(XMLReader.extractValueFromXML(xmlDoc, "//photo"));
        return new Player(nickname, password, nationality, age, photo);
    }

    private Player extractLoginFromXML(Document xmlDoc) {
        String username = XMLReader.extractValueFromXML(xmlDoc, "//username");
        String password = XMLReader.extractValueFromXML(xmlDoc, "//password");
        return new Player(username, password);
    }

    /**
     * Registers a player by processing the XML string received from the client.
     *
     * @param xmlString the XML string received from the client
     * @return the registered Player object
     * @throws IOException if an I/O error occurs
     */
    public Player registerPlayer(String xmlString) throws Exception {
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
                    sendResponse("success", "Registration successful. Welcome, " + player.getNickname() + "!");
                }
            } else {
                // Notify client that the XML is invalid
                sendResponse("error", "Invalid XML format.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse("error", "Error processing registration.");
        }
        return player;
    }

    /**
     * Sends a response to the client.
     *
     * @param status  the status of the response (e.g., "success" or "error")
     * @param message the message to be sent
     * @throws IOException if an I/O error occurs
     */
    private void sendResponse(String status, String message) throws Exception {
        String namespace = "http://www.example.org/player";
        String[][] elements = {
                {"status", status},
                {"message", message}
        };

        // Create the XML document
        Document doc = XMLDoc.createDocument();
        Element root = XMLDoc.createRootElement(doc, "playerResponse", namespace);
        root.setAttribute("type", "response");

        // Append child elements to the root
        for (String[] element : elements) {
            root.appendChild(XMLDoc.createElement(doc, element[0], element[1]));
        }

        // Convert the document to a string
        String responseXMLString = XMLDoc.documentToString(doc);

        // Send the XML string to the client
        out.writeObject(responseXMLString);
        out.flush();
    }

    /**
     * Logs in a player by processing the XML string received from the client.
     *
     * @param xmlString the XML string received from the client
     * @return the logged-in Player object
     * @throws IOException if an I/O error occurs
     */
    public Player loginPlayer(String xmlString) throws Exception {
        Player player = null;
        try {
            // Convert XML string to Document
            Document xmlDoc = XMLReader.convertStringToDocument(xmlString);

            // Validate XML against XSD
            if (XMLReader.validateXML(xmlDoc, LOGIN_XSD)) {
                // Extract Player object from XML using PlayerService
                player = extractLoginFromXML(xmlDoc);

                // Validate player login
                if (playerDatabase.validateLogin(player)) {
                    // Notify client that login is successful
                    sendResponse("success", "Login successful. Welcome, " + player.getNickname() + "!");
                }
            } else {
                // Notify client that the XML is invalid
                sendResponse("error", "Invalid XML format.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse("error", "Error processing login.");
        }
        return player;
    }

    /**
     * Edits a player's information by processing the XML string received from the client.
     *
     * @param xmlString the XML string received from the client
     * @throws Exception if an error occurs during the process
     */
    public void editPlayerInfo(String xmlString) throws Exception {
        try {
            // Convert XML string to Document
            Document xmlDoc = XMLReader.convertStringToDocument(xmlString);

            // Validate XML against XSD
            if (XMLReader.validateXML(xmlDoc, EDIT_XSD)) {
                Player editedPlayer = extractPlayerFromXML(xmlDoc);

                playerDatabase.updatePlayer(editedPlayer);

                // Notify client that the information update is successful
                sendResponse("success", "Information updated successfully");
            } else {
                // Notify client that the XML is invalid
                sendResponse("error", "Invalid XML format.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse("error", "Error processing information update.");
        }
    }
}
