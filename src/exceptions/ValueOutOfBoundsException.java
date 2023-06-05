package exceptions;

/**
 * Thrown when attempting to create a gene with a value outside its defined bounds
 */
public class ValueOutOfBoundsException extends RuntimeException{
    public ValueOutOfBoundsException() {
        super();
    }

    public ValueOutOfBoundsException(String message) {
        super(message);
    }
}
