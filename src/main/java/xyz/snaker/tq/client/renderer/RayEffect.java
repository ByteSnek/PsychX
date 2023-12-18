package xyz.snaker.tq.client.renderer;

import xyz.snaker.snakerlib.SnakerLib;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import org.joml.Matrix4f;

/**
 * Created by SnakerBone on 20/02/2023
 **/
public class RayEffect
{
    private final int colour;
    private final int minSize;
    private final int maxSize;
    private final int rayCount;

    private RayEffect(int colour, int minSize, int maxSize, int rayCount)
    {
        this.colour = colour;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.rayCount = rayCount;
    }

    public void render(PoseStack stack, MultiBufferSource source)
    {
        RandomSource random = RandomSource.create(432L);
        RenderType type = RenderType.lightning();
        VertexConsumer consumer = source.getBuffer(type);

        float time = SnakerLib.getClientTickCount() / 200F;

        stack.pushPose();

        for (int i = 0; i < rayCount; i++) {
            stack.pushPose();

            stack.mulPose(Axis.XP.rotationDegrees(random.nextFloat() * 360));
            stack.mulPose(Axis.YP.rotationDegrees(random.nextFloat() * 360));
            stack.mulPose(Axis.ZP.rotationDegrees(random.nextFloat() * 360));
            stack.mulPose(Axis.XP.rotationDegrees(random.nextFloat() * 360));
            stack.mulPose(Axis.YP.rotationDegrees(random.nextFloat() * 360));
            stack.mulPose(Axis.ZP.rotationDegrees(random.nextFloat() * 360 + time * 360));

            Matrix4f matrix = stack.last().pose();

            float sizeY = random.nextFloat() * 20 + 5;
            float sizeXZ = random.nextFloat() * 2 + 1;

            float rawSize = 30F / (Math.min(minSize, 10F * maxSize) / 10F);

            sizeY /= rawSize;
            sizeXZ /= rawSize;

            vertex(consumer, matrix, 0, 0, 0, colour);
            vertex(consumer, matrix, 0, 0, 0, colour);
            vertex(consumer, matrix, -0.7F * sizeXZ, sizeY, -0.5F * sizeXZ, colour);
            vertex(consumer, matrix, 0.7F * sizeXZ, sizeY, -0.5F * sizeXZ, colour);

            vertex(consumer, matrix, 0, 0, 0, colour);
            vertex(consumer, matrix, 0, 0, 0, colour);
            vertex(consumer, matrix, 0.7F * sizeXZ, sizeY, -0.5F * sizeXZ, colour);
            vertex(consumer, matrix, 0, sizeY, sizeXZ, colour);

            vertex(consumer, matrix, 0, 0, 0, colour);
            vertex(consumer, matrix, 0, 0, 0, colour);
            vertex(consumer, matrix, 0, sizeY, sizeXZ, colour);
            vertex(consumer, matrix, -0.7F * sizeXZ, sizeY, -0.5F * sizeXZ, colour);

            stack.popPose();
        }

        stack.popPose();

        bind(consumer, RenderType.lightning());
    }

    private void vertex(VertexConsumer consumer, Matrix4f matrix, float x, float y, float z, int colour)
    {
        int red = FastColor.ARGB32.red(colour);
        int green = FastColor.ARGB32.green(colour);
        int blue = FastColor.ARGB32.blue(colour);
        int alpha = FastColor.ARGB32.alpha(colour);

        consumer.vertex(matrix, x, y, z).color(red, green, blue, alpha).endVertex();
    }

    private void bind(VertexConsumer consumer, RenderType type)
    {
        if (consumer instanceof BufferBuilder builder) {
            type.end(builder, RenderSystem.getVertexSorting());
            builder.begin(type.mode(), type.format());
        }
    }

    public static class Builder
    {
        private int colour;
        private int minSize;
        private int maxSize;
        private int rayCount;

        public Builder()
        {
            this.colour = 0xFFFFFFFF;
            this.minSize = 16;
            this.maxSize = 64;
            this.rayCount = 64;
        }

        public Builder setColour(int colour)
        {
            this.colour = colour;
            return this;
        }

        public Builder setMinSize(int minSize)
        {
            this.minSize = minSize;
            return this;
        }

        public Builder setMaxSize(int maxSize)
        {
            this.maxSize = maxSize;
            return this;
        }

        public Builder setRayCount(int rayCount)
        {
            this.rayCount = rayCount;
            return this;
        }

        public RayEffect build()
        {
            return new RayEffect(colour, minSize, maxSize, rayCount);
        }
    }
}
