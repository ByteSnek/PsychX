package xyz.snaker.snakerlib.math;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

import org.jetbrains.annotations.NotNull;
import org.joml.*;

import com.mojang.blaze3d.vertex.PoseStack;

/**
 * Created by SnakerBone on 30/03/2023
 * <p>
 * A simple {@link PoseStack} builder that adds extra functions and paramater overloads for optimization and such
 * <p>
 * All functions are called directly to the parent stack while also returning the current ChainedPoseStack instance. Similar to that of a builder
 **/
public class PoseStackBuilder extends PoseStack
{
    /**
     * The current funny status
     **/
    private volatile boolean funny;

    /**
     * The pose stack
     **/
    private final PoseStack parent;

    public PoseStackBuilder(PoseStack parent)
    {
        this.parent = parent;
        this.funny = true;
    }

    /**
     * Sets the builder from another pose stack
     *
     * @param other The pose stack to use
     * @return A new pose stack builder
     **/
    public static PoseStackBuilder set(PoseStack other)
    {
        return new PoseStackBuilder(other);
    }

    /**
     * Rotates the specified entity based on their {@link Entity#tickCount} like tumbleweed
     *
     * @param entity The entity to rotate
     **/
    public <T extends Entity> PoseStackBuilder funny(T entity)
    {
        double value = (entity.tickCount + Minecraft.getInstance().getFrameTime()) / 8;
        Quaternionf rotation = rotateXYZ(value);
        if (funny) {
            rotate(rotation);
        }
        return this;
    }

    /**
     * Specifies whether the Entity passed into {@link PoseStackBuilder#funny} should rotate or not
     *
     * @param funny Should {@link PoseStackBuilder#funny} be active
     **/
    public boolean setFunnyStatus(boolean funny)
    {
        this.funny = funny;
        return funny;
    }

    @Override
    public void pushPose()
    {
        parent.pushPose();
    }

    @Override
    public void popPose()
    {
        parent.popPose();
    }

    @Override
    public void scale(float x, float y, float z)
    {
        parent.scale(x, y, z);
    }

    /**
     * Scales the current pose stack
     *
     * @param x The X amount to scale
     * @param y The Y amount to scale
     * @param z The Z amount to scale
     * @return The current pose stack builder
     **/
    public PoseStackBuilder scale(double x, double y, double z)
    {
        parent.scale((float) x, (float) y, (float) z);
        return this;
    }

    /**
     * Scales the current pose stack
     *
     * @param scale The XYZ amount to scale
     * @return The current pose stack builder
     **/
    public PoseStackBuilder scale(double scale)
    {
        parent.scale((float) scale, (float) scale, (float) scale);
        return this;
    }

    /**
     * Scales the current pose stack
     *
     * @param scale The XYZ amount to scale
     * @return The current pose stack builder
     **/
    public PoseStackBuilder scale(Double[] scale)
    {
        assertCorrectLength(scale, 3);
        parent.scale(scale[0].floatValue(), scale[1].floatValue(), scale[2].floatValue());
        return this;
    }

    /**
     * Scales the current pose stack
     *
     * @param scale The XYZ amount to scale
     * @return The current pose stack builder
     **/
    public PoseStackBuilder scale(Vector3f scale)
    {
        parent.scale(scale.x, scale.y, scale.z);
        return this;
    }

    @Override
    public void translate(float pX, float pY, float pZ)
    {
        parent.translate(pX, pY, pZ);
    }

    @Override
    public void translate(double pX, double pY, double pZ)
    {
        parent.translate(pX, pY, pZ);
    }

    /**
     * Moves the current pose stack
     *
     * @param translation The XYZ amount to move
     * @return The current pose stack builder
     **/
    public PoseStackBuilder translate(double translation)
    {
        parent.translate(translation, translation, translation);
        return this;
    }

    /**
     * Moves the current pose stack
     *
     * @param translation The XYZ amount to move
     * @return The current pose stack builder
     **/
    public PoseStackBuilder translate(double[] translation)
    {
        parent.translate(translation[0], translation[1], translation[2]);
        return this;
    }

    /**
     * Moves the current pose stack
     *
     * @param translation The XYZ amount to move
     * @return The current pose stack builder
     **/
    public PoseStackBuilder translate(Vector3f translation)
    {
        parent.translate(translation.x, translation.y, translation.z);
        return this;
    }

    /**
     * Moves the current pose stack
     *
     * @param translation The XYZ amount to move
     * @return The current pose stack builder
     **/
    public PoseStackBuilder translate(Vector3d translation)
    {
        parent.translate(translation.x, translation.y, translation.z);
        return this;
    }

    @Override
    public void mulPose(@NotNull Quaternionf quat)
    {
        parent.mulPose(quat);
    }

    @Override
    public void mulPoseMatrix(@NotNull Matrix4f matrix)
    {
        parent.mulPoseMatrix(matrix);
    }

    /**
     * Rotates the current pose stack
     *
     * @param rotation The XYZ amount to rotate
     * @return The current pose stack builder
     **/
    public PoseStackBuilder rotate(Quaternionf rotation)
    {
        parent.mulPose(rotation);
        return this;
    }

    /**
     * Rotates the current pose stack
     *
     * @param x The X amount to rotate
     * @param y The Y amount to rotate
     * @param z The Z amount to rotate
     * @return The current pose stack builder
     **/
    public PoseStackBuilder rotate(double x, double y, double z)
    {
        rotate(new Quaternionf(x, y, z, 1));
        return this;
    }

