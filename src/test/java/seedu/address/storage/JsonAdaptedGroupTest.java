package seedu.address.storage;

import org.junit.jupiter.api.Test;
import seedu.address.model.Name;
import seedu.address.model.group.Group;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.BENSON;

public class JsonAdaptedGroupTest {
    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        Group group = new Group(new Name("Name"), new ArrayList<>(Arrays.asList(BENSON)));
        JsonAdaptedGroup group_actual = new JsonAdaptedGroup(group);
        assertEquals(group, group_actual.toModelType());
    }
}
