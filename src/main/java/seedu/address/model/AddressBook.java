package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.group.Group;
import seedu.address.model.group.UniqueGroupList;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.group.exceptions.PersonNotFoundInGroupException;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTagList tags;
    private final UniqueGroupList groups;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        tags = new UniqueTagList();
        groups = new UniqueGroupList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Alternate AddressBook constructor
     */
    public AddressBook(AddressBook toBeCloned) {
        this();
        setPersons(toBeCloned.getPersonList());
        setTags(toBeCloned.getTagList());
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    public void setGroups(List<Group> groups) {
        this.groups.setGroups(groups);
    }

    public void setTags(List<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setTags(newData.getTagList());
        setGroups(newData.getGroupList());
    }

    /**
     * Alternate resetData implementation.
     */
    public void resetData(AddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setTags(newData.getTagList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns true if a tag with the same identity as {@code tag} exists in the address book.
     */
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        return tags.contains(tag);
    }



    /**
     * Adds a tag to the address book.
     * The tag must not already exist in the address book.
     */
    public void addTag(Tag t) {
        tags.add(t);
    }

    /**
     * Adds all tags to the address book.
     * The tag must not already exist in the address book.
     */
    public void addAllTags(Set<Tag> tags) {
        for (Tag tag: tags) {
            addTag(tag);
        }
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Returns true if a tag with the same identity as {@code tag} exists in the address book.
     */
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return groups.contains(group);
    }

    /**
     * adds a group to the unique group list
     * @param group
     */
    public void addGroup(Group group) {
        groups.add(group);
    }

    /**
     * set a group in the group list to the edited group
     * @param target
     * @param editedGroup
     * @throws DuplicateGroupException
     */
    public void setGroup(Group target, Group editedGroup) {
        groups.set(target, editedGroup);
    }

    /**
     * retrieves the group specified
     * @param target
     */
    public Group getGroup(Group target) {
        return groups.get(target).copy();
    }

    /**
     * adds a person to a group
     * @param person
     * @param group
     */
    public void addPersonToGroup(Person person, Group group) {
        if (!groups.contains(group)) {
            throw new GroupNotFoundException();
        }
        if (!persons.contains(person)) {
            throw new PersonNotFoundException();
        }
        Group editedGroup = getGroup(group);
        if (editedGroup.getPersons().contains(person)) {
            throw new DuplicateGroupException();
        }
        Set<Tag> tagListToEdit = new HashSet<>();
        tagListToEdit.addAll(person.getTags());
        tagListToEdit.add(new Tag(group.getName().toString())); //add group tag
        Person modifiedPerson = new Person(
                person.getName(),
                person.getPhone(),
                person.getAddress(),
                person.getPayRate(),
                person.getSession(),
                tagListToEdit);
        this.setPerson(person, modifiedPerson);
        editedGroup.add(modifiedPerson);
    }

    /**
     * removes a person from a group
     * @param person
     * @param group
     */
    public void removePersonFromGroup(Person person, Group group) {
        if (!groups.contains(group)) {
            throw new GroupNotFoundException();
        }
        if (!persons.contains(person)) {
            throw new PersonNotFoundException();
        }
        Group editedGroup = getGroup(group);
        if (editedGroup.getPersons().size() == 0) {
            throw new RuntimeException("size is 0");
        }
        if (!editedGroup.getPersons().contains(person)) {
            throw new PersonNotFoundInGroupException();
        }
        editedGroup.remove(person);
        Set<Tag> tagListToEdit = new HashSet<>();
        tagListToEdit.addAll(person.getTags());
        tagListToEdit.remove(new Tag(group.getName().toString())); //add group tag
        Person modifiedPerson = new Person(
                person.getName(),
                person.getPhone(),
                person.getAddress(),
                person.getPayRate(),
                person.getSession(),
                tagListToEdit);
        this.setPerson(person, modifiedPerson);
    }


    /**
     * removes a group from the unique group list
     * @param group
     */
    public void removeGroup(Group group) {
        Group groupToRemove = getGroup(group);
        for (int i = 0; i < groupToRemove.size(); i++) {
            Person toRemove = groupToRemove.get(i);
            removePersonFromGroup(toRemove, groupToRemove);
        }
        groups.remove(groupToRemove);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Group> getGroupList() {
        return groups.asUnmodifiableObservableList();
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons)
                && tags.equals(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }

    public void sort() {
        persons.sort();
    }
}
