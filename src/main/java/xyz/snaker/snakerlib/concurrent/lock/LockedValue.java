package xyz.snaker.snakerlib.concurrent.lock;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.utility.tools.UnsafeStuff;

import org.apache.commons.lang3.time.StopWatch;

/**
 * Created by SnakerBone on 7/08/2023
 * <p>
 * A simple locked value
 *
 * @param <V> The value to hold
 **/
public class LockedValue<V>
{
    /**
     * The instance of the element
     **/
    private V value;

    /**
     * Checks whether the value is locked
     **/
    private boolean locked;

    /**
     * A stopwatch instance for timing
     **/
    private StopWatch watch;

    public LockedValue()
    {
        watch = new StopWatch();
    }

    /**
     * Sets the current value and locks it
     *
     * @param value The value to set
     * @return True if the current value was set and locked
     * @throws NullPointerException If the specified value is null
     **/
    public boolean set(V value)
    {
        if (locked) {
            SnakerLib.LOGGER.warn("Value already locked");
            return false;
        }

        if (value == null) {
            throw new NullPointerException("Value cannot be null");
        }

        this.value = value;
        this.lock();
        return true;
    }

    /**
     * Gets the current value
     *
     * @return The current value
     * @throws RuntimeException If the current value is not present
     **/
    public V get()
    {
        if (value == null && !locked) {
            throw new RuntimeException("Value not set");
        }

        return value;
    }

    /**
     * Locks the value
     *
     * @return True if the value was locked
     **/
    public boolean lock()
    {
        if (locked) {
            SnakerLib.LOGGER.warn("Value already locked");
            return false;
        }

        watch.start();
        locked = true;
        return true;
    }

    /**
     * Unlocks the current value
     *
     * @return True if the current value was unlocked
     **/
    public boolean unlock()
    {
        if (!locked) {
            SnakerLib.LOGGER.warn("Value not locked");
            return false;
        }

        watch.stop();
        locked = false;
        return true;
    }

    /**
     * Resets the current value
     *
     * @return True if the current value was reset
     **/
    public boolean reset()
    {
        value = UnsafeStuff.versatileObject();
        watch.reset();
        return unlock();
    }

    @Override
    public String toString()
    {
        return value.toString();
    }
}
