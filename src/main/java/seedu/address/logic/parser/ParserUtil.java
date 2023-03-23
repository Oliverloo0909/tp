package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Name;
import seedu.address.model.group.Group;
import seedu.address.model.person.Address;
import seedu.address.model.person.PayRate;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Session;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses the modify field to a boolean
     * @param isAdd
     * @return
     * @throws ParseException
     */
    public static boolean parseIsAdd(String isAdd) throws ParseException {
        String trimmedIsAdd = isAdd.trim();
        if (!trimmedIsAdd.equalsIgnoreCase("add") && !trimmedIsAdd.equalsIgnoreCase("remove")) {
            throw new ParseException("input either m/add or m/remove");
        }
        return trimmedIsAdd.equalsIgnoreCase("add");
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String group} into a {@code group}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code group} is invalid.
     */
    public static Group parseGroup(String group) throws ParseException {
        requireNonNull(group);
        String trimmedGroup = group.trim();
        if (!Name.isValidName(trimmedGroup)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Group(new Name(trimmedGroup));
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String session} into an {@code Session}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code session} is invalid.
     */
    public static Session parseSession(String session) throws ParseException {
        requireNonNull(session);
        String trimmedSession = session.trim();
        String[] dateTime = trimmedSession.split(" to ");
        if (dateTime.length != 2) {
            throw new ParseException(Session.MESSAGE_CONSTRAINTS);
        }
        String[] startDateAndTime = dateTime[0].split(" ");
        String[] endDateAndTime = dateTime[1].split(" ");
        if (startDateAndTime.length != 2 || endDateAndTime.length != 2
                || !Session.isValidDateFormat(startDateAndTime[0].trim())
                || !Session.isValidDateFormat(endDateAndTime[0].trim())
                || !Session.isValidTimeFormat(startDateAndTime[1].trim())
                || !Session.isValidTimeFormat(endDateAndTime[1].trim())) {
            throw new ParseException(Session.MESSAGE_CONSTRAINTS);
        }
        return new Session(startDateAndTime[0].trim() + " " + startDateAndTime[1].trim(),
                endDateAndTime[0].trim() + " " + endDateAndTime[1].trim());
    }


    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static PayRate parsePayRate(String pay) throws ParseException {
        requireNonNull(pay);
        String trimmedPay = pay.trim();
        if (!PayRate.isValidPayRate(trimmedPay)) {
            throw new ParseException(PayRate.MESSAGE_CONSTRAINTS);
        }
        return new PayRate(trimmedPay);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
