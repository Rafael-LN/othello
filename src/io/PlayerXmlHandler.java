package src.io;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import src.model.Player;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerXmlHandler {
    private final String XML_FILE_PATH = "players.xml";

    /**
     * Writes player information to an XML file.
     *
     * @param player The player object containing the player information to be written to the XML file.
     */
    public void writePlayerToXml(Player player) {
        try {
            // Create a new XML document
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.newDocument();

            // Create the root element for player
            Element rootElement = document.createElement("player");
            document.appendChild(rootElement);

            // Add player information to the root element
            addPlayerToRootElement(document, player, rootElement);

            // Transform the XML document
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            // Write to XML file
            StreamResult result = new StreamResult(XML_FILE_PATH);
            transformer.transform(source, result);

            // Output to console for testing
            result = new StreamResult(System.out);
            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException e) {
            // Log the exception
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error writing players to XML", e);
            // Rethrow wrapped exception
            throw new RuntimeException("Error writing players to XML", e);
        }
    }


    /**
     * Adds player information to the root element of the XML document.
     *
     * @param document    The XML document to which the player information will be added.
     * @param player      The player object containing the player information.
     * @param rootElement The root element of the XML document.
     */
    private void addPlayerToRootElement(Document document, Player player, Element rootElement) {
        // Add player attributes to the root element
        addPlayerInfoToRootElement(document, "nickname", player.getNickname(), rootElement);
        addPlayerInfoToRootElement(document, "password", player.getPassword(), rootElement);
        addPlayerInfoToRootElement(document, "nationality", player.getNationality(), rootElement);
        addPlayerInfoToRootElement(document, "age", String.valueOf(player.getAge()), rootElement);
        addPlayerInfoToRootElement(document, "photoUrl", player.getPhotoUrl(), rootElement);
        addPlayerInfoToRootElement(document, "wins", String.valueOf(player.getWins()), rootElement);
        addPlayerInfoToRootElement(document, "losses", String.valueOf(player.getLosses()), rootElement);
        addPlayerInfoToRootElement(document, "timeSpent", String.valueOf(player.getTimeSpent()), rootElement);
    }


    /**
     * Adds player information as an attribute to the root element of the XML document.
     *
     * @param document    The XML document to which the player information will be added.
     * @param infoName    The name of the player information attribute to be added (e.g., "password").
     * @param infoValue   The value of the player information attribute to be added.
     * @param rootElement The root element of the XML document.
     */
    private void addPlayerInfoToRootElement(Document document, String infoName, String infoValue, Element rootElement) {
        // Create a new element for the player information attribute
        Element infoElement = document.createElement(infoName);

        // Set the text content of the player information attribute element
        infoElement.appendChild(document.createTextNode(infoValue));

        // Append the player information attribute element to the root element of the XML document
        rootElement.appendChild(infoElement);
    }

    /**
     * TODO Reads player information from an XML file.
     *
     * @return The player object containing the player information read from the XML file.
     */
    public Player readPlayerFromXml() {
        try {
            // Create a new XML document
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(XML_FILE_PATH);

            // Get the root element of the XML document
            Element rootElement = document.getDocumentElement();

            // Get the player information from the root element
            return getPlayerFromRootElement(document, rootElement);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            // Log the exception
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error reading players from XML", e);
            // Rethrow wrapped exception
            throw new RuntimeException("Error reading players from XML", e);
        }
    }

    private Player getPlayerFromRootElement(Document document, Element rootElement) {
        // Get the player information from the root element
        String nickname = rootElement.getElementsByTagName("nickname").item(0).getTextContent();
        String password = rootElement.getElementsByTagName("password").item(0).getTextContent();
        String nationality = rootElement.getElementsByTagName("nationality").item(0).getTextContent();
        int age = Integer.parseInt(rootElement.getElementsByTagName("age").item(0).getTextContent());
        String photoUrl = rootElement.getElementsByTagName("photoUrl").item(0).getTextContent();
        int wins = Integer.parseInt(rootElement.getElementsByTagName("wins").item(0).getTextContent());
        int losses = Integer.parseInt(rootElement.getElementsByTagName("losses").item(0).getTextContent());
        int timeSpent = Integer.parseInt(rootElement.getElementsByTagName("timeSpent").item(0).getTextContent());
        Player player = new Player(nickname, password, nationality, age, photoUrl);
        player.setWins(wins);
        player.setLosses(losses);
        player.setTimeSpent(timeSpent);
        return player;
    }
}
