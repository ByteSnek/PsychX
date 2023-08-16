package xyz.snaker.snakerlib.math;

import java.math.BigInteger;

/**
 * Created by SnakerBone on 14/02/2023
 **/
public class Maths
{
    /**
     * The approximate AABB radius of a Minecraft world
     **/
    public static final int LEVEL_AABB_RADIUS = 0x989680;

    /**
     * The sum of {@link Math#PI} / 180
     **/
    public static final float RADIANS_TO_DEGREES = 57.29577951308232F;

    /**
     * The sum of 180 / {@link Math#PI}
     **/
    public static final float DEGREES_TO_RADIANS = 0.017453292519943F;

    /**
     * The limb swing multiplier used in many of Minecraft's entity animations
     **/
    public static final float LIMB_SWING_MULTIPLIER = 0.6662F;

    /**
     * {@link Math#PI} as a float
     **/
    public static final float PI = (float) Math.PI;

    /**
     * {@link Math#E} as a float
     **/
    public static final float E = (float) Math.E;

    /**
     * {@link Math#PI} / 2 as a float
     **/
    public static final float HALF_PI = Maths.PI / 2;

    /**
     * {@link Math#PI} * 2 as a float
     **/
    public static final float TWO_PI = Maths.PI * 2;

    /**
     * Checks if the difference of a and b are equal to the wanted value
     *
     * @param a      The first number
     * @param b      The second number
     * @param wanted The wanted result
     * @return True if a - b is equal to the wanted value
     **/
    public static boolean diffEquals(int a, int b, int wanted)
    {
        int upper = Math.max(a, b);
        int lower = Math.min(a, b);
        return upper - lower == wanted;
    }

    /**
     * Converts seconds to ticks
     *
     * @param a The number in seconds
     * @return The number in ticks
     **/
    public static int secondsToTicks(int a)
    {
        return a * 20;
    }

    /**
     * Converts ticks to seconds
     *
     * @param a The number in ticks
     * @return The number in seconds
     **/
    public static int ticksToSeconds(int a)
    {
        return a / 20;
    }

    /**
     * Checks if a number is the power of 2
     *
     * @param a The number to check
     * @return True if the number is the power of 2
     **/
    public static boolean isPow2(int a)
    {
        return (a & (a - 1)) == 0;
    }

    /**
     * Converts radians to degrees
     *
     * @param a The number in radians
     * @return The number in degrees
     **/
    public static float toDeg(double a)
    {
        return (float) a * RADIANS_TO_DEGREES;
    }

    /**
     * Converts degrees to radians
     *
     * @param a The number in degrees
     * @return The number in radians
     **/
    public static float toRad(double a)
    {
        return (float) a * DEGREES_TO_RADIANS;
    }

    /**
     * Finds the square root of a number
     *
     * @param a The number
     * @return The square root of the number as a float
     **/
    public static float sqrt(double a)
    {
        return (float) Math.sqrt(a);
    }

    /**
     * Finds the floor of a number
     *
     * @param a The number
     * @return The floor of the number as a float
     **/
    public static int floor(double a)
    {
        int value = (int) a;
        return a < (double) value ? value - 1 : value;
    }

    /**
     * Finds the ceiling of a number
     *
     * @param a The number
     * @return The ceiling of the number as a float
     **/
    public static int ceil(double a)
    {
        int value = (int) a;
        return a > (double) value ? value + 1 : value;
    }

    /**
     * Returns the smaller number
     *
     * @param a The first number
     * @param b The second number
     * @return The smaller number as a float
     **/
    public static float min(double a, double b)
    {
        return (float) Math.min(a, b);
    }

    /**
     * Returns the bigger number
     *
     * @param a The first number
     * @param b The second number
     * @return The bigger number as a float
     **/
    public static float max(double a, double b)
    {
        return (float) Math.max(a, b);
    }

    /**
     * Keeps a number between two values
     *
     * @param value The value to clamp
     * @param lower The lower bound
     * @param upper The upper bound
     * @return The clamped value as a float
     **/
    public static float clamp(int value, int lower, int upper)
    {
        return Math.max(Math.min(value, upper), lower);
    }

    /**
     * Keeps a number between two values
     *
     * @param value The value to clamp
     * @param lower The lower bound
     * @param upper The upper bound
     * @return The clamped value as a float
     **/
    public static float clamp(float value, float lower, float upper)
    {
        return Math.max(Math.min(value, upper), lower);
    }

    /**
     * Keeps a number between two values
     *
     * @param value The value to clamp
     * @param lower The lower bound
     * @param upper The upper bound
     * @return The clamped value as a float
     **/
    public static float clamp(double value, double lower, double upper)
    {
        return (float) Math.max(Math.min(value, upper), lower);
    }

