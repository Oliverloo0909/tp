package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGroups.GROUP_HALL;
import static seedu.address.testutil.TypicalGroups.GROUP_VARSITY;

import org.junit.jupiter.api.Test;

import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;

public class UniqueGroupListTest {
    UniqueGroupList groupList = new UniqueGroupList();


    @Test
    public void containsNullGroupFailure() {
        assertThrows(NullPointerException.class, () -> groupList.contains(null));
    }

    @Test
    public void containsGroupNotInList() {
        assertFalse(groupList.contains(GROUP_HALL));
    }

    @Test
    public void containsGroupInList() {
        groupList.add(GROUP_HALL);
        assertTrue(groupList.contains(GROUP_HALL));
    }

    @Test
    public void add_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groupList.add(null));
    }

    @Test
    public void add_duplicateGroup_throwsDuplicateException() {
        groupList.add(GROUP_HALL);
        assertThrows(DuplicateGroupException.class, () -> groupList.add(GROUP_HALL));
    }

    @Test
    public void setGroup_null_failure() {
        assertThrows(NullPointerException.class, () -> groupList.set(null, GROUP_HALL));
        assertThrows(NullPointerException.class, () -> groupList.set(GROUP_HALL, null));
    }

    @Test
    public void setGroup_targetGroupNotInFoundException() {
        assertThrows(GroupNotFoundException.class, () -> groupList.set(GROUP_HALL, GROUP_HALL));
    }

    @Test
    public void setGroup_sameGroupSuccess() {
        groupList.add(GROUP_HALL);
        groupList.set(GROUP_HALL, GROUP_HALL);
        UniqueGroupList expectedGroupList = new UniqueGroupList();
        expectedGroupList.add(GROUP_HALL);
        assertEquals(expectedGroupList, groupList);
    }

    @Test
    public void setGroup_diffGroupSuccess() {
        groupList.add(GROUP_HALL);
        groupList.set(GROUP_HALL, GROUP_VARSITY);
        UniqueGroupList expectedGroupList = new UniqueGroupList();
        expectedGroupList.add(GROUP_VARSITY);
        assertEquals(expectedGroupList, groupList);
    }

    @Test
    public void remove_GroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () -> groupList.remove(GROUP_HALL));
    }

    @Test
    public void remove_NullGroupException() {
        assertThrows(NullPointerException.class, () -> groupList.remove(null));
    }

    @Test
    public void remove_existingGroupSuccess() {
        groupList.add(GROUP_HALL);
        groupList.remove(GROUP_HALL);
        UniqueGroupList expectedList = new UniqueGroupList();
        assertEquals(expectedList, groupList);
    }
}
