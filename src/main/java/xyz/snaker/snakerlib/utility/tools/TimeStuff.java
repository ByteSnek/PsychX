package xyz.snaker.snakerlib.utility.tools;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.math.Maths;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class TimeStuff
{
    /**
     * Checks if the remainder of the tick offset is equal to 0
     *
     * @param tickOffset The offset amount
     * @return True if the remainder of the tick offset is equal to 0
     **/
    public static boolean tickOffs(float tickOffset)
    {
        return SnakerLib.getClientTickCount() % tickOffset == 0;
    }

    /**
     * Checks if the remainder of the tick offset is equal to 0
     *
     * @param other      A ticker / timer
     * @param tickOffset The offset amount
     * @return True if the remainder of the tick offset is equal to 0
     **/
    public static boolean tickOffs(float other, float tickOffset)
    {
        return other % tickOffset == 0;
    }

    /**
     * Checks if the remainder of the second offset is equal to 0
     *
     * @param secOffset The offset amount
     * @return True if the remainder of the second offset is equal to 0
     **/
    public static boolean secOffs(int secOffset)
    {
        return SnakerLib.getClientTickCount() % Maths.secondsToTicks(secOffset) == 0;
    }

    /**
     * Checks if the remainder of the second offset is equal to 0
     *
     * @param other     A ticker / timer
     * @param secOffset The offset amount
     * @return True if the remainder of the second offset is equal to 0
     **/
    public static boolean secOffs(float other, int secOffset)
    {
        return other % Maths.secondsToTicks(secOffset) == 0;
    }
}
