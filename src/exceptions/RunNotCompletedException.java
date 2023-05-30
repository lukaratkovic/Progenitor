package exceptions;

public class RunNotCompletedException extends RuntimeException{
    public RunNotCompletedException() {
        super();
    }

    public RunNotCompletedException(String message) {
        super(message);
    }
}
