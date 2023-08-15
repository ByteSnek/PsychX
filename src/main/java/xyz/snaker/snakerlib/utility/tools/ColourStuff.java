package xyz.snaker.snakerlib.utility.tools;

import java.util.Random;

import xyz.snaker.snakerlib.internal.StringNuker;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class ColourStuff
{
    public static int hexToInt(String hexCode)
    {
        StringNuker nuker = new StringNuker(hexCode);
        nuker.replaceAllAndDestroy("#");
        return Integer.parseInt(nuker.result(), 16);
    }

    public static float hexToFloat(String hexCode)
    {
        StringNuker nuker = new StringNuker(hexCode);
        nuker.replaceAllAndDestroy("#");
        return Float.parseFloat(nuker.result());
    }

    public static int randomHex()
    {
        Random random = new Random();
        return random.nextInt(0xffffff + 1);
    }
}
