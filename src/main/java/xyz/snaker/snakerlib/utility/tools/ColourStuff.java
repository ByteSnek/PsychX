package xyz.snaker.snakerlib.utility.tools;

import java.util.Random;

import xyz.snaker.snakerlib.internal.StringNuker;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class ColourStuff
{
    /**
     * Converts a hexidecimal to an integer
     *
     * @param hexCode The hexidecimal to convert
     * @return The hexidecimal as an integer
     **/
    public static int hexToInt(String hexCode)
    {
        StringNuker nuker = new StringNuker(hexCode);
        nuker.replaceAllAndDestroy("#");
        return Integer.parseInt(nuker.result(), 16);
    }

    /**
     * Converts a hexidecimal to a float
     *
     * @param hexCode The hexidecimal to convert
     * @return The hexidecimal as a float
     **/
    public static float hexToFloat(String hexCode)
    {
        StringNuker nuker = new StringNuker(hexCode);
        nuker.replaceAllAndDestroy("#");
        return Float.parseFloat(nuker.result());
    }

    /**
     * Generates a random hexidecimal
     *
     * @return A random hexidecimal as an integer
     **/
    public static int randomHex()
    {
        Random random = new Random();
        return random.nextInt(0xffffff + 1);
    }
}
