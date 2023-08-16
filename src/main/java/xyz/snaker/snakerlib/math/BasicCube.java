package xyz.snaker.snakerlib.math;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

/**
 * Created by SnakerBone on 20/06/2023
 **/
public class BasicCube
{
    /**
     * The current vertex builder
     **/
    private final VertexConsumer consumer;

    /**
     * The current pose stack
     **/
    private final PoseStack stack;

    /**
     * The width of this cube (16px)
     **/
    private final int x = 1;

    /**
     * The height of this cube (16px)
     **/
    private final int y = 1;

    /**
     * The depth of this cube (16px)
     **/
    private final int z = 1;

    public BasicCube(VertexConsumer consumer, PoseStack stack)
    {
        this.consumer = consumer;
        this.stack = stack;
    }

    public BasicCube(MultiBufferSource source, RenderType type, PoseStack stack)
    {
        this.consumer = source.getBuffer(type);
        this.stack = stack;
    }

    /**
     * Gets the current vertex builder
     *
     * @return The current vertex builder
     **/
    public VertexConsumer consumer()
    {
        return consumer;
    }

    /**
     * Gets the current pose stack
     *
     * @return The current pose stack
     **/
    public PoseStack stack()
    {
        return stack;
    }

    /**
     * Gets the current pose
     *
     * @return The current pose
     **/
    public PoseStack.Pose pose()
    {
        return stack.last();
    }

    /**
     * Gets the current pose stack as a Matrix4f
     *
     * @return The current pose stack as a Matrix4f
     **/
    public Matrix4f matrix()
    {
        return stack.last().pose();
    }

    /**
     * Creates a basic cube and draws it
     *
     * @param consumer The vertex builder
     * @param stack    The pose stack
     * @return A drawn basic cube
     **/
    public static BasicCube create(VertexConsumer consumer, PoseStack stack)
    {
        return new BasicCube(consumer, stack).draw();
    }

    /**
     * Draws all the sides of this basic cube
     *
     * @return The drawn basic cube
     **/
    public BasicCube draw()
    {
        draw(consumer, stack, x, y, z);
        return this;
    }

    /**
     * Draws all the sides of this basic cube
     *
     * @param consumer The vertex builder
     * @param stack    The pose stack
     * @param x        The X coords
     * @param y        The Y coords
     * @param z        The Z coords
     * @return The drawn basic cube
     **/
    private BasicCube draw(VertexConsumer consumer, PoseStack stack, int x, int y, int z)
    {
        draw(consumer, stack.last().pose(), x, y, z);
        return this;
    }

    /**
     * Draws all the sides of this basic cube
     *
     * @param consumer The vertex builder
     * @param matrix   The pose stack as a Matrix4f
     * @param x        The X coords
     * @param y        The Y coords
     * @param z        The Z coords
     * @return The drawn basic cube
     **/
    private BasicCube draw(VertexConsumer consumer, Matrix4f matrix, int x, int y, int z)
    {
        front(consumer, matrix, x, y, z);
        back(consumer, matrix, x, y, z);
        left(consumer, matrix, x, y, z);
        right(consumer, matrix, x, y, z);
        top(consumer, matrix, x, y, z);
        bottom(consumer, matrix, x, y, z);
        return this;
    }

    /**
     * Draws the front of this basic cube
     *
     * @param consumer The vertex builder
     * @param matrix   The pose stack as a Matrix4f
     * @param x        The X coords
     * @param y        The Y coords
     * @param z        The Z coords
     **/
    private void front(VertexConsumer consumer, Matrix4f matrix, int x, int y, int z)
    {
        vertex(consumer, matrix, sub(x), sub(y), z);
        vertex(consumer, matrix, x, sub(y), z);
        vertex(consumer, matrix, x, y, z);
        vertex(consumer, matrix, sub(x), y, z);
    }

    /**
     * Draws the back of this basic cube
     *
     * @param consumer The vertex builder
     * @param matrix   The pose stack as a Matrix4f
     * @param x        The X coords
     * @param y        The Y coords
     * @param z        The Z coords
     **/
    private void back(VertexConsumer consumer, Matrix4f matrix, int x, int y, int z)
    {
        vertex(consumer, matrix, sub(x), sub(y), sub(z));
        vertex(consumer, matrix, x, sub(y), sub(z));
        vertex(consumer, matrix, x, y, sub(z));
        vertex(consumer, matrix, sub(x), y, sub(z));
    }

    /**
     * Draws the left side of this basic cube
     *
     * @param consumer The vertex builder
     * @param matrix   The pose stack as a Matrix4f
     * @param x        The X coords
     * @param y        The Y coords
     * @param z        The Z coords
     **/
    private void left(VertexConsumer consumer, Matrix4f matrix, int x, int y, int z)
    {
        vertex(consumer, matrix, sub(x), y, z);
        vertex(consumer, matrix, sub(x), y, sub(z));
        vertex(consumer, matrix, sub(x), sub(y), sub(z));
        vertex(consumer, matrix, sub(x), sub(y), z);
    }

    /**
     * Draws the right side of this basic cube
     *
     * @param consumer The vertex builder
     * @param matrix   The pose stack as a Matrix4f
     * @param x        The X coords
     * @param y        The Y coords
     * @param z        The Z coords
     **/
    private void right(VertexConsumer consumer, Matrix4f matrix, int x, int y, int z)
    {
        vertex(consumer, matrix, x, y, z);
        vertex(consumer, matrix, x, y, sub(z));
        vertex(consumer, matrix, x, sub(y), sub(z));
        vertex(consumer, matrix, x, sub(y), z);
    }

    /**
     * Draws the top of this basic cube
     *
     * @param consumer The vertex builder
     * @param matrix   The pose stack as a Matrix4f
     * @param x        The X coords
     * @param y        The Y coords
     * @param z        The Z coords
     **/
    private void top(VertexConsumer consumer, Matrix4f matrix, int x, int y, int z)
    {
        vertex(consumer, matrix, sub(x), y, z);
        vertex(consumer, matrix, sub(x), y, sub(z));
        vertex(consumer, matrix, x, y, sub(z));
        vertex(consumer, matrix, x, y, z);
    }

    /**
     * Draws the bottom of this basic cube
     *
     * @param consumer The vertex builder
     * @param matrix   The pose stack as a Matrix4f
     * @param x        The X coords
     * @param y        The Y coords
     * @param z        The Z coords
     **/
    private void bottom(VertexConsumer consumer, Matrix4f matrix, int x, int y, int z)
    {
        vertex(consumer, matrix, sub(x), sub(y), z);
        vertex(consumer, matrix, sub(x), sub(y), sub(z));
        vertex(consumer, matrix, x, sub(y), sub(z));
        vertex(consumer, matrix, x, sub(y), z);
    }

    /**
     * Draws a vertex
     *
     * @param consumer The vertex builder
     * @param matrix   The pose stack as a Matrix4f
     * @param x        The X coords
     * @param y        The Y coords
     * @param z        The Z coords
     **/
    private void vertex(VertexConsumer consumer, Matrix4f matrix, int x, int y, int z)
    {
        consumer.vertex(matrix, x, y, z).color(255, 255, 255, 255).uv(0, 0).uv2(0).normal(1, 0, 0).endVertex();
    }

    private int sub(int value)
    {
        return value == 1 ? 0 : 1;
    }
}