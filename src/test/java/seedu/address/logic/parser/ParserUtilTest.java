package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.PayRate;
import seedu.address.model.person.Phone;
import seedu.address.model.group.Group;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_PAY_RATE = "-23";
    private static final String INVALID_GROUP = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_PAY_RATE = "0";
    private static final String VALID_GROUP_1 = "friend";
    private static final String VALID_GROUP_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parsePayRate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePayRate((String) null));
    }

    @Test
    public void parsePayRate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePayRate(INVALID_PAY_RATE));
    }

    @Test
    public void parsePayRate_validValueWithoutWhitespace_returnsPayRate() throws Exception {
        PayRate expectedPayRate = new PayRate(VALID_PAY_RATE);
        assertEquals(expectedPayRate, ParserUtil.parsePayRate(VALID_PAY_RATE));
    }

    @Test
    public void parsePayRate_validValueWithWhitespace_returnsTrimmedPayRate() throws Exception {
        String payRateWithWhitespace = WHITESPACE + VALID_PAY_RATE + WHITESPACE;
        PayRate expectedPayRate = new PayRate(VALID_PAY_RATE);
        assertEquals(expectedPayRate, ParserUtil.parsePayRate(payRateWithWhitespace));
    }

    @Test
    public void parseGroup_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGroup(null));
    }

    @Test
    public void parseGroup_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGroup(INVALID_GROUP));
    }

    @Test
    public void parseGroup_validValueWithoutWhitespace_returnsGroup() throws Exception {
        Group expectedGroup = new Group(VALID_GROUP_1);
        assertEquals(expectedGroup, ParserUtil.parseGroup(VALID_GROUP_1));
    }

    @Test
    public void parseGroup_validValueWithWhitespace_returnsTrimmedGroup() throws Exception {
        String groupWithWhitespace = WHITESPACE + VALID_GROUP_1 + WHITESPACE;
        Group expectedGroup = new Group(VALID_GROUP_1);
        assertEquals(expectedGroup, ParserUtil.parseGroup(groupWithWhitespace));
    }

    @Test
    public void parseGroups_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGroups(null));
    }

    @Test
    public void parseGroups_collectionWithInvalidGroups_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGroups(Arrays.asList(VALID_GROUP_1, INVALID_GROUP)));
    }

    @Test
    public void parseGroups_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseGroups(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseGroups_collectionWithValidGroups_returnsGroupSet() throws Exception {
        Set<Group> actualGroupSet = ParserUtil.parseGroups(Arrays.asList(VALID_GROUP_1, VALID_GROUP_2));
        Set<Group> expectedGroupSet = new HashSet<Group>(Arrays.asList(new Group(VALID_GROUP_1), new Group(VALID_GROUP_2)));

        assertEquals(expectedGroupSet, actualGroupSet);
    }
}
