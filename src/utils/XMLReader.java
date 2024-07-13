package utils;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import java.io.IOException;

/**
 * A utility class for reading and validating XML documents.
 * <p>
 * This class is based on XMLDoc provided by Eng. Porfírio Filipe.
 */
public class XMLReader extends XMLHandler {

    /**
     * Validates an XML document against an XSD schema.
     *
     * @param xmlDoc the XML document to validate
     * @param xsdPath the path to the XSD schema file
     * @return true if the document is valid, false otherwise
     * @throws SAXException if a SAX error occurs during validation
     * @throws IOException if an IO error occurs during validation
     */
    public static boolean validateXML(Document xmlDoc, String xsdPath) throws SAXException, IOException {
        try {
            validDocXSD(xmlDoc, xsdPath);
            return true;
        } catch (SAXException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Extracts a value from an XML document using an XPath expression.
     *
     * @param xmlDoc the XML document
     * @param expression the XPath expression
     * @return the value of the first matching node
     */
    public static String extractValueFromXML(Document xmlDoc, String expression) {
        return getXPathValue(expression, xmlDoc);
    }

    /**
     * Converts an XML string to a Document object.
     *
     * @param xmlString the XML string
     * @return the parsed Document object
     * @throws Exception if an error occurs during parsing
     */
    public static Document convertStringToDocument(String xmlString) throws Exception {
        return parseString(xmlString);
    }

    /**
     * Converts an XML file to a Document object.
     *
     * @param xmlFilePath the path to the XML file
     * @return the parsed Document object
     */
    public static Document convertFileToDocument(String xmlFilePath) {
        return parseFile(xmlFilePath);
    }
}
