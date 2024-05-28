package utils;

import model.Player;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.XMLConstants;
import org.xml.sax.SAXException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class XMLReader {

    public static Document convertStringToDocument(String xmlString) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(xmlString.getBytes()));
    }

    public static boolean validateXML(Document xmlDoc, String xsdPath) throws SAXException, IOException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new StreamSource(new File(xsdPath)));
        schema.newValidator().validate(new DOMSource(xmlDoc));
        return true;
    }

    public static Player extractPlayerFromXML(Document xmlDoc) {
        Element root = xmlDoc.getDocumentElement();
        String nickname = getElementValue(root, "nickname");
        String password = getElementValue(root, "password");
        String nationality = getElementValue(root, "nationality");
        int age = Integer.parseInt(getElementValue(root, "age"));
        byte[] photo = Base64.getDecoder().decode(getElementValue(root, "photo"));
        return new Player(nickname, password, nationality, age, photo);
    }

//    public static Player extractPlayFromXML(Document xmlDoc) {
//        Element root = xmlDoc.getDocumentElement();
//        String nickname = getElementValue(root, "nickname");
//        int row = Integer.parseInt(getElementValue(root, "row"));
//        int col = Integer.parseInt(getElementValue(root, "col"));
//        return new Play(nickname, color, row, col);
//    }

    private static String getElementValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }
}
