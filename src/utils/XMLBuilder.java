package utils;

import model.Player;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLBuilder {
    // Método para criar um documento XML de registo de jogador
    public static Document createPlayerRegistrationXML(Player player) throws Exception {
        Document doc = createDocument();
        Element rootElement = createRootElement(doc, "request", "type", "register");

        // Adiciona elementos ao documento
        appendPlayerDetails(doc, rootElement, player);

        return doc;
    }

    // Método para criar um documento XML de registro de jogadas
    public static Document createPlayXML(Player player, int row, int col) throws Exception {
        Document doc = createDocument();
        Element rootElement = createRootElement(doc, "game", null, null);

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

    // Método para criar elementos XML
    private static Element createElement(Document doc, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(textContent));
        return element;
    }

    // Método para criar o documento XML
    private static Document createDocument() throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.newDocument();
    }

    // Método para criar o elemento raiz
    private static Element createRootElement(Document doc, String rootName, String attributeName, String attributeValue) {
        Element rootElement = doc.createElement(rootName);
        if (attributeName != null && attributeValue != null) {
            rootElement.setAttribute(attributeName, attributeValue);
        }
        doc.appendChild(rootElement);
        return rootElement;
    }

    // Método para adicionar detalhes do jogador
    private static void appendPlayerDetails(Document doc, Element parent, Player player) {
        parent.appendChild(createElement(doc, "nickname", player.getNickname()));
        parent.appendChild(createElement(doc, "password", player.getPassword()));
        parent.appendChild(createElement(doc, "nationality", player.getNationality()));
        parent.appendChild(createElement(doc, "age", String.valueOf(player.getAge())));
        parent.appendChild(createElement(doc, "photo", player.getBase64Photo()));
    }
}
