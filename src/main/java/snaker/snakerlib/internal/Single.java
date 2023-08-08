package snaker.snakerlib.internal;

import snaker.snakerlib.utility.SketchyStuff;

/**
 * Created by SnakerBone on 7/08/2023
 **/
public class Single<V>
{
    private V value;

    public void set(V value)
    {
        if (value == null) {
            throw new NullPointerException();
        }

        this.value = value;
    }

    public V get()
    {
        if (value == null) {
            throw new NullPointerException("Value not set");
        }

        return value;
    }

    public void forget()
    {
        this.value = SketchyStuff.empty();
    }
}
