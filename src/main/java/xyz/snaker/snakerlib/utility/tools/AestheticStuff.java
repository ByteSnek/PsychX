package xyz.snaker.snakerlib.utility.tools;

import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import xyz.snaker.snakerlib.internal.QuadConsumer;

import org.apache.logging.log4j.util.TriConsumer;

/**
 * Created by SnakerBone on 4/06/2023
 **/
public class AestheticStuff
{
    /**
     * An empty runnable instance
     *
     * @return An empty runnable
     **/
    public static Runnable emptyRunnable()
    {
        return () -> {};
    }

    /**
     * An empty consumer instance
     *
     * @return An empty consumer
     **/
    public static Consumer<?> emptyWildcardConsumer()
    {
        return u -> {};
    }

    /**
     * An empty bi consumer instance
     *
     * @return An empty bi consumer
     **/
    public static BiConsumer<?, ?> emptyWildcardBiConsumer()
    {
        return (u, d) -> {};
    }

    /**
     * An empty tri consumer instance
     *
     * @return An empty tri consumer
     **/
    public static TriConsumer<?, ?, ?> emptyWildcardTriConsumer()
    {
        return (u, d, t) -> {};
    }

    /**
     * An empty quad consumer instance
     *
     * @return An empty quad consumer
     **/
    public static QuadConsumer<?, ?, ?, ?> emptyWildcardQuadConsumer()
    {
        return (u, d, t, q) -> {};
    }

    /**
     * A null callable instance
     *
     * @return A null callable
     **/
    public static Callable<?> nullWildcardCallable()
    {
        return () -> null;
    }

    /**
     * A null supplier instance
     *
     * @return A null supplier
     **/
    public static Supplier<?> nullWildcardSupplier()
    {
        return () -> null;
    }

    /**
     * An empty consumer instance
     *
     * @return An empty consumer
     **/
    public static Consumer<Object> emptyConsumer()
    {
        return u -> {};
    }

    /**
     * An empty bi consumer instance
     *
     * @return An empty bi consumer
     **/
    public static BiConsumer<Object, Object> emptyBiConsumer()
    {
        return (u, d) -> {};
    }

    /**
     * An empty tri consumer instance
     *
     * @return An empty tri consumer
     **/
    public static TriConsumer<Object, Object, Object> emptyTriConsumer()
    {
        return (u, d, t) -> {};
    }

    /**
     * An empty quad consumer instance
     *
     * @return An empty quad consumer
     **/
    public static QuadConsumer<Object, Object, Object, Object> emptyQuadConsumer()
    {
        return (u, d, t, q) -> {};
    }

    /**
     * A null callable instance
     *
     * @return A null callable
     **/
    public static Callable<Object> nullCallable()
    {
        return () -> null;
    }

    /**
     * A null supplier instance
     *
     * @return A null supplier
     **/
    public static Supplier<Object> nullSupplier()
    {
        return () -> null;
    }
}
