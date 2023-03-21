package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.PersonNotFoundInGroupException;


/**
 * Adds a group to the address book.
 */
public class GroupCommand extends Command {
    public static final String COMMAND_WORD = "group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new group to the address book. "
            + "Parameters: "
            + PREFIX_GROUP + "NAME "
            + PREFIX_MOD + "(ADD/REMOVE)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP
            + "varsity " + PREFIX_MOD + "add";;

    public static final String MESSAGE_GROUP_ADDED_SUCCESS = "New group added: %1$s";
    public static final String MESSAGE_GROUP_REMOVED_SUCCESS = "Group deleted: %1$s";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the address book";

    private final Group group;
    private final boolean isAdd;

    /**
     * Creates an GroupCommand to add the specified {@code Person}
     */
    public GroupCommand(Group group) {
        requireNonNull(group);
        this.group = group;
        this.isAdd = true;
    }

    public GroupCommand(Group group, boolean isAdd) {
        requireAllNonNull(group, isAdd);
        this.group = group;
        this.isAdd = isAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (isAdd) {
            if (model.hasGroup(group)) {
                throw new CommandException(MESSAGE_DUPLICATE_GROUP);
            }
            model.addGroup(group);
            return new CommandResult(String.format(MESSAGE_GROUP_ADDED_SUCCESS, group));
        } else {
            if (!model.hasGroup(group)) {
                throw new CommandException("Cannot remove a group that does not exist.");
            }
            model.deleteGroup(group);
            return new CommandResult(String.format(MESSAGE_GROUP_REMOVED_SUCCESS, group));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupCommand // instanceof handles nulls
                && group.equals(((GroupCommand) other).group)
                && isAdd == ((GroupCommand) other).isAdd);
    }

}
