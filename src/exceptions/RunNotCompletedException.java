package exceptions;

/**
 * Thrown when a post-run parameter is requested from the Progenitor class before a run has been executed.
 */
public class RunNotCompletedException extends RuntimeException{
    public RunNotCompletedException() {
        super();
    }

    public RunNotCompletedException(String message) {
        super(message);
    }
}
