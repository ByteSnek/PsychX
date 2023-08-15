package xyz.snaker.snakerlib.utility.tools;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import xyz.snaker.snakerlib.SnakerLib;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.MemoryUtil;

public class UnsafeStuff
{
    /**
     * Returns an object that can be assigned to anything that is not a primitive type
     *
     * @return An empty versatile object
     **/
    public static <V> V versatileObject()
    {
        return cast(new Object[0]);
    }

    /**
     * Attempts to cast anything to anything
     *
     * @param object The object to cast
     * @return The result of the cast
     * @throws ClassCastException if the value could not be cast
     **/
    @Nullable
    @SuppressWarnings("unchecked")
    public static <V> V cast(@Nullable Object object)
    {
        V value;
        try {
            value = (V) object;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.getMessage());
        }
        return value;
    }

    /**
     * Attempts to create a new instance of any object
     *
     * @param clazz The class to instantiate
     * @return The class instance
     * @throws RuntimeException     if the class could not be instantiated
     * @throws NullPointerException if the class is null
     **/
    @NotNull
    public static <T> T instantiate(Class<T> clazz)
    {
        if (clazz == null) {
            throw new NullPointerException();
        }
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Attempts to get a fields name as a string
     *
     * @param obj       The field to find
     * @param parent    The class where the field is present
     * @param lowercase Should the result be lowercase
     * @return The field name or null if the class has no fields or accessible fields
     * @throws RuntimeException     if the field is not present in the parent class, if the parent is not valid or if the field cannot be accessed
     * @throws NullPointerException if the field is null
     **/
    public static String getFieldName(Object obj, Class<?> parent, boolean lowercase)
    {
        Field[] fields = parent.getFields();
        if (obj == null) {
            throw new NullPointerException("Field cannot be null");
        }
        if (obj.getClass() != parent) {
            throw new RuntimeException("Field must be apart of the parent class");
        }
        if (Arrays.stream(fields).toList().isEmpty()) {
            SnakerLib.LOGGER.warnf("No recognizable fields found in class '%s'", parent.getSimpleName());
            return null;
        }
        for (Field field : fields) {
            Object object;
            try {
                object = field.get(parent);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (obj.equals(object)) {
                return lowercase ? field.getName().toLowerCase() : field.getName();
            }
        }
        return null;
    }

    /**
     * Attempts to get a fields name as a string
     *
     * @param obj    The field to find
     * @param parent The class where the field is present
     * @return The field name or null if the class has no fields or accessible fields
     * @throws RuntimeException     if the field is not present in the parent class, if the parent is not valid or if the field cannot be accessed
     * @throws NullPointerException if the field is null
     **/
    public static String getFieldName(Object obj, Class<?> parent)
    {
        return getFieldName(obj, parent, true);
    }

    /**
     * Attempts to get a fields name as a string
     *
     * @param obj       The field to find
     * @param lowercase Should the result be lowercase
     * @return The field name or null if the class has no fields or accessible fields
     * @throws RuntimeException     if the field is not present in the parent class, if the parent is not valid or if the field cannot be accessed
     * @throws NullPointerException if the field is null
     **/
    public static String getFieldName(Object obj, boolean lowercase)
    {
        Class<?> parent = SnakerLib.STACK_WALKER.getCallerClass();
        Field[] fields = parent.getFields();
        if (obj == null) {
            throw new NullPointerException("Field cannot be null");
        }
        if (obj.getClass() != parent) {
            SnakerLib.LOGGER.errorf("Hint: Caller class '%s' is not apart of the parent class", parent.getSimpleName());
            throw new RuntimeException("Field must be apart of the parent class");
        }
        if (Arrays.stream(fields).toList().isEmpty()) {
            SnakerLib.LOGGER.warnf("No recognizable fields found in class '%s'", parent.getSimpleName());
            return null;
        }
        for (Field field : fields) {
            Object object;
            try {
                object = field.get(parent);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (obj.equals(object)) {
                return lowercase ? field.getName().toLowerCase() : field.getName();
            }
        }
        return null;
    }

    /**
     * Attempts to get a fields name as a string
     *
     * @param obj The field to find
     * @return The field name or null if the class has no fields or accessible fields
     * @throws RuntimeException     if the field is not present in the parent class, if the parent is not valid or if the field cannot be accessed
     * @throws NullPointerException if the field is null
     **/
    public static String getFieldName(Object obj)
    {
        return getFieldName(obj, true);
    }

    /**
     * Force crashes the JVM by starving its memory
     * <p>
     * Extremely dangerous, inefficient and nothing is saved or backed up during the process
     * <p>
     * <b>There should be no reason at all to be implementing this method outside of the developer environment</b>
     *
     * @param crashReason The reason for crashing so it can be logged if implemented
     **/
    public static void forceCrashJVM(String crashReason)
    {
        String className = SnakerLib.STACK_WALKER.getCallerClass().toString();
        String threadName = Thread.currentThread().getName();
        String regex = ".*[a-zA-Z]+.*";
        String lineBreak = "\n";

        StringBuilder reportBuilder = new StringBuilder("-+-");

        reportBuilder.append("-+-".repeat(36));

        SnakerLib.LOGGER.warnf("JVM crash detected on %s", threadName);
        SnakerLib.LOGGER.errorf("%s %24s %s", lineBreak, reportBuilder, lineBreak);
        SnakerLib.LOGGER.errorf("The JVM was forcefully crashed by %s %s", className, lineBreak);

        if (!className.contains("SnakerLib")) {
            SnakerLib.LOGGER.errorf("The JVM was not crashed by SnakerLib. Please go report it to %s %s", className, lineBreak);
        }

        if (!crashReason.matches(regex)) {
            SnakerLib.LOGGER.errorf("The reason for the crash was not specified %s", lineBreak);
        } else {
            SnakerLib.LOGGER.errorf("%s %s", crashReason, lineBreak);
        }

        SnakerLib.LOGGER.errorf("%24s", reportBuilder);
        MemoryUtil.memSet(0, 0, 1);
    }
}
