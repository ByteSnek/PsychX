package xyz.snaker.tq.client.renderer.entity;

import xyz.snaker.snakerlib.math.Tensor;
import xyz.snaker.snakerlib.resources.ResourceReference;
import xyz.snaker.tq.client.layer.UtterflyLayer;
import xyz.snaker.tq.client.model.entity.UtterflyModel;
import xyz.snaker.tq.client.renderer.RayEffect;
import xyz.snaker.tq.level.entity.utterfly.Utterfly;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

/**
 * Created by SnakerBone on 4/01/2023
 **/
public class UtterflyRenderer extends MobRenderer<Utterfly, UtterflyModel>
{
    private static final RenderType BEAM = RenderType.entitySmoothCutout(EnderDragonRenderer.CRYSTAL_BEAM_LOCATION);

    public UtterflyRenderer(EntityRendererProvider.Context context)
    {
        super(context, new UtterflyModel(context.bakeLayer(UtterflyModel.LAYER_LOCATION)), 0.5F);
        addLayer(new UtterflyLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Utterfly utterfly)
    {
        switch (utterfly.getPhase()) {
            case 1 -> {
                return new ResourceReference("textures/entity/boss/utterfly0.png");
            }
            case 2 -> {
                return new ResourceReference("textures/entity/boss/utterfly1.png");
            }
            case 3, 4 -> {
                return new ResourceReference("textures/entity/boss/utterfly2.png");
            }
            default -> {
                return new ResourceReference("textures/entity/creature/flutterfly.png");
            }
        }
    }

    @Override
    public void render(@NotNull Utterfly utterfly, float entityYaw, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight)
    {
        Tensor tensor = new Tensor(stack);
        RayEffect.Builder builder = new RayEffect.Builder();

        double scale;

        int phase = utterfly.getPhase();

        switch (phase) {
            case 1 -> {
                scale = 4;
                builder.setColour(0xFFFFE800);
            }
            case 2 -> {
                scale = 6;
                builder.setColour(0xFFFF8300);
            }
            case 3 -> {
                scale = 8;
                builder.setColour(0xFFFF0000);
            }
            default -> {
                scale = 1;
                builder.setColour(0xFF000000);
            }
        }

        tensor.scale(scale);

        if (utterfly.getCharging()) {
            builder.build().render(stack, source);
        }

        super.render(utterfly, entityYaw, partialTicks, stack, source, packedLight);
    }

    public static void renderCrystalBeams(float x, float y, float z, float partialTicks, int tickCount, PoseStack stack, MultiBufferSource source, int packedLight)
    {
        float xz = Mth.sqrt(x * x + z * z);
        float xyz = Mth.sqrt(x * x + y * y + z * z);

        stack.pushPose();
        stack.translate(0F, 2F, 0F);
        stack.mulPose(Axis.YP.rotation((float) (-Math.atan2(z, x)) - ((float) Math.PI / 2F)));
        stack.mulPose(Axis.XP.rotation((float) (-Math.atan2(xz, y)) - ((float) Math.PI / 2F)));

        VertexConsumer consumer = source.getBuffer(BEAM);

        float startV = 0F - ((float) tickCount + partialTicks) * 0.01F;
        float endV = xyz / 32F - ((float) tickCount + partialTicks) * 0.01F;

        float startX = 0F;
        float startY = 0.75F;
        float startZ = 0F;

        PoseStack.Pose pose = stack.last();

        Matrix4f matrix = pose.pose();
        Matrix3f normals = pose.normal();

        for (int i = 1; i <= 8; i++) {
            float endX = Mth.sin((float) i * ((float) Math.PI * 2F) / 8F) * 0.75F;
            float endY = Mth.cos((float) i * ((float) Math.PI * 2F) / 8F) * 0.75F;
            float endZ = (float) i / 8F;

            consumer.vertex(matrix, startX * 0.2F, startY * 0.2F, 0F)
                    .color(0, 0, 0, 255)
                    .uv(startZ, startV)
                    .overlayCoords(OverlayTexture.NO_OVERLAY)
                    .uv2(packedLight)
                    .normal(normals, 0F, -1F, 0F)
                    .endVertex();
            consumer.vertex(matrix, startX, startY, xyz)
                    .color(255, 255, 255, 255)
                    .uv(startZ, endV)
                    .overlayCoords(OverlayTexture.NO_OVERLAY)
                    .uv2(packedLight)
                    .normal(normals, 0F, -1F, 0F)
                    .endVertex();
            consumer.vertex(matrix, endX, endY, xyz)
                    .color(255, 255, 255, 255)
                    .uv(endZ, endV).overlayCoords(OverlayTexture.NO_OVERLAY)
                    .uv2(packedLight).normal(normals, 0F, -1F, 0F)
                    .endVertex();
            consumer.vertex(matrix, endX * 0.2F, endY * 0.2F, 0F)
                    .color(0, 0, 0, 255)
                    .uv(endZ, startV)
                    .overlayCoords(OverlayTexture.NO_OVERLAY)
                    .uv2(packedLight)
                    .normal(normals, 0F, -1F, 0F)
                    .endVertex();

            startX = endX;
            startY = endY;
            startZ = endZ;
        }

        stack.popPose();
    }
}
