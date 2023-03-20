package seedu.address.logic.commands;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.Name;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;



public class GroupCommandTest {

    private Group hallGroup = new Group(new Name("Hall"));
    private Group varsityGroup = new Group(new Name("Varsity"));

    @Test
    public void constructorNullTagthrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GroupCommand(null));
    }

    @Test
    public void executeNullModelThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GroupCommand(hallGroup).execute(null));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        GroupCommand groupHallCommand = new GroupCommand(hallGroup);
        GroupCommand groupVarsityCommand = new GroupCommand(varsityGroup);

        // same object -> returns true
        assertTrue(groupHallCommand.equals(groupHallCommand));

        // same values -> returns true
        GroupCommand groupHallCommandCopy = new GroupCommand(hallGroup);
        assertTrue(groupHallCommand.equals(groupHallCommandCopy));

        // different types -> returns false
        assertFalse(groupHallCommand.equals(1));

        // null -> returns false
        assertFalse(groupHallCommand.equals(null));

        // different person -> returns false
        assertFalse(groupHallCommand.equals(groupVarsityCommand));
    }
}
