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
        rootElement.appendChild(createElement(doc, "photoUrl", player.getPhotoUrl()));

        return doc;
    }

    // Método para criar elementos XML
    private static Element createElement(Document doc, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(textContent));
        return element;
    }

    // Outros métodos para criar diferentes tipos de mensagens XML...
}
