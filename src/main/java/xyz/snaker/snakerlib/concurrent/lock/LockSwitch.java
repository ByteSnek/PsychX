package xyz.snaker.snakerlib.concurrent.lock;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;

/**
 * Created by SnakerBone on 16/08/2023
 * <p>
 * A simple instance lock
 **/
public class LockSwitch
{
    /**
     * Checks whether the current instance is locked
     **/
    private boolean locked;

    /**
     * A stopwatch instance for timing
     **/
    private StopWatch watch;

    public LockSwitch()
    {
        watch = new StopWatch();
    }

    /**
     * Locks the current instance
     *
     * @throws RuntimeException If the current instance is already locked
     **/
    public void lock()
    {
        if (locked) {
            watch.stop();
            throw new RuntimeException(String.format("Switch was already locked %s minutes ago", watch.getTime(TimeUnit.MINUTES)));
        }

        locked = true;
        watch.start();
    }

    /**
     * Unlocks the current instance
     **/
    public void unlock()
    {
        if (!locked) {
            return;
        }

        locked = false;
        watch.reset();
    }
}
