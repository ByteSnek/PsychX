package bytesnek.tq.client.renderer.entity;

import xyz.snaker.snakerlib.math.Maths;
import xyz.snaker.snakerlib.math.Tensor;
import xyz.snaker.snakerlib.resources.ResourceReference;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

import bytesnek.tq.client.Shaders;
import bytesnek.tq.client.model.entity.ComaCrystalModel;
import bytesnek.tq.client.renderer.type.EntityRenderType;
import bytesnek.tq.level.entity.ComaCrystal;

/**
 * Created by SnakerBone on 14/11/2023
 **/
public class ComaCrystalRenderer extends EntityRenderer<ComaCrystal>
{
    private final ComaCrystalModel model;

    public ComaCrystalRenderer(EntityRendererProvider.Context context)
    {
        super(context);
        this.shadowRadius = 0.5F;
        this.model = new ComaCrystalModel(context.bakeLayer(ComaCrystalModel.LAYER_LOCATION));
    }

    public void render(@NotNull ComaCrystal crystal, float entityYaw, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight)
    {
        Tensor tensor = new Tensor(stack);

        float yBob = getY(crystal, partialTicks);
        float animTime = (float) crystal.time + partialTicks * 3F;
        float angleX = Maths.PI / 3F;

        RenderType entityCutoutNoCull = RenderType.entityCutoutNoCull(new ResourceReference("textures/entity/coma_crystal/coma_crystal.png"));
        VertexConsumer consumer = source.getBuffer(entityCutoutNoCull);

        tensor.pushPose();
        tensor.scale(2, 2, 2);
        tensor.translate(0, -0.5, 0);

        int overlayTexture = OverlayTexture.NO_OVERLAY;

        if (crystal.showsBottom()) {
            model.base.render(tensor.getStack(), consumer, packedLight, overlayTexture);
        }

        tensor.popPose();

        VertexConsumer vc = source.getBuffer(EntityRenderType.PULSE.get());
        Vector3f baseColour = new Vector3f(1, 0, 1);
        float alpha = Maths.tan(-yBob / 1.775F);

        tensor.pushPose();
        tensor.scale(2, 2, 2);
        tensor.translate(0, -0.5, 0);

        Shaders.getPulse().enqueueTask(() ->
        {
            Shaders.getPulseColour().set(baseColour);
            Shaders.getPulseAlpha().set(alpha);
        });

        if (crystal.showsBottom()) {
            model.base.render(tensor.getStack(), vc, packedLight, overlayTexture);
        }

        tensor.popPose();

        if (true) {
            float uvOffset = crystal.tickCount * 0.01F % 1;
            float angle = Maths.sin(Maths.PI / 4);

            VertexConsumer overlay = source.getBuffer(RenderType.energySwirl(new ResourceReference("textures/entity/coma_crystal/coma_crystal_overlay.png"), uvOffset, uvOffset));
            Vector4f colour = new Vector4f(-yBob / 2F);

            tensor.pushPose();
            tensor.scale(2, 2, 2);
            tensor.translate(0, -0.5, 0);

            if (crystal.showsBottom() && false) {
                model.base.render(stack, overlay, packedLight, overlayTexture, colour.x, colour.y, colour.z, colour.w);
            }

            tensor.mulPose(Axis.YP.rotationDegrees(animTime));
            tensor.translate(0, 1.5 + yBob / 2, 0);
            tensor.mulPose((new Quaternionf()).setAngleAxis(angleX, angle, 0F, angle));

            model.glass.render(tensor.getStack(), overlay, packedLight, overlayTexture, colour.x, colour.y, colour.z, colour.w);

            tensor.scale(0.875, 0.875, 0.875);
            tensor.mulPose((new Quaternionf()).setAngleAxis(angleX, angle, 0F, angle));
            tensor.mulPose(Axis.YP.rotationDegrees(animTime));

            model.glass.render(tensor.getStack(), overlay, packedLight, overlayTexture, colour.x, colour.y, colour.z, colour.w);

            tensor.scale(0.875, 0.875, 0.875);
            tensor.mulPose((new Quaternionf()).setAngleAxis(angleX, angle, 0F, angle));
            tensor.mulPose(Axis.YP.rotationDegrees(animTime));

            model.cube.render(tensor.getStack(), overlay, packedLight, overlayTexture, colour.x, colour.y, colour.z, colour.w);

            tensor.popPose();
        }

        BlockPos target = crystal.getBeamTarget();

        if (target != null) {
            float posX = (float) target.getX() + 0.5F;
            float posY = (float) target.getY() + 0.5F;
            float posZ = (float) target.getZ() + 0.5F;

            float distX = posX - (float) crystal.getX();
            float distY = posY - (float) crystal.getY();
            float distZ = posZ - (float) crystal.getZ();

            tensor.translate(distX, distY, distZ);

            UtterflyRenderer.renderCrystalBeams(-distX, -distY + yBob, -distZ, partialTicks, crystal.time, tensor.getStack(), source, packedLight);
        }

        super.render(crystal, entityYaw, partialTicks, tensor.getStack(), source, packedLight);
    }

    public static float getY(ComaCrystal crystal, float partialTick)
    {
        float time = (float) crystal.time + partialTick;
        float animTime = Mth.sin(time * 0.2F) / 2F + 0.5F;

        animTime = (animTime * animTime + animTime) * 0.4F;

        return animTime - 1.4F;
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull ComaCrystal crystal)
    {
        return new ResourceReference("textures/entity/coma_crystal/coma_crystal.png");
    }

    public boolean shouldRender(@NotNull ComaCrystal crystal, @NotNull Frustum frustum, double camX, double camY, double camZ)
    {
        return super.shouldRender(crystal, frustum, camX, camY, camZ) || crystal.getBeamTarget() != null;
    }
}
