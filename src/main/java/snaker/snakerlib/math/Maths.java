package snaker.snakerlib.math;

import java.math.BigInteger;

/**
 * Created by SnakerBone on 14/02/2023
 **/
public class Maths
{
    public static final int LEVEL_AABB_RADIUS = 0x989680;

    public static final float RADIANS_TO_DEGREES = 57.29577951308232F;
    public static final float DEGREES_TO_RADIANS = 0.017453292519943F;

    public static final float MAGIC_ANIM_CONSTANT = 0.6662F;

    public static final float PI = (float) Math.PI;
    public static final float E = (float) Math.E;

    public static final float HALF_PI = Maths.PI / 2;
    public static final float TWO_PI = Maths.PI * 2;

    public static boolean diffEquals(int a, int b, int wanted)
    {
        int upper = Math.max(a, b);
        int lower = Math.min(a, b);
        return upper - lower == wanted;
    }

    public static int secondsToTicks(int a)
    {
        return a * 20;
    }

    public static int ticksToSeconds(int a)
    {
        return a / 20;
    }

    public static boolean isPow2(int value)
    {
        return (value & (value - 1)) == 0;
    }

    public static float toDeg(double radians)
    {
        return (float) radians * RADIANS_TO_DEGREES;
    }

    public static float toRad(double degrees)
    {
        return (float) degrees * DEGREES_TO_RADIANS;
    }

    public static float sqrt(double a)
    {
        return (float) Math.sqrt(a);
    }

    public static int floor(double a)
    {
        int value = (int) a;
        return a < (double) value ? value - 1 : value;
    }

    public static int ceil(double a)
    {
        int value = (int) a;
        return a > (double) value ? value + 1 : value;
    }

    // min, max

    public static float min(double a, double b)
    {
        return (float) Math.min(a, b);
    }

    public static float max(double a, double b)
    {
        return (float) Math.max(a, b);
    }

    // clamping

    public static float clamp(int value, int lower, int upper)
    {
        return Math.max(Math.min(value, upper), lower);
    }

    public static float clamp(float value, float lower, float upper)
    {
        return Math.max(Math.min(value, upper), lower);
    }

    public static float clamp(double value, double lower, double upper)
    {
        return (float) Math.max(Math.min(value, upper), lower);
    }

    // power of

    public static int powSq(int value)
    {
        return (int) Math.pow(value, value);
    }

    public static float powSq(double value)
    {
        return (float) Math.pow(value, value);
    }

    public static int pow2e(int exponent)
    {
        return (int) Math.pow(2, exponent);
    }

    public static double pow2e(double exponent)
    {
        return (int) Math.pow(2, exponent);
    }

    public static int pow2b(int base)
    {
        return (int) Math.pow(base, 2);
    }

    public static double pow2b(double base)
    {
        return (int) Math.pow(base, 2);
    }

    // sine, cosine, tangent, etc

    public static float lerp(double a, double b, double c)
    {
        return (float) b + (float) a * ((float) c - (float) b);
    }

    public static float sin(double a)
    {
        return (float) Math.sin(a);
    }

    public static float cos(double a)
    {
        return (float) Math.cos(a);
    }

    public static float tan(double a)
    {
        return (float) Math.tan(a);
    }

    public static float asin(double a)
    {
        return (float) Math.asin(a);
    }

    public static float acos(double a)
    {
        return (float) Math.acos(a);
    }

    public static float atan(double a)
    {
        return (float) Math.atan(a);
    }

    public static float atan2(double y, double x)
    {
        return (float) Math.atan2(y, x);
    }

    public static float atan2RotPosHalf(double y, double x)
    {
        float angle = HALF_PI - Maths.atan2(y, x);
        return (angle * RADIANS_TO_DEGREES);
    }

    public static float atan2RotNegHalf(double y, double x)
    {
        float angle = HALF_PI - Maths.atan2(y, x);
        return -(angle * RADIANS_TO_DEGREES);
    }

    // rotation towards polong

    public static float sinRotNeg(double a)
    {
        float angle = Maths.sin(a);
        return -(angle * RADIANS_TO_DEGREES);
    }

