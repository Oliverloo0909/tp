package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GroupCommand;
import seedu.address.logic.commands.GroupModifyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Name;
import seedu.address.model.group.Group;
import seedu.address.model.tag.Tag;

public class GroupCommandParserTest {
    private GroupCommandParser parser = new GroupCommandParser();
    private String groupHall = "Hall";
    private String groupVar = "Varsity";
    private Group hall = new Group(new Name(groupHall));


    @Test
    public void parse_nameMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE);
        // non name
        assertParseFailure(parser, " ", expectedMessage);
    }

    @Test
    public void parse_groupAddGroup_success() throws ParseException {
        String input = " g/Hall" + " m/add";
        Group expectedGroup = new Group(new Name("Hall"));
        GroupCommand expectedCommand = new GroupCommand(expectedGroup, true);
        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_groupRemoveGroup_success() throws ParseException {
        String input = " g/Hall" + " m/remove";
        Group expectedGroup = new Group(new Name("Hall"));
        GroupCommand expectedCommand = new GroupCommand(expectedGroup, false);
        assertParseSuccess(parser, input, expectedCommand);
    }
}
