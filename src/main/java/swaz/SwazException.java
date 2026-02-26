package swaz;

/**
 * Represents application-specific exceptions for Swaz.
 * Used to signal user input errors and storage-related errors.
 */
public class SwazException extends Exception {

    /**
     * Creates a SwazException with the given message.
     *
     * @param message error message to be shown to the user
     */
    public SwazException(String message) {
        super(message);
    }
}
