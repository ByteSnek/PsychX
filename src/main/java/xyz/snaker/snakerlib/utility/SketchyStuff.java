package xyz.snaker.snakerlib.utility;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.internal.StupidArgumentException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SketchyStuff
{
    /**
     * Returns an object that can be assigned to anything that is not a primitive type
     **/
    public static <V> V versatileObject()
    {
        return cast(new Object[0]);
    }

    /**
     * Attempts to cast anything to anything
     **/
    @Nullable
    @SuppressWarnings("unchecked")
    public static <V> V cast(@Nullable Object object)
    {
        V value = null;

        try {
            value = (V) object;
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            String className = object.getClass().getSimpleName();
            SnakerLib.LOGGER.errorf("Could not cast value to %s: %s", className, errorMessage);
        }

        return value;
    }

    /**
     * Attempts to create a new instance of any object
     **/
    @NotNull
    public static <T> T instantiate(Class<T> clazz)
    {
        if (clazz == null) {
            throw new StupidArgumentException();
        }

        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
