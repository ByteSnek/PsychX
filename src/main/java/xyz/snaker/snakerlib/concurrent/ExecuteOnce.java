package xyz.snaker.snakerlib.concurrent;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by SnakerBone on 16/08/2023
 **/
public class ExecuteOnce
{
    private final AtomicBoolean value = new AtomicBoolean();

    public boolean execute()
    {
        return value.getAndSet(true);
    }

    public boolean reset()
    {
        return !value.getAndSet(false);
    }
}
