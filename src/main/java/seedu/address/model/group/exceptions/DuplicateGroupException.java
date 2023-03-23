package seedu.address.model.group.exceptions;

/**
 * Exception when there is a duplicate group in a UniqueGroupList.
 */
public class DuplicateGroupException extends RuntimeException {
    public DuplicateGroupException() {
        super("Operation would result in duplicate groups");
    }
}
