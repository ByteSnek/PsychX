package xyz.snaker.snakerlib.concurrent;

import xyz.snaker.snakerlib.SnakerLib;

/**
 * Created by SnakerBone on 20/08/2023
 **/
public class InstanceCapture<V>
{
    private final String target;
    private V instance;
    private boolean isCaptured;

    public InstanceCapture(String target)
    {
        this.target = target;
    }

    public boolean capture(V instance)
    {
        if (isCaptured) {
            SnakerLib.LOGGER.warnf("Class '%s' has already been captured", target);
            return false;
        } else {
            if (instance == null) {
                throw new NullPointerException("Instance cannot be null");
            }
            this.instance = instance;
            this.isCaptured = true;
            return true;
        }
    }

    public V get()
    {
        if (!isCaptured && instance == null) {
            throw new RuntimeException("Instance not captured");
        } else {
            return instance;
        }
    }

    public boolean reset()
    {
        if (!isCaptured) {
            SnakerLib.LOGGER.warn("Instance not captured");
            return false;
        } else {
            instance = null;
            isCaptured = false;
            return true;
        }
    }

    @Override
    public String toString()
    {
        return target;
    }
}
