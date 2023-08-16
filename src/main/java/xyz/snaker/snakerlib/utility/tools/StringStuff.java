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
    public static String placeholder(Locale locale, int limit, boolean modid)
    {
        return modid ? SnakerLib.MOD.get() + ":" + RandomStringUtils.randomAlphanumeric(limit).toLowerCase(locale) : RandomStringUtils.randomAlphanumeric(limit).toLowerCase(locale);
    }

    public static String placeholder(Locale locale, int limit)
    {
        return placeholder(locale, limit, false);
    }

    public static String placeholder(Locale locale, boolean modid)
    {
        return placeholder(locale, 8, modid);
    }

    public static String placeholder(Locale locale)
    {
        return placeholder(locale, false);
    }

    public static String placeholder()
    {
        return placeholder(Locale.ROOT);
    }

    public static String placeholderWithId()
    {
        return placeholder(Locale.ROOT, true);
    }

    public static String i18nt(String text)
    {
        if (!text.isEmpty()) {
            return Stream.of(text.trim().split("\\s|\\p{Pc}")).filter(word -> word.length() > 0).map(word -> word.substring(0, 1).toUpperCase() + word.substring(1)).collect(Collectors.joining(" "));
        } else {
            return text;
        }
    }

    public static String i18nf(String text)
    {
        return !text.isEmpty() ? text.replaceAll("\\s+", "_").toLowerCase() : text;
    }

    public static boolean isValidString(String string, boolean notify, boolean crash)
    {
        if (string == null || string.isEmpty()) {
            return false;
        } else {
            String regex = ".*[a-zA-Z]+.*";
            if (!string.matches(regex)) {
                if (notify) {
                    SnakerLib.LOGGER.warnf("String '%s' is not a valid string", string);
                    if (crash) {
                        throw new RuntimeException(String.format("Invalid string: %s", string));
                    }
                }
                if (!notify && crash) {
                    throw new RuntimeException(String.format("Invalid string: %s", string));
                }
            }
            return string.matches(regex);
        }
    }

    public static boolean isValidString(String string, boolean notify)
    {
        return isValidString(string, notify, false);
    }

    public static boolean isValidString(String string)
    {
        return isValidString(string, false);
    }
}
