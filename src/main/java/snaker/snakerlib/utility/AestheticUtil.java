package snaker.snakerlib.utility;

import org.apache.logging.log4j.util.TriConsumer;
import snaker.snakerlib.internal.QuadConsumer;

import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by SnakerBone on 4/06/2023
 * <p>
 * Utilities to help keep code somewhat tidy
 **/
public class AestheticUtil
{
    // @NoFormat

    private static final Runnable EMPTY_RUNNABLE = () -> {};
    private static final Consumer<?> EMPTY_WC_CONSUMER = e -> {};
    private static final BiConsumer<?, ?> EMPTY_WC_BI_CONSUMER = (t, v) -> {};
    private static final TriConsumer<?, ?, ?> EMPTY_WC_TRI_CONSUMER = (k, v, s) -> {};
    private static final QuadConsumer<?, ?, ?, ?> EMPTY_WC_QUAD_CONSUMER = (u, d, t, q) -> {};
    private static final Callable<?> NULL_WC_CALLABLE = () -> null;
    private static final Supplier<?> NULL_WC_SUPPLIER = () -> null;

    private static final Consumer<Object> EMPTY_CONSUMER = e -> {};
    private static final BiConsumer<Object, Object> EMPTY_BI_CONSUMER = (t, v) -> {};
    private static final TriConsumer<Object, Object, Object> EMPTY_TRI_CONSUMER = (k, v, s) -> {};
    private static final QuadConsumer<Object, Object, Object, Object> EMPTY_QUAD_CONSUMER = (u, d, t, q) -> {};
    private static final Callable<Object> NULL_CALLABLE = () -> null;
    private static final Supplier<Object> NULL_SUPPLIER = () -> null;

    // @YesFormat

    public static Runnable emptyRunnable()
    {
        return EMPTY_RUNNABLE;
    }

    public static Consumer<?> emptyWildcardConsumer()
    {
        return EMPTY_WC_CONSUMER;
    }

    public static BiConsumer<?, ?> emptyWildcardBiConsumer()
    {
        return EMPTY_WC_BI_CONSUMER;
    }

    public static TriConsumer<?, ?, ?> emptyWildcardTriConsumer()
    {
        return EMPTY_WC_TRI_CONSUMER;
    }

    public static QuadConsumer<?, ?, ?, ?> emptyWildcardQuadConsumer()
    {
        return EMPTY_WC_QUAD_CONSUMER;
    }

    public static Callable<?> nullWildcardCallable()
    {
        return NULL_WC_CALLABLE;
    }

    public static Supplier<?> nullWildcardSupplier()
    {
        return NULL_WC_SUPPLIER;
    }

    public static Consumer<Object> emptyConsumer()
    {
        return EMPTY_CONSUMER;
    }

    public static BiConsumer<Object, Object> emptyBiConsumer()
    {
        return EMPTY_BI_CONSUMER;
    }

    public static TriConsumer<Object, Object, Object> emptyTriConsumer()
    {
        return EMPTY_TRI_CONSUMER;
    }

    public static QuadConsumer<Object, Object, Object, Object> emptyQuadConsumer()
    {
        return EMPTY_QUAD_CONSUMER;
    }

    public static Callable<Object> nullCallable()
    {
        return NULL_CALLABLE;
    }

    public static Supplier<Object> nullSupplier()
    {
        return NULL_SUPPLIER;
    }
}
