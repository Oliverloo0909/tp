package seedu.address.logic.commands;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Name;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;
import seedu.address.testutil.PersonBuilder;

import java.util.Arrays;


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
    public void executeDuplicateGroupCommandAddFailure() {
        UserPrefs userpref = new UserPrefs();
        AddressBook addrbkHall = new AddressBook();
        Model model = new ModelManager(addrbkHall, userpref);
        model.addGroup(hallGroup);
        GroupCommand groupHallAddCommand = new GroupCommand(hallGroup, true);
        assertThrows(CommandException.class, () -> groupHallAddCommand.execute(model));
    }

    @Test
    public void executeGroupCommandAddSuccess() throws CommandException {
        UserPrefs userpref = new UserPrefs();
        AddressBook addrbk = new AddressBook();
        AddressBook addrbkHall = new AddressBook();
        addrbkHall.addGroup(hallGroup);
        Model model = new ModelManager(addrbk, userpref);
        GroupCommand groupHallAddCommand = new GroupCommand(hallGroup, true);
        CommandResult commandResult = groupHallAddCommand.execute(model);
        assertEquals(String.format(GroupCommand.MESSAGE_GROUP_ADDED_SUCCESS, hallGroup), commandResult.getFeedbackToUser()); //same feedback
        assertEquals(model.getAddressBook(), addrbkHall); //same addressbook reached
    }

    @Test
    public void executeGroupNotFoundGroupCommandRemoveFailure() {
        UserPrefs userpref = new UserPrefs();
        AddressBook addrbk = new AddressBook();
        Model model = new ModelManager(addrbk, userpref);
        GroupCommand groupHallRemoveCommand = new GroupCommand(hallGroup, false);
        assertThrows(CommandException.class, () -> groupHallRemoveCommand.execute(model));
    }

    @Test
    public void executeGroupCommandRemoveSuccess() throws CommandException {
        UserPrefs userpref = new UserPrefs();
        AddressBook addrbk = new AddressBook();
        AddressBook addrbkHall = new AddressBook();
        Model model = new ModelManager(addrbkHall, userpref);
        model.addGroup(hallGroup);
        GroupCommand groupHallRemoveCommand = new GroupCommand(hallGroup, false);
        CommandResult commandResult = groupHallRemoveCommand.execute(model);
        assertEquals(String.format(GroupCommand.MESSAGE_GROUP_REMOVED_SUCCESS, hallGroup), commandResult.getFeedbackToUser()); //same feedback
        assertEquals(model.getAddressBook(), addrbk); //same addressbook reached
    }

    @Test
    public void equals() {
        GroupCommand groupHallCommand = new GroupCommand(hallGroup);
        GroupCommand groupVarsityCommand = new GroupCommand(varsityGroup);
        GroupCommand groupHallRemoveCommand = new GroupCommand(hallGroup, false);
        GroupCommand groupHallRemoveCommand2 = new GroupCommand(hallGroup, false);

        // same object -> returns true
        assertTrue(groupHallCommand.equals(groupHallCommand));

        // same values -> returns true
        GroupCommand groupHallCommandCopy = new GroupCommand(hallGroup);
        assertTrue(groupHallCommand.equals(groupHallCommandCopy));

        assertTrue(groupHallRemoveCommand.equals(groupHallRemoveCommand2));

        // different types -> returns false
        assertFalse(groupHallCommand.equals(1));

        // null -> returns false
        assertFalse(groupHallCommand.equals(null));

        // different person -> returns false
        assertFalse(groupHallCommand.equals(groupVarsityCommand));

        // different person -> returns false
        assertFalse(groupHallCommand.equals(groupHallRemoveCommand));
    }
}
