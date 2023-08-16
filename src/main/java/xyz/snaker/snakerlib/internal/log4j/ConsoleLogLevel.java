package xyz.snaker.snakerlib.internal.log4j;

import com.mojang.datafixers.util.Pair;

/**
 * Created by SnakerBone on 20/05/2023
 **/
public enum ConsoleLogLevel
{
    /**
     * A console log level of type information
     **/
    INFO(ConsoleMarker.INFO, ConsoleTextColour.WHITE),

    /**
     * A console log level of type warning
     **/
    WARN(ConsoleMarker.WARN, ConsoleTextColour.YELLOW),

    /**
     * A console log level of type error
     **/
    ERROR(ConsoleMarker.ERROR, ConsoleTextColour.RED);

    /**
     * A pair holding the console marker and the console text colour
     **/
    private final Pair<ConsoleMarker, ConsoleTextColour> pair;

    ConsoleLogLevel(ConsoleMarker type, ConsoleTextColour code)
    {
        this.pair = new Pair<>(type, code);
    }

    /**
     * Gets the console marker
     *
     * @return The console marker
     **/
    public final ConsoleMarker marker()
    {
        return pair.getFirst();
    }

    /**
     * Gets the console text colour
     *
     * @return The console text colour
     **/
    public final ConsoleTextColour colour()
    {
        return pair.getSecond();
    }

    /**
     * Gets the console marker value
     *
     * @return The console marker as a string
     **/
    public final String markerValue()
    {
        return marker().value();
    }

    /**
     * Gets the console text colour value
     *
     * @return The console text colour value as a string
     **/
    public final String colourValue()
    {
        return colour().value();
    }

    /**
     * Gets the pair holding the console marker and the console text colour
     *
     * @return The pair holding the console marker and the console text colour
     **/
    public final Pair<ConsoleMarker, ConsoleTextColour> both()
    {
        return pair;
    }
}
