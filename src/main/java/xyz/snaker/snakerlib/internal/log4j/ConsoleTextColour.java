package xyz.snaker.snakerlib.internal.log4j;

/**
 * Created by SnakerBone on 29/05/2023
 **/
public enum ConsoleTextColour
{
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[0m");

    private final String value;

    ConsoleTextColour(String value)
    {
        this.value = value;
    }

    public String value()
    {
        return value;
    }
}