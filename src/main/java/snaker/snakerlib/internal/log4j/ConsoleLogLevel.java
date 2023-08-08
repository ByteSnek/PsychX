package snaker.snakerlib.internal.log4j;

import com.mojang.datafixers.util.Pair;

public enum ConsoleLogLevel
{
    INFO(ConsoleMarker.INFO, ConsoleTextColour.WHITE),
    WARN(ConsoleMarker.WARN, ConsoleTextColour.YELLOW),
    ERROR(ConsoleMarker.ERROR, ConsoleTextColour.RED);

    private final Pair<ConsoleMarker, ConsoleTextColour> pair;

    ConsoleLogLevel(ConsoleMarker type, ConsoleTextColour code)
    {
        this.pair = new Pair<>(type, code);
    }

    public final ConsoleMarker marker()
    {
        return pair.getFirst();
    }

    public final ConsoleTextColour colour()
    {
        return pair.getSecond();
    }

    public final String markerValue()
    {
        return marker().value();
    }

    public final String colourValue()
    {
        return colour().value();
    }

    public final Pair<ConsoleMarker, ConsoleTextColour> both()
    {
        return pair;
    }
}
