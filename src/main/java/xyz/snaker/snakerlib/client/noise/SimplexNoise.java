package xyz.snaker.snakerlib.client.noise;

/**
 * A speed-improved simplex noise algorithm for 2D, 3D and 4D in Java.
 * <p>
 * Based on example code by Stefan Gustavson (stegu@itn.liu.se).
 * Optimisations by Peter Eastman (peastman@drizzle.stanford.edu).
 * Better rank ordering method for 4D by Stefan Gustavson in 2012.
 * <p>
 * This could be speeded up even further, but it's useful as it is.
 * <p>
 * Version 2012-03-09
 * <p>
 * This code was placed in the public domain by its original author,
 * Stefan Gustavson. You may use it as you see fit, but
 * attribution is appreciated.
 **/
public class SimplexNoise
{
    public static final Grad[] GRAD3 = {
            new Grad(1, 1, 0), new Grad(-1, 1, 0), new Grad(1, -1, 0), new Grad(-1, -1, 0),
            new Grad(1, 0, 1), new Grad(-1, 0, 1), new Grad(1, 0, -1), new Grad(-1, 0, -1),
            new Grad(0, 1, 1), new Grad(0, -1, 1), new Grad(0, 1, -1), new Grad(0, -1, -1)
    };

    public static final short[] PERMS = {
            151, 160, 137, 91, 90, 15,
            131, 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36, 103, 30, 69, 142, 8, 99, 37, 240, 21, 10, 23,
            190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33,
            88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139, 48, 27, 166,
            77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244,
            102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200, 196,
            135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123,
            5, 202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42,
            223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9,
            129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228,
            251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107,
            49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254,
            138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180
    };

    public static final short[] PERM_ORD = new short[512];
    public static final short[] PERM_MOD_12 = new short[512];

    static {
        for (int i = 0; i < 512; i++) {
            PERM_ORD[i] = PERMS[i & 255];
            PERM_MOD_12[i] = (short) (PERM_ORD[i] % 12);
        }
    }

    public static final float ONE_DIV_THREE = 1F / 3F;
    public static final float ONE_DIV_SIX = 1F / 6F;

    public static int fastfloor(float x)
    {
        return x < (int) x ? (int) x - 1 : (int) x;
    }

    public static float dot(Grad grad, float x, float y, float z)
    {
        return grad.x * x + grad.y * y + grad.z * z;
    }

    public static float create(float x, float y, float z)
    {
        float a, b, c, d;
        float xyz = (x + y + z) * ONE_DIV_THREE;

        int xFloor = fastfloor(x + xyz);
        int yFloor = fastfloor(y + xyz);
        int zFloor = fastfloor(z + xyz);

        float xyzFloor = (xFloor + yFloor + zFloor) * ONE_DIV_SIX;

        float xDiff = xFloor - xyzFloor;
        float yDiff = yFloor - xyzFloor;
        float zDiff = zFloor - xyzFloor;

        float xDiff0 = x - xDiff;
        float yDiff0 = y - yDiff;
        float zDiff0 = z - zDiff;

        int matrix00, matrix01, matrix02;
        int matrix10, matrix11, matrix12;

        if (xDiff0 >= yDiff0) {
            if (yDiff0 >= zDiff0) {
                matrix00 = 1;
                matrix01 = 0;
                matrix02 = 0;
                matrix10 = 1;
                matrix11 = 1;
                matrix12 = 0;
            } else if (xDiff0 >= zDiff0) {
                matrix00 = 1;
                matrix01 = 0;
                matrix02 = 0;
                matrix10 = 1;
                matrix11 = 0;
                matrix12 = 1;
            } else {
                matrix00 = 0;
                matrix01 = 0;
                matrix02 = 1;
                matrix10 = 1;
                matrix11 = 0;
                matrix12 = 1;
            }
        } else {
            if (yDiff0 < zDiff0) {
                matrix00 = 0;
                matrix01 = 0;
                matrix02 = 1;
                matrix10 = 0;
                matrix11 = 1;
                matrix12 = 1;
            } else if (xDiff0 < zDiff0) {
                matrix00 = 0;
                matrix01 = 1;
                matrix02 = 0;
                matrix10 = 0;
                matrix11 = 1;
                matrix12 = 1;
            } else {
                matrix00 = 0;
                matrix01 = 1;
                matrix02 = 0;
                matrix10 = 1;
                matrix11 = 1;
                matrix12 = 0;
            }
        }

        float point00 = xDiff0 - matrix00 + ONE_DIV_SIX;
        float point01 = yDiff0 - matrix01 + ONE_DIV_SIX;
        float point02 = zDiff0 - matrix02 + ONE_DIV_SIX;

        float point10 = xDiff0 - matrix10 + 2.0f * ONE_DIV_SIX;
        float point11 = yDiff0 - matrix11 + 2.0f * ONE_DIV_SIX;
        float point12 = zDiff0 - matrix12 + 2.0f * ONE_DIV_SIX;

        float xDiffConj = xDiff0 - 1F + 3F * ONE_DIV_SIX;
        float yDiffConj = yDiff0 - 1F + 3F * ONE_DIV_SIX;
        float zDiffConj = zDiff0 - 1F + 3F * ONE_DIV_SIX;

        int x32 = xFloor & 255;
        int y32 = yFloor & 255;
        int z32 = zFloor & 255;

        int gi0 = PERM_MOD_12[x32 + PERM_ORD[y32 + PERM_ORD[z32]]];
        int gi1 = PERM_MOD_12[x32 + matrix00 + PERM_ORD[y32 + matrix01 + PERM_ORD[z32 + matrix02]]];
        int gi2 = PERM_MOD_12[x32 + matrix10 + PERM_ORD[y32 + matrix11 + PERM_ORD[z32 + matrix12]]];
        int gi3 = PERM_MOD_12[x32 + 1 + PERM_ORD[y32 + 1 + PERM_ORD[z32 + 1]]];

        float magnitudeA = 0.6F - xDiff0 * xDiff0 - yDiff0 * yDiff0 - zDiff0 * zDiff0;

        if (magnitudeA < 0) {
            a = 0F;
        } else {
            magnitudeA *= magnitudeA;
            a = magnitudeA * magnitudeA * dot(GRAD3[gi0], xDiff0, yDiff0, zDiff0);
        }

        float magnitudeB = 0.6F - point00 * point00 - point01 * point01 - point02 * point02;

        if (magnitudeB < 0) {
            b = 0F;
        } else {
            magnitudeB *= magnitudeB;
            b = magnitudeB * magnitudeB * dot(GRAD3[gi1], point00, point01, point02);
        }

        float magnitudeC = 0.6f - point10 * point10 - point11 * point11 - point12 * point12;

        if (magnitudeC < 0) {
            c = 0F;
        } else {
            magnitudeC *= magnitudeC;
            c = magnitudeC * magnitudeC * dot(GRAD3[gi2], point10, point11, point12);
        }

        float magnitudeD = 0.6F - xDiffConj * xDiffConj - yDiffConj * yDiffConj - zDiffConj * zDiffConj;

        if (magnitudeD < 0) {
            d = 0F;
        } else {
            magnitudeD *= magnitudeD;
            d = magnitudeD * magnitudeD * dot(GRAD3[gi3], xDiffConj, yDiffConj, zDiffConj);
        }

        return 32F * (a + b + c + d);
    }

    public record Grad(float x, float y, float z)
    {

    }
}
