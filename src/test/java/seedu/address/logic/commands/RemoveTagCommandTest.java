package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.PayRate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class RemoveTagCommandTest {
    private static final Index VALID_INDEX = Index.fromOneBased(1);
    private static final Tag VALID_TAG = new Tag("valid");
    private static final Tag INVALID_TAG = new Tag("invalid");

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveTagCommand(null, Collections.singleton(VALID_TAG)));
    }

    @Test
    public void constructor_nullTags_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveTagCommand(VALID_INDEX, null));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Model model = new ModelManager();
        RemoveTagCommand command = new RemoveTagCommand(Index.fromOneBased(1), Collections.singleton(VALID_TAG));
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_tagNotFound_throwsCommandException() {
        Person person = new Person(new Name("Name"), new Phone("123"),
                new Address("Address"), new PayRate("20"), new HashSet<>());
        Model model = new ModelManager();
        model.addPerson(person);
        RemoveTagCommand command = new RemoveTagCommand(VALID_INDEX, Collections.singleton(INVALID_TAG));
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_validCommand_success() throws CommandException {
        Set<Tag> tags = new HashSet<>();
        tags.add(VALID_TAG);
        Person person = new Person(new Name("Name"), new Phone("123"), new Address("Address"), new PayRate("20"), tags);
        Model model = new ModelManager();
        model.addPerson(person);
        RemoveTagCommand command = new RemoveTagCommand(VALID_INDEX, tags);
        CommandResult commandResult = command.execute(model);
        assertEquals(String.format(RemoveTagCommand.REMOVE_TAG_PERSON_SUCCESS,
                tags, person.getName()), commandResult.getFeedbackToUser());
    }
}

