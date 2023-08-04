package snaker.snakerlib.utility;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import snaker.snakerlib.internal.StupidArgumentException;

public class SketchyStuff
{
    @Nullable
    @SuppressWarnings("unchecked")
    public static <Anything> Anything tryCast(@Nullable Object object)
    {
        return (Anything) object;
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
