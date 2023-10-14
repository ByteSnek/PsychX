package xyz.snaker.tq.utility;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by SnakerBone on 14/10/2023
 **/
public class Once
{
    private final AtomicBoolean bool = new AtomicBoolean();

    public boolean once()
    {
        return bool.getAndSet(true);
    }

    public void reset()
    {
        bool.getAndSet(false);
    }
}
