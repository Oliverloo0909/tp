package seedu.address.model.group;

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
    private UniquePersonList group;

    public Group(Name name) {
        this.name = name;
        this.group = new UniquePersonList();
    }

    public Group(Name name, ArrayList<Person> persons) {
        this.name = name;
        this.group = new UniquePersonList();
        for(Person person: persons) {
            group.add(person);
        }
    }

    public Group(Name name, UniquePersonList persons) {
        this.name = name;
        this.group = new UniquePersonList();
        for(int i = 0; i < persons.size(); i++) {
            group.add(persons.get(i));
        }
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
        if(group == null) {
            return 0;
        }
        return group.size();
    }

    @Override
    public boolean equals(Object other) {

        if (other == this) {
            return true; //short circuit if same object
        }


        if (!(other instanceof Group
                && name.equals(((Group) other).getName()))) {
            return false;
        }

        if (other == null || !(other instanceof Group) ||
                (other instanceof Group
                        && (size() != ((Group) other).size() ||
                        !name.equals(((Group)other).name)))
                ) {
            return false;
        }


        for (int i = 0; i < size(); i++) {
            System.out.println(this.get(i));
            System.out.println(((Group) other).get(i));
            if (!this.get(i).equals(((Group) other).get(i))) {
                return false;
            }
        }

        return true;
    }

    public ObservableList<Person> getPersons() {
        return group.asUnmodifiableObservableList();
    }

    public Group copy() {
        return new Group(this.name, this.group);
    }
}
