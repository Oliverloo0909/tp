package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.GroupModifyCommandParser.IS_ADD_NOT_SPECIFIED_PARSE_FAILURE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GroupModifyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Name;
import seedu.address.model.group.Group;

/**
 * Contains integration tests (interaction with the Model)
 * and unit tests for GroupModifyCommandParser.
 */
public class GroupModifyCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GroupModifyCommand.MESSAGE_USAGE);
    private static final String TAG_FIELD_NOT_PRESENT =
            String.format(GroupModifyCommandParser.MESSAGE_GROUP_DOES_NOT_EXIST_PARSE_FAILURE,
                    GroupModifyCommand.MESSAGE_USAGE);
    private static final String MOD_FIELD_NOT_SPECIFIED = String.format(IS_ADD_NOT_SPECIFIED_PARSE_FAILURE,
            GroupModifyCommand.MESSAGE_USAGE);
    private GroupModifyCommandParser parser = new GroupModifyCommandParser();

    @Test
    public void parse_missingParts_failure() {
        String test1 = "g/Hall";
        String test2 = "1 ";
        String test3 = " ";
        String test4 = "m/add";

        // nothing specified
        assertParseFailure(parser, test3, MESSAGE_INVALID_FORMAT);

        // no index specified
        assertParseFailure(parser, test1, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, test2, TAG_FIELD_NOT_PRESENT);

        // no field
        assertParseFailure(parser, test4, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_modFieldEmptyFailure() {
        assertParseFailure(parser, "1 g/group", MOD_FIELD_NOT_SPECIFIED);
    }

    @Test
    public void parse_groupAddGroup_success() throws ParseException {
        Index targetIndex = INDEX_FIRST_PERSON;
        String input = targetIndex.getOneBased() + " g/Hall" + " m/add";
        // One group
        Group expectedGroup = new Group(new Name("Hall"));
        GroupModifyCommand expectedCommand = new GroupModifyCommand(targetIndex, expectedGroup, true);
        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_groupRemoveGroup_success() throws ParseException {
        Index targetIndex = INDEX_FIRST_PERSON;
        String input = targetIndex.getOneBased() + " g/Hall" + " m/remove";
        // One group
        Group expectedGroup = new Group(new Name("Hall"));
        GroupModifyCommand expectedCommand = new GroupModifyCommand(targetIndex, expectedGroup, false);
        assertParseSuccess(parser, input, expectedCommand);
    }

}
