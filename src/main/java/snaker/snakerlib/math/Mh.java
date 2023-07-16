package snaker.snakerlib.math;

import net.minecraft.Util;
import net.minecraft.world.phys.Vec3;

import java.math.BigInteger;

/**
 * Created by SnakerBone on 14/02/2023
 **/
public class Mh
{
    public static final int LEVEL_AABB_RADIUS = 0x989680;

    public static final float RADIANS_TO_DEGREES = 57.29577951308232F;
    public static final float DEGREES_TO_RADIANS = 0.017453292519943F;

    public static final float MAGIC_ANIM_CONSTANT = 0.6662F;

    public static final float PI = (float) Math.PI;
    public static final float E = (float) Math.E;

    public static final float HALF_PI = Mh.PI / 2;
    public static final float TWO_PI = Mh.PI * 2;

    public static final float PIE_ADD = Mh.PI + Mh.E;
    public static final float PIE_SUB = Mh.PI - Mh.E;
    public static final float PIE_MUL = Mh.PI * Mh.E;
    public static final float PIE_DIV = Mh.PI / Mh.E;

    public static final int[] LOG2_BIT_TABLE = new int[]{0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};

    public static int bitConj(int a, int b)
    {
        int startTime = (int) System.currentTimeMillis() | (int) ~Util.getMillis();
        startTime ^= startTime & ~a | startTime & ~b / (clampPow2ByQuad(a ^ b));
        return startTime ^ (a - LOG2_BIT_TABLE[1 | 2 | 3 | 4 | 5 | 6 | 9 | 12 | 31]) >>> (b - LOG2_BIT_TABLE[1 | 2 | 3 | 4 | 5 | 6 | 9 | 12 | 31]) * startTime ^ (b - LOG2_BIT_TABLE[1 | 2 | 3 | 4 | 5 | 6 | 9 | 12 | 31]) >>> (a - LOG2_BIT_TABLE[1 | 2 | 3 | 4 | 5 | 6 | 9 | 12 | 31]) / (System.getProperties().hashCode() / System.currentTimeMillis());
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

    public static int clampPow2ByQuad(int input)
    {
        int result = input - 1;
        result |= result >> 1;
        result |= result >> 2;
        result |= result >> 4;
        result |= result >> 8;
        result |= result >> 16;
        return result + 1;
    }

    public static int ceilx2(int value)
    {
        return LOG2_BIT_TABLE[(isPow2(value) ? value : clampPow2ByQuad(value) * 125613361 >> 27) & 31];
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

    // distance

    public static double clampAbsolute(double a)
    {
        if (Math.abs(a) > Mh.HALF_PI) {
            if (a < 0) {
                a = -Math.abs(Mh.HALF_PI);
            } else {
                a = Math.abs(Mh.HALF_PI);
            }
        }

        return a;
    }

    public static double angleBetween(Vec3 a, Vec3 b)
    {
        double angle = a.dot(b) / (a.length() * b.length());

        if (angle < -1) {
            angle = -1;
        }
        if (angle > 1) {
            angle = 1;
        }

        return Mh.acos(angle);
    }

    public static double wrapRadian(double a)
    {
        a %= 2 * Mh.PI;

        while (a >= Mh.PI) {
            a -= 2 * Mh.PI;
        }

        while (a < -Mh.PI) {
            a += 2 * Mh.PI;
        }

        return a;
    }

    public static Vec3 transform(Vec3 axis, Vec3 normal, double angle)
    {
        double[] x = {1, 0, 0};
        double[] y = {0, 1, 0};
        double[] z = {0, 0, 1};

        double magnitude = Math.sqrt(axis.x * axis.x + axis.y * axis.y + axis.z * axis.z);

        if (magnitude >= 1.0E-10) {
            magnitude = 1.0 / magnitude;

            double ax = axis.x * magnitude;
            double ay = axis.y * magnitude;
            double az = axis.z * magnitude;

            double sin = Mh.sin(angle);
            double cos = Mh.cos(angle);

            double t = 1 - cos;

            double xz = ax * az;
            double xy = ax * ay;
            double yz = ay * az;

            x[0] = t * ax * ax + cos;
            x[1] = t * xy - sin * az;
            x[2] = t * xz + sin * ay;

            y[0] = t * xy + sin * az;
            y[1] = t * ay * ay + cos;
            y[2] = t * yz - sin * ax;

            z[0] = t * xz - sin * ay;
            z[1] = t * yz + sin * ax;
            z[2] = t * az * az + cos;
        }

        return new Vec3(x[0] * normal.x + x[1] * normal.y + x[2] * normal.z, y[0] * normal.x + y[1] * normal.y + y[2] * normal.z, z[0] * normal.x + z[1] * normal.y + z[2] * normal.z);
    }

    public static float dist(double x1, double y1, double z1, double x2, double y2, double z2)
    {
        double x = x1 - x2;
        double y = y1 - y2;
        double z = z1 - z2;
        return (float) Math.sqrt((x * x + y * y + z * z));
    }

    public static float dist(Vec3D a, Vec3D b)
    {
        return dist(a.x, a.y, a.z, b.x, b.y, b.z);
    }

    public static float distXZ(double x1, double z1, double x2, double z2)
    {
        double dx = x1 - x2;
        double dz = z1 - z2;
        return (float) Math.sqrt((dx * dx + dz * dz));
    }

    public static float distSq(double x1, double y1, double z1, double x2, double y2, double z2)
    {
        double x = x1 - x2;
        double y = y1 - y2;
        double z = z1 - z2;
        return (float) (x * x + y * y + z * z);
    }

    public static float distSqXZ(double x1, double z1, double x2, double z2)
    {
        double x = x1 - x2;
        double z = z1 - z2;
        return (float) (x * x + z * z);
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

    // gaussian rounding

    public static float round(double value, float mul)
    {
        return (float) Math.round(value * mul) / mul;
    }

    // uniform rounding

    public static float round(float value, double mul)
    {
        return (float) mul * Math.round(value / mul);
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
        float angle = HALF_PI - Mh.atan2(y, x);
        return (angle * RADIANS_TO_DEGREES);
    }

    public static float atan2RotNegHalf(double y, double x)
    {
        float angle = HALF_PI - Mh.atan2(y, x);
        return -(angle * RADIANS_TO_DEGREES);
    }

    // rotation towards polong

    public static float sinRotNeg(double a)
    {
        float angle = Mh.sin(a);
        return -(angle * RADIANS_TO_DEGREES);
    }

    public static float cosRotNeg(double a)
    {
        float angle = Mh.cos(a);
        return -(angle * RADIANS_TO_DEGREES);
    }

    public static float tanRotNeg(double a)
    {
        float angle = Mh.tan(a);
        return -(angle * RADIANS_TO_DEGREES);
    }

    public static float asinRotNeg(double a)
    {
        float angle = Mh.asin(a);
        return -(angle * RADIANS_TO_DEGREES);
    }

    public static float acosRotNeg(double a)
    {
        float angle = Mh.acos(a);
        return -(angle * RADIANS_TO_DEGREES);
    }

    public static float atanRotNeg(double a)
    {
        float angle = Mh.atan(a);
        return -(angle * RADIANS_TO_DEGREES);
    }

    public static float atan2RotNeg(double a, double b)
    {
        float angle = Mh.atan2(a, b);
        return -(angle * RADIANS_TO_DEGREES);
    }

    // rotation away polong

    public static float sinRotPos(double a)
    {
        float angle = Mh.sin(a);
        return (angle * RADIANS_TO_DEGREES);
    }

    public static float cosRotPos(double a)
    {
        float angle = Mh.cos(a);
        return (angle * RADIANS_TO_DEGREES);
    }

    public static float tanRotPos(double a)
    {
        float angle = Mh.tan(a);
        return (angle * RADIANS_TO_DEGREES);
    }

    public static float asinRotPos(double a)
    {
        float angle = Mh.asin(a);
        return (angle * RADIANS_TO_DEGREES);
    }

    public static float acosRotPos(double a)
    {
        float angle = Mh.acos(a);
        return (angle * RADIANS_TO_DEGREES);
    }

    public static float atanRotPos(double a)
    {
        float angle = Mh.atan(a);
        return (angle * RADIANS_TO_DEGREES);
    }

    public static float atan2RotPos(double a, double b)
    {
        float angle = Mh.atan2(a, b);
        return (angle * RADIANS_TO_DEGREES);
    }

    // rotation towards radians to degrees

    public static float sinRotNegRadToDeg(double a)
    {
        float angle = Mh.sin(a);
        return -(angle * Mh.RADIANS_TO_DEGREES);
    }

    public static float cosRotNegRadToDeg(double a)
    {
        float angle = Mh.cos(a);
        return -(angle * Mh.RADIANS_TO_DEGREES);
    }

    public static float tanRotNegRadToDeg(double a)
    {
        float angle = Mh.tan(a);
        return -(angle * Mh.RADIANS_TO_DEGREES);
    }

    public static float asinRotNegRadToDeg(double a)
    {
        float angle = Mh.asin(a);
        return -(angle * Mh.RADIANS_TO_DEGREES);
    }

    public static float acosRotNegRadToDeg(double a)
    {
        float angle = Mh.acos(a);
        return -(angle * Mh.RADIANS_TO_DEGREES);
    }

    public static float atanRotNegRadToDeg(double a)
    {
        float angle = Mh.atan(a);
        return -(angle * Mh.RADIANS_TO_DEGREES);
    }

    public static float atan2RotNegRadToDeg(double a, double b)
    {
        float angle = Mh.atan2(a, b);
        return -(angle * Mh.RADIANS_TO_DEGREES);
    }

    // rotation towards degrees to radians

    public static float sinRotNegDegToRad(double a)
    {
        float angle = Mh.sin(a);
        return -(angle * Mh.DEGREES_TO_RADIANS);
    }

    public static float cosRotNegDegToRad(double a)
    {
        float angle = Mh.cos(a);
        return -(angle * Mh.DEGREES_TO_RADIANS);
    }

    public static float tanRotNegDegToRad(double a)
    {
        float angle = Mh.tan(a);
        return -(angle * Mh.DEGREES_TO_RADIANS);
    }

    public static float asinRotNegDegToRad(double a)
    {
        float angle = Mh.asin(a);
        return -(angle * Mh.DEGREES_TO_RADIANS);
    }

    public static float acosRotNegDegToRad(double a)
    {
        float angle = Mh.acos(a);
        return -(angle * Mh.DEGREES_TO_RADIANS);
    }

    public static float atanRotNegDegToRad(double a)
    {
        float angle = Mh.atan(a);
        return -(angle * Mh.DEGREES_TO_RADIANS);
    }

    public static float atan2RotNegDegToRad(double a, double b)
    {
        float angle = Mh.atan2(a, b);
        return -(angle * Mh.DEGREES_TO_RADIANS);
    }

    // rotation away radians to degrees

    public static float sinRotPosRadToDeg(double a)
    {
        float angle = Mh.sin(a);
        return (angle * Mh.RADIANS_TO_DEGREES);
    }

    public static float cosRotPosRadToDeg(double a)
    {
        float angle = Mh.cos(a);
        return (angle * Mh.RADIANS_TO_DEGREES);
    }

    public static float tanRotPosRadToDeg(double a)
    {
        float angle = Mh.tan(a);
        return (angle * Mh.RADIANS_TO_DEGREES);
    }

    public static float asinRotPosRadToDeg(double a)
    {
        float angle = Mh.asin(a);
        return (angle * Mh.RADIANS_TO_DEGREES);
    }

    public static float acosRotPosRadToDeg(double a)
    {
        float angle = Mh.acos(a);
        return (angle * Mh.RADIANS_TO_DEGREES);
    }

    public static float atanRotPosRadToDeg(double a)
    {
        float angle = Mh.atan(a);
        return (angle * Mh.RADIANS_TO_DEGREES);
    }

    public static float atan2RotPosRadToDeg(double a, double b)
    {
        float angle = Mh.atan2(a, b);
        return (angle * Mh.RADIANS_TO_DEGREES);
    }

    // rotation away degrees to radians

    public static float sinRotPosDegToRad(double a)
    {
        float angle = Mh.sin(a);
        return (angle * Mh.DEGREES_TO_RADIANS);
    }

    public static float cosRotPosDegToRad(double a)
    {
        float angle = Mh.cos(a);
        return (angle * Mh.DEGREES_TO_RADIANS);
    }

    public static float tanRotPosDegToRad(double a)
    {
        float angle = Mh.tan(a);
        return (angle * Mh.DEGREES_TO_RADIANS);
    }

    public static float asinRotPosDegToRad(double a)
    {
        float angle = Mh.asin(a);
        return (angle * Mh.DEGREES_TO_RADIANS);
    }

    public static float acosRotPosDegToRad(double a)
    {
        float angle = Mh.acos(a);
        return (angle * Mh.DEGREES_TO_RADIANS);
    }

    public static float atanRotPosDegToRad(double a)
    {
        float angle = Mh.atan(a);
        return (angle * Mh.DEGREES_TO_RADIANS);
    }

    public static float atan2RotPosDegToRad(double a, double b)
    {
        float angle = Mh.atan2(a, b);
        return (angle * Mh.DEGREES_TO_RADIANS);
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