package snaker.snakerlib.math;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Created by SnakerBone on 12/05/2023
 **/
public class Vec3D
{
    public static final Vec3D ZERO = new Vec3D(0, 0, 0);
    public static final Vec3D CENTRE = new Vec3D(0.5, 0.5, 0.5);
    public static final Vec3D ONE = new Vec3D(1, 1, 1);
    public static final Vec3D X_POS = new Vec3D(1, 0, 0);
    public static final Vec3D X_NEG = new Vec3D(-1, 0, 0);
    public static final Vec3D Y_POS = new Vec3D(0, 1, 0);
    public static final Vec3D Y_NEG = new Vec3D(0, -1, 0);
    public static final Vec3D Z_POS = new Vec3D(0, 0, 1);
    public static final Vec3D Z_NEG = new Vec3D(0, 0, -1);

    public double x;
    public double y;
    public double z;

    public Vec3D()
    {
        this(ZERO);
    }

    public Vec3D(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3D(BlockPos pos)
    {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public Vec3D(Vec3D vec)
    {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }

    public Vec3D(double[] doubles)
    {
        this(doubles[0], doubles[1], doubles[2]);
    }

    public Vec3D(float[] floats)
    {
        this(floats[0], floats[1], floats[2]);
    }

    public Vec3D(Vec3 vec)
    {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }

    public static Vec3D blockPos(BlockPos pos)
    {
        return vec3i(pos);
    }

    public static Vec3D vec3i(Vec3i vec3i)
    {
        return new Vec3D(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }

    public static Vec3D centre(Vec3D vec)
    {
        return new Vec3D(vec.x, vec.y, vec.z).add(0.5);
    }

    public static Vec3D blockPosCentre(BlockPos pos)
    {
        return blockPos(pos).add(0.5);
    }

    public static Vec3D entity(Entity entity)
    {
        return new Vec3D(entity.position());
    }

    public static Vec3D entityCentre(Entity entity)
    {
        return new Vec3D(entity.position()).add(0, entity.getMyRidingOffset() + entity.getBbHeight() / 2, 0);
    }

    public static Vec3D blockEntity(BlockEntity entity)
    {
        return blockPos(entity.getBlockPos());
    }

    public static Vec3D blockEntityCentre(BlockEntity entity)
    {
        return blockEntity(entity).add(0.5);
    }

    public static Vec3D axes(double[] doubles)
    {
        return new Vec3D(doubles[2], doubles[0], doubles[1]);
    }

    public static Vec3D axes(float[] floats)
    {
        return new Vec3D(floats[2], floats[0], floats[1]);
    }

    public static Vec3D array(double[] doubles)
    {
        return new Vec3D(doubles[0], doubles[1], doubles[2]);
    }

    public static Vec3D array(float[] floats)
    {
        return new Vec3D(floats[0], floats[1], floats[2]);
    }

    public static Vec3D readTag(CompoundTag tag)
    {
        return new Vec3D(tag.getDouble("x"), tag.getDouble("y"), tag.getDouble("z"));
    }

    public CompoundTag writeTag(CompoundTag tag)
    {
        tag.putDouble("x", x);
        tag.putDouble("y", y);
        tag.putDouble("z", z);
        return tag;
    }

    public BlockPos pos()
    {
        return new BlockPos((int) x, (int) y, (int) z);
    }

    public Vector3f v3f()
    {
        return new Vector3f((float) x, (float) y, (float) z);
    }

    public Vec3 vec3()
    {
        return new Vec3((float) x, (float) y, (float) z);
    }

    public Vector4f v4f()
    {
        return new Vector4f((float) x, (float) y, (float) z, 1);
    }

    public double[] arrayDouble()
    {
        return new double[]{x, y, z};
    }

    public float[] arrayFloat()
    {
        return new float[]{(float) x, (float) y, (float) z};
    }

    public Vec3D set(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vec3D set(double value)
    {
        return set(value, value, value);
    }

    public Vec3D set(Vec3D vec)
    {
        return set(vec.x, vec.y, vec.z);
    }

    public Vec3D set(Vec3i vec3i)
    {
        return set(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }

    public Vec3D set(double[] doubles)
    {
        return set(doubles[0], doubles[1], doubles[2]);
    }

    public Vec3D set(float[] floats)
    {
        return set(floats[0], floats[1], floats[2]);
    }

    public Vec3D add(double x, double y, double z)
    {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vec3D add(double value)
    {
        return add(value, value, value);
    }

    public Vec3D add(Vec3D vec)
    {
        return add(vec.x, vec.y, vec.z);
    }

    public Vec3D add(Vec3 vec)
    {
        return add(vec.x, vec.y, vec.z);
    }

    public Vec3D add(BlockPos pos)
    {
        return add(pos.getX(), pos.getY(), pos.getZ());
    }

    public Vec3D sub(double x, double y, double z)
    {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vec3D sub(double value)
    {
        return sub(value, value, value);
    }

    public Vec3D sub(Vec3D vec)
    {
        return sub(vec.x, vec.y, vec.z);
    }

    public Vec3D sub(Vec3 vec)
    {
        return sub(vec.x, vec.y, vec.z);
    }

    public Vec3D sub(BlockPos pos)
    {
        return sub(pos.getX(), pos.getY(), pos.getZ());
    }

    public Vec3D mul(double x, double y, double z)
    {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    public Vec3D mul(double value)
    {
        return mul(value, value, value);
    }

    public Vec3D mul(Vec3D vec)
    {
        return mul(vec.x, vec.y, vec.z);
    }

    public Vec3D div(double x, double y, double z)
    {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    public Vec3D div(double value)
    {
        return div(value, value, value);
    }

    public Vec3D div(Vec3D vec)
    {
        return div(vec.x, vec.y, vec.z);
    }

    public Vec3D div(BlockPos pos)
    {
        return div(pos.getX(), pos.getY(), pos.getZ());
    }

    public Vec3D floor()
    {
        this.x = Mh.floor(x);
        this.y = Mh.floor(y);
        this.z = Mh.floor(z);
        return this;
    }

    public Vec3D ceil()
    {
        this.x = Mh.ceil(x);
        this.y = Mh.ceil(y);
        this.z = Mh.ceil(z);
        return this;
    }

    public double magnitudeSqrt()
    {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double magnitute()
    {
        return x * x + y * y + z * z;
    }

    public Vec3D negate()
    {
        this.x = -x;
        this.y = -y;
        this.z = -z;
        return this;
    }

    public Vec3D normalize()
    {
        double sqrt = magnitudeSqrt();
        if (sqrt != 0) {
            mul(1 / sqrt);
        }
        return this;
    }

    public double dist(Vec3D vec)
    {
        double x = this.x - vec.x;
        double y = this.y - vec.y;
        double z = this.z - vec.z;
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double distSq(Vec3D vec)
    {
        double x = this.x - vec.x;
        double y = this.y - vec.y;
        double z = this.z - vec.z;
        return x * x + y * y + z * z;
    }

    public double dot(double x, double y, double z)
    {
        return x * this.x + y * this.y + z * this.z;
    }

    public double dot(Vec3D vec)
    {
        double value = vec.x * x + vec.y * y + vec.z * z;
        if (value > 1 && value < 1.00001) {
            value = 1;
        } else if (value < -1 && value > -1.00001) {
            value = -1;
        }
        return value;
    }

    public Vec3D cross(Vec3D vec)
    {
        double x = y * vec.z - z * vec.y;
        double y = z * vec.x - this.x * vec.z;
        double z = this.x * vec.y - this.y * vec.x;
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vec3D perpendicular()
    {
        if (z == 0) {
            return zCross();
        }
        return xCross();
    }

    public Vec3D xCross()
    {
        double z = this.z;
        double y = -this.y;
        this.x = 0;
        this.y = z;
        this.z = y;
        return this;
    }

    public Vec3D yCross()
    {
        double z = -this.z;
        double x = this.x;
        this.x = z;
        this.y = 0;
        this.z = x;
        return this;
    }

    public Vec3D zCross()
    {
        double y = this.y;
        double x = -this.x;
        this.x = y;
        this.y = x;
        this.z = 0;
        return this;
    }

    public double scalar(Vec3D vec)
    {
        double value = vec.magnitudeSqrt();
        return value == 0 ? 0 : dot(vec) / value;
    }

    public Vec3D project(Vec3D vec)
    {
        double magnitute = vec.magnitute();
        if (magnitute == 0) {
            set(0, 0, 0);
            return this;
        }
        double dot = dot(vec) / magnitute;
        set(vec).mul(dot);
        return this;
    }

    public Vec3D conj(Quaternionf quat)
    {
        quat.conjugate();
        return this;
    }

    public double angle(Vec3D vec)
    {
        return Math.acos(copy().normalize().dot(vec.copy().normalize()));
    }

    @Nullable
    public Vec3D YZintercept(Vec3D vec, double value)
    {
        double x = vec.x - this.x;
        double y = vec.y - this.y;
        double z = vec.z - this.z;
        if (x == 0) {
            return null;
        }
        double amount = (value - this.x) / x;
        if (between(-1E-5, amount, 1E-5)) {
            return this;
        }
        if (!between(0, amount, 1)) {
            return null;
        }
        this.x = value;
        this.y += amount * y;
        this.z += amount * z;
        return this;
    }

    @Nullable
    public Vec3D XZintercept(Vec3D vec, double value)
    {
        double x = vec.x - this.x;
        double y = vec.y - this.y;
        double z = vec.z - this.z;
        if (y == 0) {
            return null;
        }
        double amount = (value - this.y) / y;
        if (between(-1E-5, amount, 1E-5)) {
            return this;
        }
        if (!between(0, amount, 1)) {
            return null;
        }
        this.x += amount * x;
        this.y = value;
        this.z += amount * z;
        return this;
    }

    @Nullable
    public Vec3D XYintercept(Vec3D vec, double value)
    {
        double dx = vec.x - x;
        double dy = vec.y - y;
        double dz = vec.z - z;
        if (dz == 0) {
            return null;
        }
        double amount = (value - z) / dz;
        if (between(-1E-5, amount, 1E-5)) {
            return this;
        }
        if (!between(0, amount, 1)) {
            return null;
        }
        x += amount * dx;
        y += amount * dy;
        z = value;
        return this;
    }

    public boolean isZero()
    {
        return x == 0 && y == 0 && z == 0;
    }

    public boolean isAxial()
    {
        return x == 0 ? (y == 0 || z == 0) : (y == 0 && z == 0);
    }

    public double getSide(int side)
    {
        switch (side) {
            case 0, 1 -> {
                return y;
            }
            case 2, 3 -> {
                return z;
            }
            case 4, 5 -> {
                return x;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public Vec3D setSide(int a, double b)
    {
        switch (a) {
            case 0, 1 -> y = b;
            case 2, 3 -> z = b;
            case 4, 5 -> x = b;
            default -> throw new IndexOutOfBoundsException();
        }
        return this;
    }

    @Override
    public int hashCode()
    {
        long xBits = Double.doubleToLongBits(x);
        int value = (int) (xBits ^ xBits >>> 32);
        xBits = Double.doubleToLongBits(y);
        value = 31 * value + (int) (xBits ^ xBits >>> 32);
        xBits = Double.doubleToLongBits(z);
        value = 31 * value + (int) (xBits ^ xBits >>> 32);
        return value;
    }

    @Override
    public boolean equals(Object object)
    {
        if (super.equals(object)) {
            return true;
        }
        if (!(object instanceof Vec3D vec)) {
            return false;
        }
        return x == vec.x && y == vec.y && z == vec.z;
    }

    public boolean equalsT(Vec3D vec)
    {
        return between(x - 1E-5, vec.x, x + 1E-5) && between(y - 1E-5, vec.y, y + 1E-5) && between(z - 1E-5, vec.z, z + 1E-5);
    }

    public Vec3D copy()
    {
        return new Vec3D(this);
    }

    @Override
    public String toString()
    {
        MathContext context = new MathContext(4, RoundingMode.HALF_UP);
        return "Vec3A(" + new BigDecimal(x, context) + ", " + new BigDecimal(y, context) + ", " + new BigDecimal(z, context) + ")";
    }

    public boolean between(double min, double value, double max)
    {
        return min <= value && value <= max;
    }
}