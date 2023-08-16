package xyz.snaker.snakerlib.internal.log4j;

/**
 * Created by SnakerBone on 29/05/2023
 **/
public enum ConsoleMarker
{
    /**
     * A console marker of type information
     **/
    INFO(),

    /**
     * A console marker of type warning
     **/
    WARN(),

    /**
     * A console marker of type error
     **/
    ERROR();

    /**
     * The console marker value
     **/
    private final String value;

    ConsoleMarker()
    {
        this.value = name().toUpperCase();
    }

    /**
     * Gets the console marker value
     *
     * @return The console marker value as a string
     **/
    public String value()
    {
        return value;
    }
}
