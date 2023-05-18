package exceptions;

public class ValueOutOfBoundsException extends RuntimeException{
    public ValueOutOfBoundsException() {
        super();
    }

    public ValueOutOfBoundsException(String message) {
        super(message);
    }
}
