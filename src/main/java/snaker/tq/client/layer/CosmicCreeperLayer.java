package snaker.tq.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.resources.Identifier;
import snaker.snakerlib.utility.ShaderUtil;
import snaker.tq.client.model.entity.CosmicCreeperModel;
import snaker.tq.level.entity.mob.CosmicCreeper;

/**
 * Created by SnakerBone on 1/06/2023
 **/
public class CosmicCreeperLayer extends EnergySwirlLayer<CosmicCreeper, CosmicCreeperModel>
{
    private final CosmicCreeperModel model;
    private final ResourceLocation texture;

    public CosmicCreeperLayer(RenderLayerParent<CosmicCreeper, CosmicCreeperModel> parent, EntityModelSet set)
    {
        super(parent);
        this.model = new CosmicCreeperModel(set.bakeLayer(CosmicCreeperModel.LAYER_LOCATION));
        this.texture = new Identifier("textures/entity/mob/cosmic_creeper/cosmic_creeper_layer.png");
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
    protected @NotNull EntityModel<CosmicCreeper> model()
    {
        return model;
    }

    @Override
    public void render(@NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight, @NotNull CosmicCreeper creeper, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        float tick = creeper.tickCount + partialTicks;
        VertexConsumer consumer = source.getBuffer(RenderType.energySwirl(texture, xOffset(tick) % 1, tick * 0.01F % 1));
        getParentModel().copyPropertiesTo(model);
        model.prepareMobModel(creeper, limbSwing, limbSwingAmount, partialTicks);
        model.setupAnim(creeper, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        model.renderToBuffer(stack, consumer, packedLight, ShaderUtil.packOverlay(creeper), 0.5F, 0.5F, 0.5F, 1.0F);
    }

    public static class Eyes extends EyesLayer<CosmicCreeper, CosmicCreeperModel>
    {
        public Eyes(RenderLayerParent<CosmicCreeper, CosmicCreeperModel> parent)
        {
            super(parent);
        }

        @Override
        public @NotNull RenderType renderType()
        {
            return RenderType.eyes(new Identifier("textures/entity/mob/cosmic_creeper/cosmic_creeper_eyes_layer.png"));
        }

        @Override
        public void render(@NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight, @NotNull CosmicCreeper creeper, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
        {
            super.render(stack, source, LightTexture.FULL_BRIGHT, creeper, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
        }
    }
}