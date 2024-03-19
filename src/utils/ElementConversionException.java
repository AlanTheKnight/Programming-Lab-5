package utils;

/**
 * Exception thrown when a model can't be created from XML element.
 */
public class ElementConversionException extends Exception {
    public ElementConversionException(String message) {
        super(message);
    }
}
