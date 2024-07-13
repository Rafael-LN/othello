package utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import java.io.*;

/**
 * A utility class for handling XML documents, including creation, parsing, transformation, and validation.
 * <p>
 * This class is based on XMLDoc provided by Eng. Porfírio Filipe.
 */
public class XMLHandler {

    /**
     * Creates a new XML document.
     *
     * @return a new Document object
     * @throws Exception if an error occurs during document creation
     */
    protected static Document createDocument() throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.newDocument();
    }

    /**
     * Creates a root element with an optional attribute.
     *
     * @param doc the XML document
     * @param rootName the name of the root element
     * @param attributeName the name of the attribute (nullable)
     * @param attributeValue the value of the attribute (nullable)
     * @return the created root element
     */
    protected static Element createRootElement(Document doc, String rootName, String attributeName, String attributeValue) {
        Element rootElement = doc.createElement(rootName);
        if (attributeName != null && attributeValue != null) {
            rootElement.setAttribute(attributeName, attributeValue);
        }
        doc.appendChild(rootElement);
        return rootElement;
    }

    /**
     * Adds child elements to a parent element.
     *
     * @param doc the XML document
     * @param parent the parent element
     * @param tagNames an array of tag names for the child elements
     * @param textContents an array of text contents for the child elements
     */
    protected static void appendChildElements(Document doc, Element parent, String[] tagNames, String[] textContents) {
        for (int i = 0; i < tagNames.length; i++) {
            parent.appendChild(createElement(doc, tagNames[i], textContents[i]));
        }
    }

    /**
     * Creates a new element with text content.
     *
     * @param doc the XML document
     * @param tagName the tag name of the element
     * @param textContent the text content of the element
     * @return the created element
     */
    protected static Element createElement(Document doc, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(textContent));
        return element;
    }

    /**
     * Converts an XML document to a string.
     *
     * @param xmlDoc the XML document
     * @return the XML document as a string
     * @throws TransformerFactoryConfigurationError if a configuration error occurs in the TransformerFactory
     * @throws TransformerException if an error occurs during the transformation
     */
    public static String documentToString(Document xmlDoc) throws TransformerFactoryConfigurationError, TransformerException {
        if (xmlDoc == null) {
            return null;
        }

        Writer out = new StringWriter();
        Transformer tf = TransformerFactory.newInstance().newTransformer();

        tf.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        tf.setOutputProperty(OutputKeys.VERSION, "1.0");
        if (xmlDoc.getXmlEncoding() != null)
            tf.setOutputProperty(OutputKeys.ENCODING, xmlDoc.getXmlEncoding());
        else
            tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        tf.setOutputProperty(OutputKeys.INDENT, "no");
        tf.setOutputProperty("{http://util.apache.org/xslt}indent-amount", "2");

        tf.transform(new DOMSource(xmlDoc), new StreamResult(out));
        return out.toString();
    }

    /**
     * Parses an XML string and returns a Document object.
     *
     * @param xmlStr the XML string
     * @return the parsed Document object
     * @throws Exception if an error occurs during parsing
     */
    protected static Document parseString(String xmlStr) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlStr));
        return builder.parse(is);
    }

    /**
     * Parses an XML file and returns a Document object.
     *
     * @param fileName the name of the XML file
     * @return the parsed Document object
     */
    protected static Document parseFile(String fileName) {
        DocumentBuilder docBuilder;
        Document doc = null;
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setIgnoringElementContentWhitespace(true);
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
        } catch (Exception e) {
            System.out.println("Wrong parser configuration: " + e.getMessage());
            return null;
        }
        File sourceFile = new File(fileName);
        try {
            doc = docBuilder.parse(sourceFile);
        } catch (SAXException | IOException e) {
            System.out.println("Could not read source file: " + e.getLocalizedMessage());
        }
        return doc;
    }

    /**
     * Validates an XML document against an XSD schema.
     *
     * @param xmlDoc the XML document to validate
     * @param vFileName the name of the XSD schema file
     * @throws SAXException if a SAX error occurs during validation
     * @throws IOException if an IO error occurs during validation
     */
    protected static void validDocXSD(Document xmlDoc, String vFileName) throws SAXException, IOException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile = new StreamSource(new File(vFileName));
        Schema schema = factory.newSchema(schemaFile);
        Validator validator = schema.newValidator();
        validator.validate(new DOMSource(xmlDoc));
    }

    /**
     * Executes an XPath expression and returns the value of the first matching node.
     *
     * @param expression the XPath expression
     * @param doc the XML document
     * @return the value of the first matching node
     */
    protected static String getXPathValue(String expression, Document doc) {
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            return (String) xpath.evaluate(expression, doc, XPathConstants.STRING);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
