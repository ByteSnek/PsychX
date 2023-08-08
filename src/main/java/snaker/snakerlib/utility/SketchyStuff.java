package snaker.snakerlib.utility;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import snaker.snakerlib.SnakerLib;
import snaker.snakerlib.internal.StupidArgumentException;

public class SketchyStuff
{
    public static <V> V empty()
    {
        return cast(new Object[0]);
    }

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
