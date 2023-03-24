package seedu.address.model.group;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.model.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Group list that contains a group of people (people in group are unique).
 */
public class Group {

    private Name name;
    private UniquePersonList group;

    /**
     * group constructor
     * @param name
     */
    public Group(Name name) {
        this.name = name;
        this.group = new UniquePersonList();
    }

    /**
     * group constructor
     * @param name
     * @param persons
     */
    public Group(Name name, ArrayList<Person> persons) {
        this.name = name;
        this.group = new UniquePersonList();
        for (Person person : persons) {
            group.add(person);
        }
    }

    /**
     * group constructor
     * @param name
     * @param persons
     */
    public Group(Name name, UniquePersonList persons) {
        this.name = name;
        this.group = persons;
    }

    /**
     * Adds a person to the group.
     * The person must not already exist in the group.
     */
    public void add(Person toAdd) {
        group.add(toAdd);
    }

    /**
     * Gets the name of the group.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * Removes the specified person from the group.
     * The person must exist in the group.
     */
    public void remove(Person toRemove) {
        group.remove(toRemove);
    }

    @Override
    public String toString() {
        return "(" + name.toString() + ")";
    }

    /**
     * Returns the person at the specified index.
     */
    public Person get(int index) {
        return group.get(index);
    }

    /**
     * Returns the number of people in the group.
     */
    public int size() {
        return group.size();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;

        return otherGroup.name.equals(this.name);
    }

    /**
     * Returns an unmodifiable view of the group's person list.
     */
    public ObservableList<Person> getPersons() {
        return group.asUnmodifiableObservableList();
    }

    /**
     * Creates and returns a copy of this group.
     */
    public Group copy() {
        return new Group(this.name, this.group);
    }
}
