package services;

import io.PlayerDatabase;
import model.Player;
import org.w3c.dom.Document;
import utils.XMLDoc;

import java.io.ObjectOutputStream;
import java.util.List;

public class LobbyService {
    private static final String LOBBY_XSD = "src/schemas/lobby.xsd";
    private PlayerDatabase playerDatabase;
    private ObjectOutputStream out;

    public LobbyService(ObjectOutputStream out, PlayerDatabase playerDatabase) {
        this.out = out;
        this.playerDatabase = playerDatabase;
    }

    public List<Player> getPlayers() {
        return playerDatabase.loadPlayers();
    }

    public boolean challengePlayer(String username) {
        Player player = playerDatabase.getPlayer(username);
        if (player != null) {
            try {
                out.writeObject(player);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void enterLobby(Player player) {
        broadcastLobby();
    }

    public void broadcastLobby() {
        try {
            List<Player> players = getPlayers();

            Document lobbyDoc = createLobbyUpdateXML(players);
            String lobbyMessage = XMLDoc.documentToString(lobbyDoc);
            out.writeObject(lobbyMessage);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Document createLobbyUpdateXML(List<Player> players) throws Exception {
        String[][] elements = {{"type", "lobby"}};



        Document doc = XMLBuilder.createXML("updateLobbyResponse", elements, null);

        return doc;
    }

    public void handleEnterLobbyRequest(String xmlString) throws Exception {
        try {
            Document xmlDoc = XMLReader.convertStringToDocument(xmlString);
            if (XMLReader.validateXML(xmlDoc, LOBBY_XSD)) {
                String username = XMLReader.extractValueFromXML(xmlDoc, "//username");
                Player player = playerDatabase.getPlayer(username);
                if (player != null) {
                    enterLobby(player);
                } else {
                    sendResponse("error", "Player not found.");
                }
            } else {
                sendResponse("error", "Invalid XML format.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse("error", "Error processing lobby entry.");
        }
    }

    private void sendResponse(String status, String message) throws Exception {
        String[][] elements = {
                {"status", status},
                {"message", message}
        };
        Document responseXML = XMLBuilder.createXML("response", elements, new String[][]{{"type", "response"}});
        String responseXMLString = XMLHandler.documentToString(responseXML);
        out.writeObject(responseXMLString);
        out.flush();
    }
}
