package xyz.snaker.tq.client.fx;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.RandomSource;
import org.joml.Matrix4f;
import xyz.snaker.snakerlib.SnakerLib;

import java.awt.*;

/**
 * Created by SnakerBone on 20/02/2023
 **/
public class RayFX
{
    public static void create(PoseStack stack, MultiBufferSource source, Color color, int min, float max, int amount, double offsetX, double offsetY, double offsetZ)
    {
        RandomSource random = RandomSource.create(432L);
        VertexConsumer consumer = source.getBuffer(RenderType.lightning());

        float time = SnakerLib.getClientTickCount() / 200F;

        stack.pushPose();

        if (offsetX + offsetY + offsetZ != 0) {
            stack.translate(offsetX, offsetY, offsetZ);
        }

        for (int i = 0; i < amount; i++) {
            stack.pushPose();

            stack.mulPose(Axis.XP.rotationDegrees(random.nextFloat() * 360));
            stack.mulPose(Axis.YP.rotationDegrees(random.nextFloat() * 360));
            stack.mulPose(Axis.ZP.rotationDegrees(random.nextFloat() * 360));
            stack.mulPose(Axis.XP.rotationDegrees(random.nextFloat() * 360));
            stack.mulPose(Axis.YP.rotationDegrees(random.nextFloat() * 360));
            stack.mulPose(Axis.ZP.rotationDegrees(random.nextFloat() * 360 + time * 360));

            Matrix4f pose = stack.last().pose();

            float uLerp = random.nextFloat() * 20 + 5;
            float vLerp = random.nextFloat() * 2 + 1;

            float mul = 30 / (Math.min(min, 10 * max) / 10);

            uLerp /= mul;
            vLerp /= mul;

            consumer.vertex(pose, 0, 0, 0).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
            consumer.vertex(pose, 0, 0, 0).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
            consumer.vertex(pose, -0.7F * vLerp, uLerp, -0.5F * vLerp).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
            consumer.vertex(pose, 0.7F * vLerp, uLerp, -0.5F * vLerp).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
            consumer.vertex(pose, 0, 0, 0).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
            consumer.vertex(pose, 0, 0, 0).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
            consumer.vertex(pose, 0.7F * vLerp, uLerp, -0.5F * vLerp).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
            consumer.vertex(pose, 0, uLerp, vLerp).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
            consumer.vertex(pose, 0, 0, 0).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
            consumer.vertex(pose, 0, 0, 0).color(color.getRed(), color.getGreen(), color.getBlue(), 255).endVertex();
            consumer.vertex(pose, 0, uLerp, vLerp).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
            consumer.vertex(pose, -0.7F * vLerp, uLerp, -0.5F * vLerp).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();

            stack.popPose();
        }

        stack.popPose();

        bind(consumer, RenderType.lightning());
    }

    private static void bind(VertexConsumer vc, RenderType type)
    {
        if (vc instanceof BufferBuilder) {
            type.end((BufferBuilder) vc, RenderSystem.getVertexSorting());
            ((BufferBuilder) vc).begin(type.mode(), type.format());
        }
    }
}
