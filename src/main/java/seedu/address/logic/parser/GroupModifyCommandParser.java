package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOD;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GroupModifyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;

/**
 * Parses input arguments and creates a new GroupAddCommand object
 */
public class GroupModifyCommandParser implements Parser<GroupModifyCommand> {

    /**
     * informs user that he/she did not add the tag field
     */
    public static final String MESSAGE_GROUP_DOES_NOT_EXIST_PARSE_FAILURE = "Did not specify group g/ field! \n%1$s";
    public static final String IS_ADD_NOT_SPECIFIED_PARSE_FAILURE = "Did not specify m/ field! \n%1$s";
    /**
     * Parses the given {@code String} of arguments in the context of the GroupAddCommand
     * and returns an specific case of GroupAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GroupModifyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GROUP, PREFIX_MOD);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GroupModifyCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_GROUP).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_GROUP_DOES_NOT_EXIST_PARSE_FAILURE,
                    GroupModifyCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_MOD).isEmpty()) {
            throw new ParseException(String.format(IS_ADD_NOT_SPECIFIED_PARSE_FAILURE,
                    GroupModifyCommand.MESSAGE_USAGE));
        }

        Group group = ParserUtil.parseGroup(argMultimap.getValue(PREFIX_GROUP).get());
        boolean isAdd = ParserUtil.parseIsAdd(argMultimap.getValue(PREFIX_MOD).get());
        return new GroupModifyCommand(index, group, isAdd);
    }
}