    public static float cosRotNeg(double a)
    {
        float angle = Maths.cos(a);
        return -(angle * RADIANS_TO_DEGREES);
    }

    public static float tanRotNeg(double a)
    {
        float angle = Maths.tan(a);
        return -(angle * RADIANS_TO_DEGREES);
    }

    public static float asinRotNeg(double a)
    {
        float angle = Maths.asin(a);
        return -(angle * RADIANS_TO_DEGREES);
    }

    public static float acosRotNeg(double a)
    {
        float angle = Maths.acos(a);
        return -(angle * RADIANS_TO_DEGREES);
    }

    public static float atanRotNeg(double a)
    {
        float angle = Maths.atan(a);
        return -(angle * RADIANS_TO_DEGREES);
    }

    public static float atan2RotNeg(double a, double b)
    {
        float angle = Maths.atan2(a, b);
        return -(angle * RADIANS_TO_DEGREES);
    }

    // rotation away polong

    public static float sinRotPos(double a)
    {
        float angle = Maths.sin(a);
        return (angle * RADIANS_TO_DEGREES);
    }

    public static float cosRotPos(double a)
    {
        float angle = Maths.cos(a);
        return (angle * RADIANS_TO_DEGREES);
    }

    public static float tanRotPos(double a)
    {
        float angle = Maths.tan(a);
        return (angle * RADIANS_TO_DEGREES);
    }

    public static float asinRotPos(double a)
    {
        float angle = Maths.asin(a);
        return (angle * RADIANS_TO_DEGREES);
    }

    public static float acosRotPos(double a)
    {
        float angle = Maths.acos(a);
        return (angle * RADIANS_TO_DEGREES);
    }

    public static float atanRotPos(double a)
    {
        float angle = Maths.atan(a);
        return (angle * RADIANS_TO_DEGREES);
    }

    public static float atan2RotPos(double a, double b)
    {
        float angle = Maths.atan2(a, b);
        return (angle * RADIANS_TO_DEGREES);
    }

    // rotation towards radians to degrees

    public static float sinRotNegRadToDeg(double a)
    {
        float angle = Maths.sin(a);
        return -(angle * Maths.RADIANS_TO_DEGREES);
    }

    public static float cosRotNegRadToDeg(double a)
    {
        float angle = Maths.cos(a);
        return -(angle * Maths.RADIANS_TO_DEGREES);
    }

    public static float tanRotNegRadToDeg(double a)
    {
        float angle = Maths.tan(a);
        return -(angle * Maths.RADIANS_TO_DEGREES);
    }

    public static float asinRotNegRadToDeg(double a)
    {
        float angle = Maths.asin(a);
        return -(angle * Maths.RADIANS_TO_DEGREES);
    }

    public static float acosRotNegRadToDeg(double a)
    {
        float angle = Maths.acos(a);
        return -(angle * Maths.RADIANS_TO_DEGREES);
    }

    public static float atanRotNegRadToDeg(double a)
    {
        float angle = Maths.atan(a);
        return -(angle * Maths.RADIANS_TO_DEGREES);
    }

    public static float atan2RotNegRadToDeg(double a, double b)
    {
        float angle = Maths.atan2(a, b);
        return -(angle * Maths.RADIANS_TO_DEGREES);
    }

    // rotation towards degrees to radians

    public static float sinRotNegDegToRad(double a)
    {
        float angle = Maths.sin(a);
        return -(angle * Maths.DEGREES_TO_RADIANS);
    }

    public static float cosRotNegDegToRad(double a)
    {
        float angle = Maths.cos(a);
        return -(angle * Maths.DEGREES_TO_RADIANS);
    }

    public static float tanRotNegDegToRad(double a)
    {
        float angle = Maths.tan(a);
        return -(angle * Maths.DEGREES_TO_RADIANS);
    }

    public static float asinRotNegDegToRad(double a)
    {
        float angle = Maths.asin(a);
        return -(angle * Maths.DEGREES_TO_RADIANS);
    }

