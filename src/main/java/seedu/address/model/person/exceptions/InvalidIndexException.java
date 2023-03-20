package seedu.address.model.person.exceptions;

public class InvalidIndexException extends RuntimeException {
    public InvalidIndexException() {
    super("Index out of bounds");
}
}
