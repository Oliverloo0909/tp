package seedu.address.model.group.exceptions;

public class PersonNotFoundInGroupException extends RuntimeException {
    public PersonNotFoundInGroupException() {
        super("Person cannot be found in group!");
    }
}
