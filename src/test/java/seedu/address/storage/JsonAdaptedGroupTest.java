package seedu.address.storage;

import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Name;
import seedu.address.model.group.Group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

public class JsonAdaptedGroupTest {

    private static final String INVALID_NAME = "R@chel";
    private static final List<JsonAdaptedPerson> VALID_GROUP = new ArrayList();

    @Test
    public void toModelType_validGroupDetails_returnsGroup() throws Exception {
        Group group = new Group(new Name("Name"), new ArrayList<>(Arrays.asList(BENSON)));
        JsonAdaptedGroup group_actual = new JsonAdaptedGroup(group);
        assertEquals(group, group_actual.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedGroup group =
                new JsonAdaptedGroup(INVALID_NAME, VALID_GROUP);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, group::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedGroup group =
                new JsonAdaptedGroup(null, VALID_GROUP);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, group::toModelType);
    }
}