    /**
     * Finds the squared power of a number
     *
     * @param a The number
     * @return The squared power of the number
     **/
    public static int powSq(int a)
    {
        return (int) Math.pow(a, a);
    }

    /**
     * Finds the squared power of a number
     *
     * @param a The number
     * @return The squared power of the number
     **/
    public static float powSq(double a)
    {
        return (float) Math.pow(a, a);
    }

    /**
     * Finds the power of 2 by using an exponent
     *
     * @param a The exponent
     * @return The power of the number
     **/
    public static int pow2e(int a)
    {
        return (int) Math.pow(2, a);
    }

    /**
     * Finds the power of 2 by using an exponent
     *
     * @param a The exponent
     * @return The power of the number
     **/
    public static double pow2e(double a)
    {
        return (int) Math.pow(2, a);
    }

    /**
     * Finds the power of a number with the exponent of 2
     *
     * @param a The base
     * @return The power of the number
     **/
    public static int pow2b(int a)
    {
        return (int) Math.pow(a, 2);
    }

    /**
     * Finds the power of a number with the exponent of 2
     *
     * @param a The base
     * @return The power of the number
     **/
    public static double pow2b(double a)
    {
        return (int) Math.pow(a, 2);
    }

    /**
     * Calculates the linear interpolation of three numbers
     *
     * @param a The first number
     * @param b The second number
     * @param c The third number
     * @return The linear interpolation of the three numbers
     **/
    public static float lerp(double a, double b, double c)
    {
        return (float) b + (float) a * ((float) c - (float) b);
    }

    /**
     * Calculates the sine angle of a number
     *
     * @param a The number
     * @return The sine angle as a float
     **/
    public static float sin(double a)
    {
        return (float) Math.sin(a);
    }

    /**
     * Calculates the cosine angle of a number
     *
     * @param a The number
     * @return The cosine angle as a float
     **/
    public static float cos(double a)
    {
        return (float) Math.cos(a);
    }

    /**
     * Calculates the tangent angle of a number
     *
     * @param a The number
     * @return The tangent angle as a float
     **/
    public static float tan(double a)
    {
        return (float) Math.tan(a);
    }

    /**
     * Calculates the arcsine angle of a number
     *
     * @param a The number
     * @return The arcsine angle as a float
     **/
    public static float asin(double a)
    {
        return (float) Math.asin(a);
    }

    /**
     * Calculates the arccosine angle of a number
     *
     * @param a The number
     * @return The arccosine angle as a float
     **/
    public static float acos(double a)
    {
        return (float) Math.acos(a);
    }

    /**
     * Calculates the arctangent angle of a number
     *
     * @param a The number
     * @return The arctangent angle as a float
     **/
    public static float atan(double a)
    {
        return (float) Math.atan(a);
    }

    /**
     * Calculates the arctangent angle of 2 numbers
     *
     * @param a The first number
     * @param b The second number
     * @return The arctangent angle of the 2 numbers as a float
     **/
    public static float atan2(double a, double b)
    {
        return (float) Math.atan2(a, b);
    }

    /**
     * Rotates towards an angle
     *
     * @param a The first number
     * @param b The second number
     * @return The rotation angle as a float
     **/
    public static float rotateTowards(double a, double b)
    {
        float angle = atan2(a, b);
        return -(angle * RADIANS_TO_DEGREES);
    }

    /**
     * Generates a factorial
     *
     * @param a The number
     * @return The value as a big integer
     **/
    public static BigInteger factorial(long a)
    {
        BigInteger factorial = BigInteger.ONE;
        for (long i = a;
             i > 0;
             i--) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        return factorial;
    }

    /**
     * Generates a factorial
     *
     * @param a The number
     * @return The value as a big integer
     **/
    public static BigInteger factorial(int a)
    {
        BigInteger factorial = BigInteger.ONE;
        for (int i = a; i > 0; i--) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        return factorial;
    }

    /**
     * Generates an additorial
     *
     * @param a The number
     * @return The value as a big integer
     **/
    public static BigInteger additorial(long a)
    {
        BigInteger additorial = BigInteger.ONE;
        for (long i = a; i > 0; i--) {
            additorial = additorial.add(BigInteger.valueOf(i));
        }
        return additorial;
    }

    /**
     * Generates an additorial
     *
     * @param a The number
     * @return The value as a big integer
     **/
    public static BigInteger additorial(int a)
    {
        BigInteger additorial = BigInteger.ONE;
        for (int i = a; i > 0; i--) {
            additorial = additorial.add(BigInteger.valueOf(i));
        }
        return additorial;
    }
}