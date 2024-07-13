package utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A utility class for creating XML documents.
 * <p>
 * This class is based on XMLDoc provided by Eng. Porfírio Filipe.
 */
public class XMLBuilder extends XMLHandler {

    /**
     * Creates a generic XML document.
     *
     * @param rootName the name of the root element
     * @param elements an array of element tag names and their corresponding text contents
     * @param attributes an array of attribute names and values for the root element (nullable)
     * @return the created XML document
     * @throws Exception if an error occurs during document creation
     */
    public static Document createXML(String rootName, String[][] elements, String[][] attributes) throws Exception {
        Document doc = createDocument();
        Element rootElement = createRootElement(doc, rootName, attributes);

        for (String[] element : elements) {
            appendChildElements(doc, rootElement, new String[]{element[0]}, new String[]{element[1]});
        }

        return doc;
    }

    /**
     * Creates a root element with optional attributes.
     *
     * @param doc the XML document
     * @param rootName the name of the root element
     * @param attributes an array of attribute names and values for the root element (nullable)
     * @return the created root element
     */
    private static Element createRootElement(Document doc, String rootName, String[][] attributes) {
        Element rootElement = doc.createElement(rootName);
        if (attributes != null) {
            for (String[] attribute : attributes) {
                rootElement.setAttribute(attribute[0], attribute[1]);
            }
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
}
