package snaker.snakerlib.internal;

/**
 * Created by SnakerBone on 29/05/2023
 **/
public enum ColourCode
{
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[0m");

    final String code;

    ColourCode(String code)
    {
        this.code = code;
    }

    public String get()
    {
        return code;
    }
}