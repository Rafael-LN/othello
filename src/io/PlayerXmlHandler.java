package src.io;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import src.model.Player;
import src.model.XmlDocument;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerXmlHandler {
    private final String XML_FILE_PATH = "players.xml";
    private XmlDocument xmlDocument;

    public PlayerXmlHandler() {
        xmlDocument = new XmlDocument(XML_FILE_PATH);
    }

    /**
     * Writes player information to an XML file.
     *
     * @param player The player object containing the player information to be written to the XML file.
     * @return false if the player exits otherwise return true and register the player
     */
    public boolean registerPlayer(Player player) {
        // Check if the player already exists in the database
        if (playerExists(player.getNickname())) {
            System.out.println("Player with nickname " + player.getNickname() + " already exists.");
            return false;
        }

        // Add player information to the root element
        addPlayerToRootElement(player);

        return true;
    }


    /**
     * Adds player information to the root element of the XML document.
     *
     * @param player The player object containing the player information.
     */
    private void addPlayerToRootElement(Player player) {
        // Add player attributes to the root element
        addPlayerInfoToRootElement("nickname", player.getNickname());
        addPlayerInfoToRootElement("password", player.getPassword());
        addPlayerInfoToRootElement("nationality", player.getNationality());
        addPlayerInfoToRootElement("age", String.valueOf(player.getAge()));
        addPlayerInfoToRootElement("photoUrl", player.getPhotoUrl());
        addPlayerInfoToRootElement("wins", String.valueOf(player.getWins()));
        addPlayerInfoToRootElement("losses", String.valueOf(player.getLosses()));
        addPlayerInfoToRootElement("timeSpent", String.valueOf(player.getTimeSpent()));
    }


    /**
     * Adds player information as an attribute to the root element of the XML document.
     *
     * @param infoName  The name of the player information attribute to be added (e.g., "password").
     * @param infoValue The value of the player information attribute to be added.
     */
    private void addPlayerInfoToRootElement(String infoName, String infoValue) {
        Document document = xmlDocument.getDocument();

        // Create a new element for the player information attribute
        Element infoElement = document.createElement(infoName);

        // Set the text content of the player information attribute element
        infoElement.appendChild(document.createTextNode(infoValue));

        // Append the player information attribute element to the root element of the XML document
        xmlDocument.getRootElement().appendChild(infoElement);
    }

    /**
     * Checks if a player with the given nickname already exists in the database.
     *
     * @param nickname The nickname of the player to check.
     * @return true if the player already exists, false otherwise.
     */
    private boolean playerExists(String nickname) {
        NodeList playerList = xmlDocument.getElementsByTagName("player");
        for (int i = 0; i < playerList.getLength(); i++) {
            Element playerElement = (Element) playerList.item(i);
            String existingNickname = getPlayerNickname(playerElement);
            if (existingNickname.equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    private String getPlayerNickname(Element playerElement) {
        Element nicknameElement = (Element) playerElement.getElementsByTagName("nickname").item(0);
        return nicknameElement.getTextContent();
    }


    /**
     * Reads player information from an XML file.
     *
     * @return The player object containing the player information read from the XML file.
     */
    public Player readPlayerFromXml() {
        // Create a new XML document
        Document document = xmlDocument.getDocument();

        // Get the root element of the XML document
        Element rootElement = document.getDocumentElement();

        // Get the player information from the root element
        return getPlayerFromRootElement(document, rootElement);

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

    public int getPlayersCount() throws XPathExpressionException {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile("count(/player)");
        Double result = (Double) expr.evaluate(xmlDocument, XPathConstants.NUMBER);
        return result.intValue();
    }

    public List<Player> getPlayers() {
        Document document = xmlDocument.getDocument();

        List<Player> players = new ArrayList<>();
        NodeList playerList = document.getElementsByTagName("player");
        for (int i = 0; i < playerList.getLength(); i++) {
            Element playerElement = (Element) playerList.item(i);
            players.add(getPlayerFromRootElement(document, playerElement));
        }
        return players;
    }


    private void handleXmlException(Exception e) {
        // Log the exception
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error reading players from XML", e);
        // Rethrow wrapped exception
        throw new RuntimeException("Error reading players from XML", e);
    }

}
