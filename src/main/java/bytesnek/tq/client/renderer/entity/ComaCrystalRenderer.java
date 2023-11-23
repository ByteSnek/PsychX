package bytesnek.tq.client.renderer.entity;

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

import bytesnek.hiss.math.Maths;
import bytesnek.snakerlib.math.Tensor;
import bytesnek.snakerlib.resources.ResourceReference;
import bytesnek.tq.client.Shaders;
import bytesnek.tq.client.model.entity.ComaCrystalModel;
import bytesnek.tq.client.renderer.type.EntityRenderType;
import bytesnek.tq.level.entity.crystal.ComaCrystal;

/**
 * Created by SnakerBone on 14/11/2023
 **/
public class ComaCrystalRenderer extends EntityRenderer<ComaCrystal>
{
    private static final ResourceLocation CRYSTAL_LOCATION = new ResourceReference("textures/entity/coma_crystal/coma_crystal.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(CRYSTAL_LOCATION);
    private static final float SIN_45 = (float) Math.sin((Math.PI / 4D));
    private final ComaCrystalModel model;

    public ComaCrystalRenderer(EntityRendererProvider.Context context)
    {
        super(context);
        this.shadowRadius = 0.5F;
        this.model = new ComaCrystalModel(context.bakeLayer(ComaCrystalModel.LAYER_LOCATION));
    }

    @Override
    public void render(@NotNull ComaCrystal crystal, float entityYaw, float partialTicks, @NotNull PoseStack stack, MultiBufferSource source, int packedLight)
    {
        Tensor tensor = new Tensor(stack);

        float bobY = getY(crystal, partialTicks);
        float animTime = ((float) crystal.time + partialTicks) * 3.0F;
        int noOverlay = OverlayTexture.NO_OVERLAY;
        float uvOffset = (crystal.tickCount + partialTicks) * 0.01F % 1;

        tensor.pushPose();
        tensor.scale(2.0F, 2.0F, 2.0F);
        tensor.translate(0.0F, -0.5F, 0.0F);

        if (crystal.showsBottom()) {
            model.base.render(tensor.getStack(), source.getBuffer(RENDER_TYPE), packedLight, noOverlay);
        }

        setupPulseShader(crystal, new Vector3f(1, 0, 1), partialTicks);

        VertexConsumer pulse = source.getBuffer(EntityRenderType.PULSE.get());

        if (crystal.showsBottom()) {
            model.base.render(tensor.getStack(), pulse, packedLight, noOverlay);
        }

//        Vector4f baseColour = new Vector4f(crystal.getDepletingTicks() != 0 ? ((60F - (float) crystal.getDepletingTicks()) / 60F) % 1 : 1);
//
//        tensor.pushPose();
//
//        tensor.mulPose(Axis.YP.rotationDegrees(animTime));
//        tensor.translate(0.0F, 1.5F + bobY / 2.0F, 0.0F);
//        tensor.mulPose((new Quaternionf()).setAngleAxis(((float) Math.PI / 3F), SIN_45, 0.0F, SIN_45));
//        model.glass.render(tensor.getStack(), source.getBuffer(RENDER_TYPE), packedLight, noOverlay, baseColour.x, baseColour.y, baseColour.z, baseColour.w);
//        tensor.scale(0.875F, 0.875F, 0.875F);
//        tensor.mulPose((new Quaternionf()).setAngleAxis(((float) Math.PI / 3F), SIN_45, 0.0F, SIN_45));
//        tensor.mulPose(Axis.YP.rotationDegrees(animTime));
//        model.glass.render(tensor.getStack(), source.getBuffer(RENDER_TYPE), packedLight, noOverlay, baseColour.x, baseColour.y, baseColour.z, baseColour.w);
//        tensor.scale(0.875F, 0.875F, 0.875F);
//        tensor.mulPose((new Quaternionf()).setAngleAxis(((float) Math.PI / 3F), SIN_45, 0.0F, SIN_45));
//        tensor.mulPose(Axis.YP.rotationDegrees(animTime));
//        model.cube.render(tensor.getStack(), source.getBuffer(RENDER_TYPE), packedLight, noOverlay, baseColour.x, baseColour.y, baseColour.z, baseColour.w);
//
//        tensor.popPose();

        VertexConsumer consumer = source.getBuffer(RenderType.energySwirl(new ResourceReference("textures/entity/coma_crystal/coma_crystal_overlay.png"), uvOffset, uvOffset));
        Vector4f colour = new Vector4f(crystal.getDepletingTicks() != 0 ? ((60F - (float) crystal.getDepletingTicks()) / 60F) % 1 : -bobY / 2F);

        tensor.mulPose(Axis.YP.rotationDegrees(animTime));
        tensor.translate(0.0F, 1.5F + bobY / 2.0F, 0.0F);
        tensor.mulPose((new Quaternionf()).setAngleAxis(((float) Math.PI / 3F), SIN_45, 0.0F, SIN_45));
        model.glass.render(tensor.getStack(), consumer, packedLight, noOverlay, colour.x, colour.y, colour.z, colour.w);
        tensor.scale(0.875F, 0.875F, 0.875F);
        tensor.mulPose((new Quaternionf()).setAngleAxis(((float) Math.PI / 3F), SIN_45, 0.0F, SIN_45));
        tensor.mulPose(Axis.YP.rotationDegrees(animTime));
        model.glass.render(tensor.getStack(), consumer, packedLight, noOverlay, colour.x, colour.y, colour.z, colour.w);
        tensor.scale(0.875F, 0.875F, 0.875F);
        tensor.mulPose((new Quaternionf()).setAngleAxis(((float) Math.PI / 3F), SIN_45, 0.0F, SIN_45));
        tensor.mulPose(Axis.YP.rotationDegrees(animTime));
        model.cube.render(tensor.getStack(), consumer, packedLight, noOverlay, colour.x, colour.y, colour.z, colour.w);

        tensor.popPose();
        
        renderBeams(crystal, tensor.getStack(), source, packedLight, partialTicks);

        super.render(crystal, entityYaw, partialTicks, stack, source, packedLight);
    }

    public void setupPulseShader(ComaCrystal crystal, Vector3f colour, float partialTicks)
    {
        Shaders.getPulse().enqueueTask(() ->
        {
            float yBob = getY(crystal, partialTicks);
            float alpha = Maths.tan(-yBob / 1.775F);
            float depleteAlpha = (60F - (float) crystal.getDepletingTicks()) / 60F;

            Shaders.getPulseColour().set(colour);
            Shaders.getPulseAlpha().set(crystal.getDepletingTicks() != 0 ? depleteAlpha % 1 : alpha);
        });
    }

    public void renderBeams(ComaCrystal crystal, PoseStack stack, MultiBufferSource source, int packedLight, float partialTicks)
    {
        float yBob = getY(crystal, partialTicks);
        BlockPos target = crystal.getBeamTarget();

        if (target != null) {
            float posX = (float) target.getX() + 0.5F;
            float posY = (float) target.getY() + 0.5F;
            float posZ = (float) target.getZ() + 0.5F;

            float distX = posX - (float) crystal.getX();
            float distY = posY - (float) crystal.getY();
            float distZ = posZ - (float) crystal.getZ();

            stack.translate(distX, distY, distZ);

            UtterflyRenderer.renderCrystalBeams(-distX, -distY + yBob, -distZ, partialTicks, crystal.time, stack, source, packedLight);
        }
    }

    public float getY(ComaCrystal crystal, float partialTick)
    {
        float time = (float) crystal.time + partialTick;
        float animTime = Mth.sin(time * 0.2F) / 2F + 0.5F;

        animTime = (animTime * animTime + animTime) * 0.4F;

        return animTime - 1.4F;
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull ComaCrystal crystal)
    {
        return CRYSTAL_LOCATION;
    }

    public boolean shouldRender(@NotNull ComaCrystal crystal, @NotNull Frustum frustum, double camX, double camY, double camZ)
    {
        return super.shouldRender(crystal, frustum, camX, camY, camZ) || crystal.getBeamTarget() != null;
    }
}
