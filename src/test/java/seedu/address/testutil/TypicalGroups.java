package seedu.address.testutil;

import seedu.address.model.Name;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.Arrays;

import static seedu.address.testutil.TypicalPersons.ALICE;

public class TypicalGroups {
    public static final Name HALL = new Name("hall");
    public static final Name VARSITY = new Name("varsity");
    public static final Group GROUP_VARSITY = new Group(VARSITY);
    public static final Group GROUP_WITH_ALICE = new Group(HALL, new ArrayList<Person>(Arrays.asList(ALICE)));
    public static final Group GROUP_HALL = new Group(HALL);
}
