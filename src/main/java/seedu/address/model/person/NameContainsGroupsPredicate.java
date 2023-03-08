package seedu.address.model.person;

import seedu.address.model.group.Group;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Tests that a {@code Person}'s {@code Group} matches any of the groups given.
 */
public class NameContainsGroupsPredicate implements Predicate<Person> {
    private final List<String> groups;

    public NameContainsGroupsPredicate(List<String> groups) {
        this.groups = groups;
    }

    @Override
    public boolean test(Person person) {
        return groups.stream()
                .anyMatch(keyword -> person.getGroups().contains(new Group(keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsGroupsPredicate // instanceof handles nulls
                && groups.equals(((NameContainsGroupsPredicate) other).groups)); // state check
    }

    @Override
    public String toString() {
        return groups.stream().collect(Collectors.joining(" , "));
    }
}
