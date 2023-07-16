package snaker.snakerlib.math;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.joml.*;

/**
 * Created by SnakerBone on 30/03/2023
 * <p>
 * A simple {@link PoseStack} builder that adds extra functions and paramater overloads for optimization and such
 * <p>
 * All functions are called directly to the parent stack while also returning the current ChainedPoseStack instance. Similar to that of a builder
 **/
public class PoseStackBuilder extends PoseStack
{
    private volatile boolean funny;
    private final PoseStack parent;

    public PoseStackBuilder(PoseStack parent)
    {
        this.parent = parent;
        this.funny = true;
    }

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
    public void scale(float pX, float pY, float pZ)
    {
        parent.scale(pX, pY, pZ);
    }

    public PoseStackBuilder scale(double x, double y, double z)
    {
        parent.scale((float) x, (float) y, (float) z);
        return this;
    }

    public PoseStackBuilder scale(double value)
    {
        parent.scale((float) value, (float) value, (float) value);
        return this;
    }

    public PoseStackBuilder scale(Double[] scale)
    {
        assertCorrectLength(scale, 3);
        parent.scale(scale[0].floatValue(), scale[1].floatValue(), scale[2].floatValue());
        return this;
    }

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

    public PoseStackBuilder translate(double translation)
    {
        parent.translate(translation, translation, translation);
        return this;
    }

    public PoseStackBuilder translate(double[] translation)
    {
        parent.translate(translation[0], translation[1], translation[2]);
        return this;
    }

    public PoseStackBuilder translate(Vector3f translation)
    {
        parent.translate(translation.x, translation.y, translation.z);
        return this;
    }

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

    public PoseStackBuilder rotate(Quaternionf rotation)
    {
        parent.mulPose(rotation);
        return this;
    }

    public PoseStackBuilder rotate(double x, double y, double z)
    {
        rotate(new Quaternionf(x, y, z, 1));
        return this;
    }

    public PoseStackBuilder rotate(double x, double y, double z, double w)
    {
        rotate(new Quaternionf(x, y, z, w));
        return this;
    }

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

    public PoseStackBuilder rotateAround(Quaternionf rotation, Vector3f pos)
    {
        parent.rotateAround(rotation, pos.x, pos.y, pos.z);
        return this;
    }

    public PoseStackBuilder rotateAround(Vector4f rotation, Vector3f pos)
    {
        parent.rotateAround(new Quaternionf(rotation.x, rotation.y, rotation.z, rotation.w), pos.x, pos.y, pos.z);
        return this;
    }

    public PoseStackBuilder rotateAround(Quaternionf rotation, double x, double y, double z)
    {
        parent.rotateAround(rotation, (float) x, (float) y, (float) z);
        return this;
    }

    public PoseStackBuilder rotateAround(double x1, double y1, double z1, double w, double x2, double y2, double z2)
    {
        parent.rotateAround(new Quaternionf(x1, y1, z1, w), (float) x2, (float) y2, (float) z2);
        return this;
    }

    public PoseStackBuilder rotateAround(double x1, double y1, double z1, double x2, double y2, double z2)
    {
        parent.rotateAround(new Quaternionf(x1, y1, z1, 1), (float) x2, (float) y2, (float) z2);
        return this;
    }

    public PoseStackBuilder rotateAround(Double[] rotation, Double[] pos)
    {
        assertCorrectLength(rotation, 4);
        assertCorrectLength(pos, 3);
        parent.rotateAround(new Quaternionf(rotation[0], rotation[1], rotation[2], rotation[3]), pos[0].floatValue(), pos[1].floatValue(), pos[2].floatValue());
        return this;
    }

    private Quaternionf rotateXYZ(double value)
    {
        return new Quaternionf().rotateXYZ((float) value, (float) value, (float) value);
    }

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
