package snaker.snakerlib.math;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.joml.Matrix4f;

/**
 * Created by SnakerBone on 20/06/2023
 **/
public class BasicCube
{
    private final VertexConsumer consumer;
    private final PoseStack stack;

    private final long x = 1;
    private final long y = 1;
    private final long z = 1;

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

    public VertexConsumer consumer()
    {
        return consumer;
    }

    public PoseStack stack()
    {
        return stack;
    }

    public PoseStack.Pose pose()
    {
        return stack.last();
    }

    public Matrix4f matrix()
    {
        return stack.last().pose();
    }

    public static BasicCube create(VertexConsumer consumer, PoseStack stack)
    {
        return new BasicCube(consumer, stack).draw();
    }

    public BasicCube draw()
    {
        draw(consumer, stack, x, y, z);
        return this;
    }

    private BasicCube draw(VertexConsumer consumer, PoseStack stack, long x, long y, long z)
    {
        draw(consumer, stack.last().pose(), x, y, z);
        return this;
    }

    private BasicCube draw(VertexConsumer consumer, Matrix4f pose, long x, long y, long z)
    {
        front(consumer, pose, x, y, z);
        back(consumer, pose, x, y, z);
        left(consumer, pose, x, y, z);
        right(consumer, pose, x, y, z);
        top(consumer, pose, x, y, z);
        bottom(consumer, pose, x, y, z);
        return this;
    }

    private void front(VertexConsumer consumer, Matrix4f pose, long x, long y, long z)
    {
        vertex(consumer, pose, sub(x), sub(y), z);
        vertex(consumer, pose, x, sub(y), z);
        vertex(consumer, pose, x, y, z);
        vertex(consumer, pose, sub(x), y, z);
    }

    private void back(VertexConsumer consumer, Matrix4f pose, long x, long y, long z)
    {
        vertex(consumer, pose, sub(x), sub(y), sub(z));
        vertex(consumer, pose, x, sub(y), sub(z));
        vertex(consumer, pose, x, y, sub(z));
        vertex(consumer, pose, sub(x), y, sub(z));
    }

    private void left(VertexConsumer consumer, Matrix4f pose, long x, long y, long z)
    {
        vertex(consumer, pose, sub(x), y, z);
        vertex(consumer, pose, sub(x), y, sub(z));
        vertex(consumer, pose, sub(x), sub(y), sub(z));
        vertex(consumer, pose, sub(x), sub(y), z);
    }

    private void right(VertexConsumer consumer, Matrix4f pose, long x, long y, long z)
    {
        vertex(consumer, pose, x, y, z);
        vertex(consumer, pose, x, y, sub(z));
        vertex(consumer, pose, x, sub(y), sub(z));
        vertex(consumer, pose, x, sub(y), z);
    }

    private void top(VertexConsumer consumer, Matrix4f pose, long x, long y, long z)
    {
        vertex(consumer, pose, sub(x), y, z);
        vertex(consumer, pose, sub(x), y, sub(z));
        vertex(consumer, pose, x, y, sub(z));
        vertex(consumer, pose, x, y, z);
    }

    private void bottom(VertexConsumer consumer, Matrix4f pose, long x, long y, long z)
    {
        vertex(consumer, pose, sub(x), sub(y), z);
        vertex(consumer, pose, sub(x), sub(y), sub(z));
        vertex(consumer, pose, x, sub(y), sub(z));
        vertex(consumer, pose, x, sub(y), z);
    }

    private void vertex(VertexConsumer consumer, Matrix4f matrix, long x, long y, long z)
    {
        try {
            consumer.vertex(matrix, x, y, z).color(255, 255, 255, 255).uv(0, 0).uv2(0).normal(1, 0, 0).endVertex();
        } catch (Exception e) {
            //SnakerLib.LOGGER.logError(e);
        }
    }

    private long sub(long value)
    {
        return value == 1 ? 0 : 1;
    }
}