package xyz.snaker.snakerlib.utility.random;

/**
 * Created by SnakerBone on 25/08/2023
 **/
public class Frequency
{
    private int value;

    private Frequency(int value)
    {
        this.value = value;
    }

    public static Frequency of(int value)
    {
        return new Frequency(value);
    }

    public int getValue()
    {
        return value;
    }
}
