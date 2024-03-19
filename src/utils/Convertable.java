package utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Object that can be converted to XML element.
 *
 * @author AlanTheKnight
 */
public interface Convertable {
    /**
     * Converts the object to XML element.
     *
     * @param document the document
     * @return the XML element
     */
    Element getElement(Document document);
}
