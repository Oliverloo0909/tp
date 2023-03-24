package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TAGS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGroups.*;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Name;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for GroupModifyCommand.
 */
public class GroupModifyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private AddressBook addressBook = getTypicalAddressBook();
    private Name hall = new Name("Hall");
    private Name varsity = new Name("Varsity");
    private Group hallGroup = new Group(hall);
    private Group diffGroup = new Group(varsity);
    private String groupInput = "Hall";

    @Test
    public void execute_invalidIndex_failure() throws ParseException, CommandException {
        Index index = ParserUtil.parseIndex("100");
        assertThrows(CommandException.class, () -> new GroupModifyCommand(index, hallGroup).execute(model));
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        GroupModifyCommand command = new GroupModifyCommand(outOfBoundIndex, hallGroup);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        GroupModifyCommand command = new GroupModifyCommand(outOfBoundIndex, hallGroup);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void addToModifiedGroup_groupNotFound_failure() {
        String groupInput = "(Hall)";
        GroupModifyCommand GroupModifyCommand = new GroupModifyCommand(INDEX_FIRST_PERSON, hallGroup);

        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        String results = model.getAddressBook()
                .getTagList().toString();
        assertCommandFailure(GroupModifyCommand, model,
                String.format(GroupModifyCommand.GROUP_NOT_FOUND_FAILURE,
                        groupInput, results));
    }

    @Test
    public void addToModifiedGroup_studentAlreadyAdded_failure() throws CommandException {
        GroupModifyCommand groupModifyCommand = new GroupModifyCommand(INDEX_FIRST_PERSON, hallGroup);
        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);

        model.setAddressBook(new AddressBookBuilder()
                .withPerson(ALICE).withTag(new Tag("friends")).withGroup(GROUP_WITH_ALICE)
                .build());
        assertThrows(CommandException.class, () -> groupModifyCommand.execute(model));
    }

    @Test
    public void execute_GroupModify_Add_Command_success() throws CommandException {

        Group hallGroupWithAlice = new Group(hall, new ArrayList<>(Arrays.asList(ALICE)));

        GroupModifyCommand GroupModifyCommand = new GroupModifyCommand(INDEX_FIRST_PERSON, hallGroup, true);

        CommandResult commandResult = new CommandResult(String.format(GroupModifyCommand.GROUP_ADD_PERSON_SUCCESS,
                ALICE.getName(), hallGroup));

        //expectedModel
        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs());
        expectedModel.addGroup(hallGroupWithAlice);
        Person expectedPerson = new PersonBuilder().withName("Alice Pauline")
                .withAddress("123, Jurong West Ave 6, #08-111").withPayRate("10")
                .withPhone("94351253").withSession("01-01-2022 12:00", "01-01-2022 13:00")
                .withTags("private", "friends", "hall")
                .build();
        expectedModel.addPerson(expectedPerson);


        //create a test model
        Model actualModel = new ModelManager(new AddressBook(), new UserPrefs());
        actualModel.setAddressBook(new AddressBookBuilder().withPerson(ALICE)
                .build());
        actualModel.addGroup(hallGroup);

        actualModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        actualModel.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);

        assertEquals(commandResult, GroupModifyCommand.execute(actualModel));


        assertEquals(expectedModel.getAddressBook().getGroupList(), expectedModel.getAddressBook().getGroupList());

        assertEquals(expectedModel.getAddressBook().getPersonList(), expectedModel.getAddressBook().getPersonList());
    }

    @Test
    public void equals() {
        final GroupModifyCommand standardCommand = new GroupModifyCommand(INDEX_FIRST_PERSON, hallGroup);

        // same values -> returns true
        GroupModifyCommand commandWithSameValues = new GroupModifyCommand(INDEX_FIRST_PERSON, hallGroup);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new GroupModifyCommand(INDEX_SECOND_PERSON, hallGroup)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new GroupModifyCommand(INDEX_FIRST_PERSON, diffGroup)));
    }
}
