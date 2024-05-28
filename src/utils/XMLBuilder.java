package utils;

import model.Player;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLBuilder {
    // Método para criar um documento XML de registro de jogador
    public static Document createPlayerRegistrationXML(Player player) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        // Elemento raiz
        Element rootElement = doc.createElement("request");
        rootElement.setAttribute("type", "register");
        doc.appendChild(rootElement);

        // Adiciona elementos ao documento
        rootElement.appendChild(createElement(doc, "nickname", player.getNickname()));
        rootElement.appendChild(createElement(doc, "password", player.getPassword()));
        rootElement.appendChild(createElement(doc, "nationality", player.getNationality()));
        rootElement.appendChild(createElement(doc, "age", String.valueOf(player.getAge())));
        rootElement.appendChild(createElement(doc, "photo", player.getBase64Photo()));

        return doc;
    }

    // Método para criar elementos XML
    private static Element createElement(Document doc, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(textContent));
        return element;
    }

    // Método para criar um documento XML de registro de jogadas
    public static Document createPlayXML(Player player, int row, int col) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document  doc = dBuilder.newDocument();

        // Elemento raiz
        Element rootElement = doc.createElement("game");
        doc.appendChild(rootElement);

        // Adiciona elementos ao documento
        Element users = doc.createElement("players");
        rootElement.appendChild(users);

        Element user = doc.createElement("player");
        users.appendChild(user);
        user.appendChild(createElement(doc, "nickname", player.getNickname()));
        user.appendChild(createElement(doc, "color", "BLACK"));

        Element move = doc.createElement("move");
        rootElement.appendChild(move);
        move.appendChild(createElement(doc, "nickname", player.getNickname()));

        Element position = doc.createElement("position");
        rootElement.appendChild(position);
        position.appendChild(createElement(doc, "row", String.valueOf(row)));
        position.appendChild(createElement(doc, "col", String.valueOf(col)));

        return doc;
    }
}
