package seedu.address.model.group.exceptions;

/**
 * Exception when person cannot be found.
 */
public class PersonNotFoundInGroupException extends RuntimeException {
    public PersonNotFoundInGroupException() {
        super("Person cannot be found in group!");
    }
}