    /**
     * Rotates the current pose stack
     *
     * @param x The X amount to rotate
     * @param y The Y amount to rotate
     * @param z The Z amount to rotate
     * @param w The W amount to rotate
     * @return The current pose stack builder
     **/
    public PoseStackBuilder rotate(double x, double y, double z, double w)
    {
        rotate(new Quaternionf(x, y, z, w));
        return this;
    }

    /**
     * Rotates the current pose stack
     *
     * @param rotation The XYZW amount to rotate
     * @return The current pose stack builder
     **/
    public PoseStackBuilder rotate(Double[] rotation)
    {
        assertCorrectLength(rotation, 4);
        rotate(new Quaternionf(rotation[0], rotation[1], rotation[2], rotation[3]));
        return this;
    }

    @Override
    public void rotateAround(@NotNull Quaternionf quat, float x, float y, float z)
    {
        parent.rotateAround(quat, x, y, z);
    }

    /**
     * Rotates the current pose stack around a pivot point
     *
     * @param rotation The XYZW amount to rotate
     * @param pos      The pivot point
     * @return The current pose stack builder
     **/
    public PoseStackBuilder rotateAround(Quaternionf rotation, Vector3f pos)
    {
        parent.rotateAround(rotation, pos.x, pos.y, pos.z);
        return this;
    }

    /**
     * Rotates the current pose stack around a pivot point
     *
     * @param rotation The XYZW amount to rotate
     * @param pos      The pivot point
     * @return The current pose stack builder
     **/
    public PoseStackBuilder rotateAround(Vector4f rotation, Vector3f pos)
    {
        parent.rotateAround(new Quaternionf(rotation.x, rotation.y, rotation.z, rotation.w), pos.x, pos.y, pos.z);
        return this;
    }

    /**
     * Rotates the current pose stack around a pivot point
     *
     * @param rotation The XYZW amount to rotate
     * @param x        The X pivot point
     * @param y        The Y pivot point
     * @param z        The Z pivot point
     * @return The current pose stack builder
     **/
    public PoseStackBuilder rotateAround(Quaternionf rotation, double x, double y, double z)
    {
        parent.rotateAround(rotation, (float) x, (float) y, (float) z);
        return this;
    }

    /**
     * Rotates the current pose stack around a pivot point
     *
     * @param x1 The X amount to rotate
     * @param y1 The Y amount to rotate
     * @param z1 The Z amount to rotate
     * @param w  The W amount to rotate
     * @param x2 The X pivot point
     * @param y2 The Y pivot point
     * @param z2 The Z pivot point
     * @return The current pose stack builder
     **/
    public PoseStackBuilder rotateAround(double x1, double y1, double z1, double w, double x2, double y2, double z2)
    {
        parent.rotateAround(new Quaternionf(x1, y1, z1, w), (float) x2, (float) y2, (float) z2);
        return this;
    }

    /**
     * Rotates the current pose stack around a pivot point
     *
     * @param x1 The X amount to rotate
     * @param y1 The Y amount to rotate
     * @param z1 The Z amount to rotate
     * @param x2 The X pivot point
     * @param y2 The Y pivot point
     * @param z2 The Z pivot point
     * @return The current pose stack builder
     **/
    public PoseStackBuilder rotateAround(double x1, double y1, double z1, double x2, double y2, double z2)
    {
        parent.rotateAround(new Quaternionf(x1, y1, z1, 1), (float) x2, (float) y2, (float) z2);
        return this;
    }

    /**
     * Rotates the current pose stack around a pivot point
     *
     * @param rotation The XYZW rotation
     * @param pos      The pivot point
     * @return The current pose stack builder
     **/
    public PoseStackBuilder rotateAround(Double[] rotation, Double[] pos)
    {
        assertCorrectLength(rotation, 4);
        assertCorrectLength(pos, 3);
        parent.rotateAround(new Quaternionf(rotation[0], rotation[1], rotation[2], rotation[3]), pos[0].floatValue(), pos[1].floatValue(), pos[2].floatValue());
        return this;
    }

    /**
     * Rotates the current pose stack in all 3 directions simultaneously
     *
     * @param value The rotation value
     * @return A new quaternion
     **/
    private Quaternionf rotateXYZ(double value)
    {
        return new Quaternionf().rotateXYZ((float) value, (float) value, (float) value);
    }

    /**
     * Checks if an array is the correct length
     *
     * @param array        The array to check
     * @param wantedLength The wanted length of the array
     **/
    private <A> void assertCorrectLength(A[] array, int wantedLength)
    {
        if (array.length != wantedLength) {
            throw new IndexOutOfBoundsException(array.length);
        }
    }

    @Override
    public @NotNull Pose last()
    {
        return parent.last();
    }

    @Override
    public boolean clear()
    {
        return parent.clear();
    }

    @Override
    public boolean equals(Object object)
    {
        if (super.equals(object)) {
            return true;
        }
        if (!(object instanceof PoseStackBuilder)) {
            return false;
        }
        return parent.equals(object);
    }

    @Override
    public String toString()
    {
        return "[Current Stack]: " + parent.toString() + " [Current Pose]: " + parent.last() + " [Current Matrix]: " + parent.last().pose();
    }
}