    public static float acosRotNegDegToRad(double a)
    {
        float angle = Maths.acos(a);
        return -(angle * Maths.DEGREES_TO_RADIANS);
    }

    public static float atanRotNegDegToRad(double a)
    {
        float angle = Maths.atan(a);
        return -(angle * Maths.DEGREES_TO_RADIANS);
    }

    public static float atan2RotNegDegToRad(double a, double b)
    {
        float angle = Maths.atan2(a, b);
        return -(angle * Maths.DEGREES_TO_RADIANS);
    }

    // rotation away radians to degrees

    public static float sinRotPosRadToDeg(double a)
    {
        float angle = Maths.sin(a);
        return (angle * Maths.RADIANS_TO_DEGREES);
    }

    public static float cosRotPosRadToDeg(double a)
    {
        float angle = Maths.cos(a);
        return (angle * Maths.RADIANS_TO_DEGREES);
    }

    public static float tanRotPosRadToDeg(double a)
    {
        float angle = Maths.tan(a);
        return (angle * Maths.RADIANS_TO_DEGREES);
    }

    public static float asinRotPosRadToDeg(double a)
    {
        float angle = Maths.asin(a);
        return (angle * Maths.RADIANS_TO_DEGREES);
    }

    public static float acosRotPosRadToDeg(double a)
    {
        float angle = Maths.acos(a);
        return (angle * Maths.RADIANS_TO_DEGREES);
    }

    public static float atanRotPosRadToDeg(double a)
    {
        float angle = Maths.atan(a);
        return (angle * Maths.RADIANS_TO_DEGREES);
    }

    public static float atan2RotPosRadToDeg(double a, double b)
    {
        float angle = Maths.atan2(a, b);
        return (angle * Maths.RADIANS_TO_DEGREES);
    }

    // rotation away degrees to radians

    public static float sinRotPosDegToRad(double a)
    {
        float angle = Maths.sin(a);
        return (angle * Maths.DEGREES_TO_RADIANS);
    }

    public static float cosRotPosDegToRad(double a)
    {
        float angle = Maths.cos(a);
        return (angle * Maths.DEGREES_TO_RADIANS);
    }

    public static float tanRotPosDegToRad(double a)
    {
        float angle = Maths.tan(a);
        return (angle * Maths.DEGREES_TO_RADIANS);
    }

    public static float asinRotPosDegToRad(double a)
    {
        float angle = Maths.asin(a);
        return (angle * Maths.DEGREES_TO_RADIANS);
    }

    public static float acosRotPosDegToRad(double a)
    {
        float angle = Maths.acos(a);
        return (angle * Maths.DEGREES_TO_RADIANS);
    }

    public static float atanRotPosDegToRad(double a)
    {
        float angle = Maths.atan(a);
        return (angle * Maths.DEGREES_TO_RADIANS);
    }

    public static float atan2RotPosDegToRad(double a, double b)
    {
        float angle = Maths.atan2(a, b);
        return (angle * Maths.DEGREES_TO_RADIANS);
    }

    // wrapping to degrees

    public static float wrap(double a)
    {
        float value = (float) a % 360;

        if (value >= 180) {
            value -= 360;
        }

        if (value < -180) {
            value += 360;
        }

        return value;
    }

    // factorials, additorials, etc

    public static BigInteger factorial(long value)
    {
        BigInteger factorial = BigInteger.ONE;
        for (long i = value; i > 0; i--) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        return factorial;
    }

    public static BigInteger factorial(int value)
    {
        BigInteger factorial = BigInteger.ONE;
        for (int i = value; i > 0; i--) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        return factorial;
    }

    public static BigInteger additorial(long value)
    {
        BigInteger additorial = BigInteger.ONE;
        for (long i = value; i > 0; i--) {
            additorial = additorial.add(BigInteger.valueOf(i));
        }
        return additorial;
    }

    public static BigInteger additorial(int value)
    {
        BigInteger additorial = BigInteger.ONE;
        for (int i = value; i > 0; i--) {
            additorial = additorial.add(BigInteger.valueOf(i));
        }
        return additorial;
    }
}