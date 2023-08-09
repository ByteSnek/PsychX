package xyz.snaker.snakerlib.internal.log4j;

/**
 * Created by SnakerBone on 29/05/2023
 **/
public enum ConsoleMarker
{
    INFO,
    WARN,
    ERROR;

    private final String value;

    ConsoleMarker()
    {
        this.value = name().toUpperCase();
    }

    public String value()
    {
        return value;
    }
}
