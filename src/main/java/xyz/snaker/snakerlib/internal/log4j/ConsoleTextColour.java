package xyz.snaker.snakerlib.internal.log4j;

/**
 * Created by SnakerBone on 29/05/2023
 **/
public enum ConsoleTextColour
{
    /**
     * A console text colour of black
     **/
    BLACK("\u001B[30m"),

    /**
     * A console text colour of red
     **/
    RED("\u001B[31m"),

    /**
     * A console text colour of green
     **/
    GREEN("\u001B[32m"),

    /**
     * A console text colour of yellow
     **/
    YELLOW("\u001B[33m"),

    /**
     * A console text colour of blue
     **/
    BLUE("\u001B[34m"),

    /**
     * A console text colour of purple
     **/
    PURPLE("\u001B[35m"),

    /**
     * A console text colour of cyan
     **/
    CYAN("\u001B[36m"),

    /**
     * A console text colour of white
     **/
    WHITE("\u001B[0m");

    /**
     * The console text colour value
     **/
    private final String value;

    ConsoleTextColour(String value)
    {
        this.value = value;
    }

    /**
     * Gets the console text colour value
     *
     * @return The console text colour value as a string
     **/
    public String value()
    {
        return value;
    }
}