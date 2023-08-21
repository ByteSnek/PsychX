package xyz.snaker.snakerlib.utility.tools;

import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import xyz.snaker.snakerlib.SnakerLib;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class StringStuff
{
    /**
     * Generates a random alpha numeric string
     *
     * @param locale The language to use
     * @param limit  The maximum amount of characters to use in the string
     * @param modid  Should the string include a modid
     * @return A random alphanumeric string
     **/
    public static String placeholder(Locale locale, int limit, boolean modid)
    {
        return modid ? SnakerLib.MOD.get() + ":" + RandomStringUtils.randomAlphanumeric(limit).toLowerCase(locale) : RandomStringUtils.randomAlphanumeric(limit).toLowerCase(locale);
    }

    /**
     * Generates a random alpha numeric string without a modid
     *
     * @param locale The language to use
     * @param limit  The maximum amount of characters to use in the string
     * @return The random alphanumeric string
     **/
    public static String placeholder(Locale locale, int limit)
    {
        return placeholder(locale, limit, false);
    }

    /**
     * Generates a random alpha numeric string with a limit of 8
     *
     * @param locale The language to use
     * @param modid  Should the string include a modid
     * @return A random alphanumeric string
     **/
    public static String placeholder(Locale locale, boolean modid)
    {
        return placeholder(locale, 8, modid);
    }

    /**
     * Generates a random alpha numeric string with a limit of 8 and without a modid
     *
     * @param locale The language to use
     * @return A random alphanumeric string
     **/
    public static String placeholder(Locale locale)
    {
        return placeholder(locale, false);
    }

    /**
     * Generates a random alpha numeric string with a limit of 8, without a modid and a root language
     *
     * @return A random alphanumeric string
     **/
    public static String placeholder()
    {
        return placeholder(Locale.ROOT);
    }

    /**
     * Generates a random alpha numeric string with a limit of 8, with a modid and a root language
     *
     * @return A random alphanumeric string
     **/
    public static String placeholderWithId()
    {
        return placeholder(Locale.ROOT, true);
    }

    /**
     * Formats an I18N string
     * <p>
     * For example these strings:
     * <pre>
     *      hello_world
     *      hello
     *      world
     * </pre>
     * Would be formatted to:
     * <pre>
     *     Hello World
     *     Hello
     *     World
     * </pre>
     *
     * @param string The string to format
     * @return The formatted string
     **/
    public static String i18nt(String string)
    {
        if (!string.isEmpty()) {
            return Stream.of(string.trim().split("\\s|\\p{Pc}")).filter(word -> word.length() > 0).map(word -> word.substring(0, 1).toUpperCase() + word.substring(1)).collect(Collectors.joining(" "));
        } else {
            return string;
        }
    }

    /**
     * Formats a string to a parseable I18N string
     * <p>
     * For example these strings:
     * <pre>
     *      Hello World
     *      Hello
     *      World
     * </pre>
     * Would be formatted to:
     * <pre>
     *     hello_world
     *     hello
     *     world
     * </pre>
     *
     * @param string The string to format
     * @return The formatted string
     **/
    public static String i18nf(String string)
    {
        return !string.isEmpty() ? string.replaceAll("\\s+", "_").toLowerCase() : string;
    }

    /**
     * Checks if a string is not null, empty and if it matches valid english regex
     *
     * @param string         The string to check
     * @param notify         Should be logged if the string is invalid
     * @param throwException Should throw exception if string is invalid
     * @return True if the string is valid
     **/
    public static boolean isValidString(String string, boolean notify, boolean throwException)
    {
        if (string == null || string.isEmpty()) {
            return false;
        } else {
            String regex = ".*[a-zA-Z]+.*";
            if (!string.matches(regex)) {
                if (notify) {
                    SnakerLib.LOGGER.warnf("String '%s' is not a valid string", string);
                    if (throwException) {
                        throw new RuntimeException(String.format("Invalid string: %s", string));
                    }
                }
                if (!notify && throwException) {
                    throw new RuntimeException(String.format("Invalid string: %s", string));
                }
            }
            return string.matches(regex);
        }
    }

    /**
     * Checks if a string is not null, empty and if it matches valid english regex
     *
     * @param string The string to check
     * @param notify Should be logged if the string is invalid
     * @return True if the string is valid
     **/
    public static boolean isValidString(String string, boolean notify)
    {
        return isValidString(string, notify, false);
    }

    /**
     * Checks if a string is not null, empty and if it matches valid english regex
     *
     * @param string The string to check
     * @return True if the string is valid
     **/
    public static boolean isValidString(String string)
    {
        return isValidString(string, false);
    }

    public static String shiftDecimal(String string, int amount, boolean strip)
    {
        if (strip) {
            string = string.substring(string.indexOf("."));
        }
        int index = string.indexOf('.');
        if (index >= 0 && index < string.length() - 1) {
            String before = string.substring(0, index);
            String after = string.substring(index + 1);
            for (int i = 0; i < amount; i++) {
                if (!after.isEmpty()) {
                    char digit = after.charAt(0);
                    before += digit;
                    after = after.substring(1);
                } else {
                    break;
                }
            }
            return before + "." + after;
        } else {
            return string;
        }
    }
}
