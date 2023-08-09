package xyz.snaker.snakerlib.internal;

/**
 * Created by SnakerBone on 4/06/2023
 * <p>
 * Utilities and such revolving around replacing a string with an empty literal
 *
 * @see String#replace(char, char)
 * @see String#replaceAll(String, String)
 **/
public class StringNuker
{
    private final String nothing = "";
    private String string;

    public StringNuker(String string)
    {
        this.string = string;
    }

    public StringNuker()
    {
        this.string = nothing;
    }

    public String nuke(Boolean nullify)
    {
        if (nullify) {
            string = null;
            return null;
        } else {
            string = nothing;
            return string;
        }
    }

    public String nuke()
    {
        return nuke(false);
    }

    public String nukeIf(Boolean condition, Boolean nullify)
    {
        if (condition) {
            if (nullify) {
                string = null;
                return null;
            } else {
                string = nothing;
                return string;
            }
        }
        return string;
    }

    public String nukeIf(Boolean condition)
    {
        return nukeIf(condition, false);
    }

    public <Task extends Runnable> String nukeElseDo(Boolean condition, Task task, Boolean nullify)
    {
        if (condition) {
            if (nullify) {
                string = null;
                return null;
            } else {
                string = nothing;
                return string;
            }
        } else {
            task.run();
        }
        return string;
    }

    public <Task extends Runnable> String nukeElseDo(Boolean condition, Task task)
    {
        return nukeElseDo(condition, task, false);
    }

    public String replaceAllAndDestroy(String... regexs)
    {
        for (String regex : regexs) {
            string = string.replaceAll(regex, nothing);
        }
        return string;
    }

    public String replaceAllAndDestroy(String regex)
    {
        string = string.replaceAll(regex, nothing);
        return string;
    }

    public String replaceAllAndDestroyIf(Boolean condition, String... regexs)
    {
        if (condition) {
            for (String regex : regexs) {
                string = string.replaceAll(regex, nothing);
            }
        }
        return string;
    }

    public String replaceAllAndDestroyIf(Boolean condition, String regex)
    {
        if (condition) {
            string = string.replaceAll(regex, nothing);
        }
        return string;
    }

    public <Assignee extends String> String replaceAllAndDestroyElseAssign(Boolean condition, Assignee assignee, String... regexs)
    {
        if (condition) {
            for (String regex : regexs) {
                string = string.replaceAll(regex, nothing);
            }
        } else {
            string = assignee;
            return string;
        }
        return string;
    }

    public <Assignee extends String> String replaceAllAndDestroyElseAssign(Boolean condition, Assignee assignee, String regex)
    {
        if (condition) {
            string = string.replaceAll(regex, nothing);
        } else {
            string = assignee;
            return string;
        }
        return string;
    }

    public String replaceAndDestroy(String... targets)
    {
        for (String target : targets) {
            string = string.replace(target, nothing);
        }
        return string;
    }

    public String replaceAndDestroy(String target)
    {
        string = string.replace(target, nothing);
        return string;
    }

    public String replaceAndDestroyIf(Boolean condition, String... targets)
    {
        if (condition) {
            for (String target : targets) {
                string = string.replace(target, nothing);
            }
        }
        return string;
    }

    public String replaceAndDestroyIf(Boolean condition, String target)
    {
        if (condition) {
            string = string.replace(target, nothing);
        }
        return string;
    }

    public <Assignee extends String> String replaceAndDestroyElseAssign(Boolean condition, Assignee assignee, String... targets)
    {
        if (condition) {
            for (String target : targets) {
                string = string.replace(target, nothing);
            }
        } else {
            string = assignee;
            return string;
        }
        return string;
    }

    public <Assignee extends String> String replaceAndDestroyElseAssign(Boolean condition, Assignee assignee, String target)
    {
        if (condition) {
            string = string.replace(target, nothing);
        } else {
            string = assignee;
            return string;
        }
        return string;
    }

    public String result()
    {
        return string;
    }
}