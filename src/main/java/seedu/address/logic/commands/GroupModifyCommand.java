package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;



/**
 * Adds a person to a group to in the address book.
 */
public class GroupModifyCommand extends Command {

    public static final String COMMAND_WORD = "groupmod";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add or delete a person of index i to/from a group specified. "
            + "Parameters: INDEX (must be a positive integer) "
            + "" + PREFIX_GROUP + "GROUP...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_GROUP + "varsity";
    public static final String GROUP_ADD_PERSON_SUCCESS = "Added Person: %1$s to Group: %2$s";
    public static final String GROUP_REMOVE_PERSON_SUCCESS = "Removed Person: %1$s from Group: %2$s";
    public static final String GROUP_NOT_FOUND_FAILURE = "The group: %1$s cannot be found. "
            + "Here are the list of existing groups: %2$s";
    public static final String STUDENT_ALREADY_ADDED_FAILURE = "Student already belongs to %1$s";

    private Group groupToMod;
    private Index index;
    private boolean isAdd;

    /**
     * command constructor
     * @param index                of the person in the filtered person list to edit
     * @param groupToMod details to edit the person with
     */
    public GroupModifyCommand(Index index, Group groupToMod, boolean isAdd) {
        this.index = index;
        this.groupToMod = groupToMod;
        this.isAdd = isAdd;
    }

    //add by default
    public GroupModifyCommand(Index index, Group groupToMod) {
        this.index = index;
        this.groupToMod = groupToMod;
        this.isAdd = true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();


        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (!model.hasGroup(groupToMod)) {
            throw new CommandException(String.format(GROUP_NOT_FOUND_FAILURE,
                    groupToMod.toString(),
                    model.getAddressBook().getGroupList().toString()));
        }

        Person personToGroupMod = lastShownList.get(index.getZeroBased());

        if (groupToMod.getPersons().contains(personToGroupMod)) {
            throw new CommandException(STUDENT_ALREADY_ADDED_FAILURE);
        }

        if (isAdd) {
            try {
                model.addPersonToGroup(personToGroupMod, groupToMod);
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                return new CommandResult(String.format(
                        GROUP_ADD_PERSON_SUCCESS,
                        personToGroupMod.getName(),
                        groupToMod));
            } catch (Exception e) {
                throw new CommandException(e.getMessage());
            }
        } else {

            try {
                model.removePersonFromGroup(personToGroupMod, groupToMod);
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                return new CommandResult(String.format(
                        GROUP_REMOVE_PERSON_SUCCESS,
                        personToGroupMod.getName(),
                        groupToMod));
            } catch (Exception e) {
                throw new CommandException(e.getMessage());
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof GroupModifyCommand)) {
            return false;
        }
        GroupModifyCommand e = (GroupModifyCommand) other;
        return index.equals(e.index)
                && groupToMod.equals(e.groupToMod)
                && isAdd == e.isAdd;
    }
}
