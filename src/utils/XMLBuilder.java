package utils;

import model.Player;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

public class XMLBuilder {

    public static Document createLoginXML(Player player) throws Exception {
        Document doc = createDocument();
        Element rootElement = createRootElement(doc, "request", "type", "login");

        appendChildElements(doc, rootElement,
                new String[]{"nickname", "password"},
                new String[]{player.getNickname(), player.getPassword()});

        return doc;
    }

    public static Document createPlayerRegistrationXML(Player player) throws Exception {
        Document doc = createDocument();
        Element rootElement = createRootElement(doc, "request", "type", "register");

        appendChildElements(doc, rootElement,
                new String[]{"nickname", "password", "nationality", "age", "photo"},
                new String[]{player.getNickname(), player.getPassword(), player.getNationality(),
                        String.valueOf(player.getAge()), player.getBase64Photo()});
        return doc;
    }

    public static Document createPlayXML(Player player, int row, int col) throws Exception {
        Document doc = createDocument();
        Element rootElement = createRootElement(doc, "game", null, null);

        Element users = doc.createElement("players");
        rootElement.appendChild(users);

        Element user = doc.createElement("player");
        users.appendChild(user);

        appendChildElements(doc, user,
                new String[]{"nickname", "color"},
                new String[]{player.getNickname(), "BLACK"});

        Element move = doc.createElement("move");
        rootElement.appendChild(move);
        appendChildElements(doc, move, new String[]{"nickname"}, new String[]{player.getNickname()});

        Element position = doc.createElement("position");
        rootElement.appendChild(position);
        appendChildElements(doc, position, new String[]{"row", "col"},
                new String[]{String.valueOf(row), String.valueOf(col)});

        return doc;
    }

    private static void appendChildElements(Document doc, Element parent, String[] tagNames, String[] textContents) {
        for (int i = 0; i < tagNames.length; i++) {
            parent.appendChild(createElement(doc, tagNames[i], textContents[i]));
        }
    }

    private static Element createElement(Document doc, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(textContent));
        return element;
    }

    private static Document createDocument() throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.newDocument();
    }

    private static Element createRootElement(Document doc, String rootName, String attributeName, String attributeValue) {
        Element rootElement = doc.createElement(rootName);
        if (attributeName != null && attributeValue != null) {
            rootElement.setAttribute(attributeName, attributeValue);
        }
        doc.appendChild(rootElement);
        return rootElement;
    }

    public static boolean validateXML(Document xml, String xsdPath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(xml));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
