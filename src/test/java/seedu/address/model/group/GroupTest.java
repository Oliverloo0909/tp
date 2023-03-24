package seedu.address.model.group;

import org.junit.jupiter.api.Test;
import seedu.address.model.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;

public class GroupTest {
    Name hall = new Name("hall");
    Name varsity = new Name("varsity");
    Group groupVarsity = new Group(varsity);
    Group groupHallWithAlice = new Group(hall, new ArrayList<Person>(Arrays.asList(ALICE)));
    Group groupHall = new Group(hall);

    @Test
    public void constructors() {
        Group groupArrayListConstructor = new Group(hall, new ArrayList<>());
        Group groupUniquePersonsListCon = new Group(hall, new UniquePersonList());
        //single Name input constructore == name & arraylist input
        assertEquals(groupHall, groupArrayListConstructor);

        //singleName and UniquePeronsList
        assertEquals(groupHall, groupUniquePersonsListCon);
    }

    @Test
    public void equals() {
        Group groupHallCopy = new Group(new Name("hall"));

        //same values
        assertTrue(groupHall.equals(groupHallCopy));

        //same object
        assertTrue(groupHall.equals(groupHall));

        //null -> False
        assertFalse(groupHall.equals(null));

        //different type -> returns false
        assertFalse(groupHall.equals(ALICE));

        //different groups -> returns false
        assertFalse(groupHall.equals(new Group(varsity)));
    }

    @Test
    public void size() {
        assertTrue(groupHall.size() == 0);
        assertTrue(groupHallWithAlice.size() == 1);
    }

    @Test
    public void get() {
        assertTrue(groupHallWithAlice.get(0).equals(ALICE));
    }

    @Test
    public void toStringSuccess() {
        assertEquals(groupHall.toString(), "(hall)");
    }

    @Test
    public void removeSuccess() {
        groupHallWithAlice.remove(ALICE);
        assertEquals(groupHallWithAlice, groupHall);
    }

    @Test
    public void addSuccess() {
        groupHall.add(ALICE);
        assertEquals(groupHallWithAlice, groupHall);
    }

}
