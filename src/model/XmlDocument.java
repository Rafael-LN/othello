package src.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XmlDocument {
    private Document document;
    private Element rootElement;

    public XmlDocument(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                createXmlFile(filePath);
            } else {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                document = builder.parse(file);
                rootElement = document.getDocumentElement();
            }
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

    public Document getDocument() {
        return document;
    }

    public Element getRootElement() {
        return rootElement;
    }

    public NodeList getElementsByTagName (String tagName) {
        return getDocument().getElementsByTagName(tagName);
    }

    private void createXmlFile(String filePath) throws ParserConfigurationException, TransformerException {
        document = createNewDocument();

        // Write the document to the file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);
    }

    private Document createNewDocument() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document newDocument = docBuilder.newDocument();

        // Create root element
        rootElement = newDocument.createElement("players");
        newDocument.appendChild(rootElement);

        return newDocument;
    }

}
