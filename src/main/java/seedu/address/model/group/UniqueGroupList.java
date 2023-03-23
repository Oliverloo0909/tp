package seedu.address.model.group;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * A list of groups that enforces uniqueness between its name and does not allow nulls.
 * A group is considered unique by comparing using {@code Group#equals(Group)}.
 *
 * Supports a minimal set of list operations.
 *
 */
public class UniqueGroupList implements Iterable<Group>{
    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */

    private final ObservableList<Group> internalList = FXCollections.observableArrayList();
    private final ObservableList<Group> internalUnmodifiableList =
                FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent group as the given argument.
     */
    public boolean contains(Group toCheck) {
            requireNonNull(toCheck);
            return internalList.stream().anyMatch(toCheck::equals);
        }

    public ObservableList<Group> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Adds a group to the list.
     * The person must not already exist in the list.
     */
    public void add(Group toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateGroupException();
        }
        internalList.add(toAdd);
    }
    public void set(Group target, Group editedGroup) {
        requireAllNonNull(target, editedGroup);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new GroupNotFoundException();
        }

        if (!target.equals(editedGroup) && contains(editedGroup)) {
            throw new DuplicateGroupException();
        }

        internalList.set(index, editedGroup);
    }

    public Group get(Group target) {
        requireNonNull(target);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new GroupNotFoundException();
        }
        return internalList.get(index).copy();
    }


    /**
     * Removes the equivalent group from the list.
     * The group must exist in the list.
     */
    public void remove(Group toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new GroupNotFoundException();
        }
    }

    @Override
    public Iterator<Group> iterator() {
        return internalList.iterator();
    }

    @Override
    public String toString() {
        String group1 = internalList.get(0).getName().toString();
        String group2 = internalList.get(1).getName().toString();
        String group3 = internalList.get(2).getName().toString();
        return "[" + group1 + ", "
                + group2 + ", "
                + group3 + "... ]";
    }


    public void setGroups(List<Group> groups) {
        requireAllNonNull(groups);
        if (!groupsAreUnique(groups)) {
            throw new DuplicateGroupException();
        }

        internalList.setAll(groups);
    }

    /**
     * Returns true if {@code groups} contains only unique groups.
     */
    private boolean groupsAreUnique(List<Group> groups) {
        for (int i = 0; i < groups.size() - 1; i++) {
            for (int j = i + 1; j < groups.size(); j++) {
                if (groups.get(i).equals(groups.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueGroupList // instanceof handles nulls
                && internalList.equals(((UniqueGroupList) other).internalList));
    }
}
