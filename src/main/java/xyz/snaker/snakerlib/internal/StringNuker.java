package xyz.snaker.snakerlib.internal;

/**
 * Created by SnakerBone on 4/06/2023
 * <p>
 * A simple implementation of a string deconstructor and replacer
 *
 * @see String#replace(char, char)
 * @see String#replaceAll(String, String)
 **/
public class StringNuker
{
    /**
     * An empty string
     **/
    private static final String EMPTY = "";

    /**
     * The string to modify
     **/
    private String string;

    public StringNuker(String string)
    {
        this.string = string;
    }

    public StringNuker()
    {
        this.string = EMPTY;
    }

    /**
     * Replaces the string with either an empty string or null
     *
     * @param nullify Should the string return null
     * @return The result
     **/
    public String nuke(boolean nullify)
    {
        if (nullify) {
            string = null;
            return null;
        } else {
            string = EMPTY;
            return string;
        }
    }

    /**
     * Replaces the string with an empty string
     *
     * @return The result
     **/
    public String nuke()
    {
        return nuke(false);
    }

    /**
     * Replaces the string with either an empty string or null. Only executes if the condition is true
     *
     * @param condition The condition to check
     * @param nullify   Should the string return null
     * @return The result
     **/
    public String nukeIf(boolean condition, boolean nullify)
    {
        if (condition) {
            if (nullify) {
                string = null;
                return null;
            } else {
                string = EMPTY;
                return string;
            }
        }
        return string;
    }

    /**
     * Replaces the string with an empty string. Only executes if the condition is true
     *
     * @param condition The condition to check
     * @return The result
     **/
    public String nukeIf(boolean condition)
    {
        return nukeIf(condition, false);
    }

    /**
     * Replaces the string with either an empty string or null. Only executes if the condition is true. If the condition is false another task is executed
     *
     * @param task      The task to execute if the condition is false
     * @param condition The condition to check
     * @param nullify   Should the string return null
     * @return The result
     **/
    public <R extends Runnable> String nukeElseDo(R task, boolean condition, boolean nullify)
    {
        if (condition) {
            if (nullify) {
                string = null;
                return null;
            } else {
                string = EMPTY;
                return string;
            }
        } else {
            task.run();
        }
        return string;
    }

    /**
     * Replaces the string with an empty string. Only executes if the condition is true. If the condition is false another task is executed
     *
     * @param task      The task to execute if the condition is false
     * @param condition The condition to check
     * @return The result
     **/
    public <R extends Runnable> String nukeElseDo(R task, boolean condition)
    {
        return nukeElseDo(task, condition, false);
    }

    /**
     * Replaces a string dependant on the regex's passed in
     *
     * @param regexs The regex's to replace with
     * @return The result
     **/
    public String replaceAllAndDestroy(String... regexs)
    {
        for (String regex : regexs) {
            string = string.replaceAll(regex, EMPTY);
        }
        return string;
    }

    /**
     * Replaces a string dependant on the regex passed in
     *
     * @param regex The regex to replace with
     * @return The result
     **/
    public String replaceAllAndDestroy(String regex)
    {
        string = string.replaceAll(regex, EMPTY);
        return string;
    }

    /**
     * Replaces a string dependant on the condition and the regex's passed in
     *
     * @param condition The condition to check
     * @param regexs    The regex's to replace with
     * @return The result
     **/
    public String replaceAllAndDestroyIf(boolean condition, String... regexs)
    {
        if (condition) {
            for (String regex : regexs) {
                string = string.replaceAll(regex, EMPTY);
            }
        }
        return string;
    }

    /**
     * Replaces a string dependant on the condition and the regex passed in
     *
     * @param condition The condition to check
     * @param regex     The regex to replace with
     * @return The result
     **/
    public String replaceAllAndDestroyIf(boolean condition, String regex)
    {
        if (condition) {
            string = string.replaceAll(regex, EMPTY);
        }
        return string;
    }

    /**
     * Replaces a string dependant on the condition and the regex's passed in. If the condition is false the string will be assigned to the assignee
     *
     * @param condition The condition to check
     * @param assignee  The string to assign if the condition is false
     * @param regexs    The regex's to replace with
     * @return The result
     **/
    public <S extends String> String replaceAllAndDestroyElseAssign(boolean condition, S assignee, String... regexs)
    {
        if (condition) {
            for (String regex : regexs) {
                string = string.replaceAll(regex, EMPTY);
            }
        } else {
            string = assignee;
            return string;
        }
        return string;
    }

    /**
     * Replaces a string dependant on the condition and the regex passed in. If the condition is false the string will be assigned to the assignee
     *
     * @param condition The condition to check
     * @param assignee  The string to assign if the condition is false
     * @param regex     The regex to replace with
     * @return The result
     **/
    public <S extends String> String replaceAllAndDestroyElseAssign(boolean condition, S assignee, String regex)
    {
        if (condition) {
            string = string.replaceAll(regex, EMPTY);
        } else {
            string = assignee;
            return string;
        }
        return string;
    }

    /**
     * Replaces a string dependant on the strings passed in
     *
     * @param targets The strings to find and replace
     * @return The result
     **/
    public String replaceAndDestroy(String... targets)
    {
        for (String target : targets) {
            string = string.replace(target, EMPTY);
        }
        return string;
    }

    /**
     * Replaces a string dependant on the string passed in
     *
     * @param target The string to find and replace
     * @return The result
     **/
    public String replaceAndDestroy(String target)
    {
        string = string.replace(target, EMPTY);
        return string;
    }

    /**
     * Replaces a string dependant on the condition and the strings passed in
     *
     * @param condition The condition to check
     * @param targets   The strings to find and replace
     * @return The result
     **/
    public String replaceAndDestroyIf(boolean condition, String... targets)
    {
        if (condition) {
            for (String target : targets) {
                string = string.replace(target, EMPTY);
            }
        }
        return string;
    }

    /**
     * Replaces a string dependant on the condition and the string passed in
     *
     * @param condition The condition to check
     * @param target    The string to find and replace
     * @return The result
     **/
    public String replaceAndDestroyIf(boolean condition, String target)
    {
        if (condition) {
            string = string.replace(target, EMPTY);
        }
        return string;
    }

    /**
     * Replaces a string dependant on the condition and the strings passed in. If the condition is false the string will be assigned to the assignee
     *
     * @param condition The condition to check
     * @param assignee  The string to assign if the condition is false
     * @param targets   The strings to find and replace
     * @return The result
     **/
    public <S extends String> String replaceAndDestroyElseAssign(boolean condition, S assignee, String... targets)
    {
        if (condition) {
            for (String target : targets) {
                string = string.replace(target, EMPTY);
            }
        } else {
            string = assignee;
            return string;
        }
        return string;
    }

    /**
     * Replaces a string dependant on the condition and the string passed in. If the condition is false the string will be assigned to the assignee
     *
     * @param condition The condition to check
     * @param assignee  The string to assign if the condition is false
     * @param target    The string to find and replace
     * @return The result
     **/
    public <S extends String> String replaceAndDestroyElseAssign(boolean condition, S assignee, String target)
    {
        if (condition) {
            string = string.replace(target, EMPTY);
        } else {
            string = assignee;
            return string;
        }
        return string;
    }

    /**
     * Gets the result of the string currently being modified
     **/
    public String result()
    {
        return string;
    }
}