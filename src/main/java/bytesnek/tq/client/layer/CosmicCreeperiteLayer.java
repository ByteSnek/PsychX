package bytesnek.tq.client.layer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import org.jetbrains.annotations.NotNull;

import bytesnek.snakerlib.resources.ResourceReference;
import bytesnek.snakerlib.utility.Rendering;
import bytesnek.tq.client.model.entity.CosmicCreeperiteModel;
import bytesnek.tq.level.entity.mob.CosmicCreeperite;

/**
 * Created by SnakerBone on 1/06/2023
 **/
public class CosmicCreeperiteLayer extends EnergySwirlLayer<CosmicCreeperite, CosmicCreeperiteModel>
{
    private final CosmicCreeperiteModel model;
    private final ResourceLocation texture;

    public CosmicCreeperiteLayer(RenderLayerParent<CosmicCreeperite, CosmicCreeperiteModel> parent, EntityModelSet set)
    {
        super(parent);
        this.model = new CosmicCreeperiteModel(set.bakeLayer(CosmicCreeperiteModel.LAYER_LOCATION));
        this.texture = new ResourceReference("textures/entity/mob/cosmic_creeperite/cosmic_creeperite_layer.png");
    }

    @Override
    protected float xOffset(float u)
    {
        return u * 0.01F;
    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation()
    {
        return texture;
    }

    @Override
    protected @NotNull EntityModel<CosmicCreeperite> model()
    {
        return model;
    }

    @Override
    public void render(@NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight, @NotNull CosmicCreeperite creeper, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        float tick = creeper.tickCount + partialTicks;
        VertexConsumer consumer = source.getBuffer(RenderType.energySwirl(texture, xOffset(tick) % 1, tick * 0.01F % 1));
        getParentModel().copyPropertiesTo(model);
        model.prepareMobModel(creeper, limbSwing, limbSwingAmount, partialTicks);
        model.setupAnim(creeper, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        model.renderToBuffer(stack, consumer, packedLight, Rendering.packOverlay(creeper), 0.5F, 0.5F, 0.5F, 1.0F);
    }

    public static class Eyes extends EyesLayer<CosmicCreeperite, CosmicCreeperiteModel>
    {
        public Eyes(RenderLayerParent<CosmicCreeperite, CosmicCreeperiteModel> parent)
        {
            super(parent);
        }

        @Override
        public @NotNull RenderType renderType()
        {
            return RenderType.eyes(new ResourceReference("textures/entity/mob/cosmic_creeperite/cosmic_creeperite_eyes_layer.png"));
        }

        @Override
        public void render(@NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight, @NotNull CosmicCreeperite creeperite, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
        {
            super.render(stack, source, LightTexture.FULL_BRIGHT, creeperite, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
        }
    }
}
