package seedu.address.model.group;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

import java.util.ArrayList;

/**
 * Group list that contains a group of people (people in group are unique)
 */
public class Group {

    private Name name;
    private UniquePersonList group = new UniquePersonList();

    public Group(Name name) {
        this.name = name;
    }

    public Group(Name name, ArrayList<Person> persons) {
        for(Person person: persons) {
            group.add(person);
        }
        this.name = name;
    }

    /**
     * Adds a person to the group.
     * The group must not already exist in the list.
     */
    public void add(Person toAdd) {
        //add to unique person list
        group.add(toAdd);
    }

    /**
     * Gets name of group
     * @return
     */
    public Name getName() {
        return this.name;
    }

    /**
     * Removes the equivalent person the group.
     * The person must exist in the group.
     * @param toRemove
     */
    public void remove(Person toRemove) {
        group.remove(toRemove);
    }

    @Override
    public String toString() {
        return "(" + name.toString() + ")";
    }

    /**
     * returns person at specified index.
     * @return
     */
    public Person get(int index) {
        return group.get(index);
    }

    /**
     * returns number of people in group
     * @return
     */
    public int size() {
        return group.size();
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof Group
                    && name.equals((Name) ((Group) other).getName()));
    }

    public ObservableList<Person> getPersons() {
        return group.asUnmodifiableObservableList();
    }
}
