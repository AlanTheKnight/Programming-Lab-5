package models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.Convertable;
import utils.ElementConversionException;
import utils.Validatable;

/**
 * Worker's coordinates model.
 *
 * @author AlanTheKnight
 */
public class Coordinates implements Validatable, Convertable {
    /**
     * X coordinate (not null).
     */
    private final int x;

    /**
     * Y coordinate (not null, greater than -154).
     */
    private final Float y;

    /**
     * Creates a new Coordinates object.
     *
     * @param x x
     * @param y y
     */
    public Coordinates(int x, Float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new Coordinates object from the given XML element.
     *
     * @param element XML element
     * @return new Coordinates object
     * @throws ElementConversionException if the element is invalid
     */
    public static Coordinates fromElement(Element element) throws ElementConversionException {
        try {
            int x = Integer.parseInt(element.getElementsByTagName("x").item(0).getTextContent());
            Float y = Float.parseFloat(element.getElementsByTagName("y").item(0).getTextContent());
            return new Coordinates(x, y);
        } catch (NullPointerException e) {
            throw new ElementConversionException(e.getMessage());
        }
    }

    @Override
    public boolean validate() {
        return y != null && y > -154;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Coordinates that = (Coordinates) obj;
        return x == that.x && y.equals(that.y);
    }

    @Override
    public int hashCode() {
        return x + y.hashCode();
    }

    @Override
    public String toString() {
        return "Coordinates{" + "x=" + x + ", y=" + y + '}';
    }

    /**
     * Creates a new XML element from the coordinates.
     *
     * @param document XML document
     * @return new XML element
     */
    @Override
    public Element getElement(Document document) {
        Element coordinatesElement = document.createElement("coordinates");

        Element xElement = document.createElement("x");
        xElement.setTextContent(String.valueOf(x));
        coordinatesElement.appendChild(xElement);

        Element yElement = document.createElement("y");
        yElement.setTextContent(String.valueOf(y));
        coordinatesElement.appendChild(yElement);

        return coordinatesElement;
    }
}
