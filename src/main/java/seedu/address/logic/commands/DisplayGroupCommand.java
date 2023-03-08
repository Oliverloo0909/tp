package seedu.address.logic.commands;

import seedu.address.model.Model;

import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TAGS;


/**
 * Lists all groups in the address book to the user.
 */
public class DisplayGroupCommand extends Command {
    public static final String COMMAND_WORD = "display";

    public static final String MESSAGE_SUCCESS = "Listed all groups";

    public static final String MESSAGE_TEMPORARY_OUTPUT = "Here are all existing groups: ";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        String results = model.getAddressBook().getTagList().toString();
        return new CommandResult(MESSAGE_TEMPORARY_OUTPUT + results);
    }
}