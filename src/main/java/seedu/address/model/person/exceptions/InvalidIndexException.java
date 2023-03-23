package seedu.address.model.person.exceptions;

/**
 * Exception when there is an invalid index
 */
public class InvalidIndexException extends RuntimeException {
    public InvalidIndexException() {
        super("Index out of bounds");
    }
}
