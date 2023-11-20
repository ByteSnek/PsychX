package bytesnek.tq.client.renderer.entity;

import xyz.snaker.snakerlib.resources.ResourceReference;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import com.mojang.blaze3d.vertex.PoseStack;

import org.jetbrains.annotations.NotNull;

import bytesnek.tq.client.layer.CosmicCreeperLayer;
import bytesnek.tq.client.model.entity.CosmicCreeperModel;
import bytesnek.tq.level.entity.mob.CosmicCreeper;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class CosmicCreeperRenderer extends MobRenderer<CosmicCreeper, CosmicCreeperModel>
{
    public CosmicCreeperRenderer(EntityRendererProvider.Context context)
    {
        super(context, new CosmicCreeperModel(context.bakeLayer(CosmicCreeperModel.LAYER_LOCATION)), 0.5F);

        addLayer(new CosmicCreeperLayer(this, context.getModelSet()));
        addLayer(new CosmicCreeperLayer.Eyes(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull CosmicCreeper creeper)
    {
        return new ResourceReference("textures/entity/mob/cosmic_creeper/cosmic_creeper.png");
    }

    @Override
    protected float getWhiteOverlayProgress(CosmicCreeper creeper, float partialTicks)
    {
        float swelling = creeper.getSwelling(partialTicks);

        return (swelling * 10) % 2 == 0 ? 0 : Mth.clamp(swelling, 0.5F, 1);
    }

    @Override
    protected void scale(CosmicCreeper creeper, PoseStack stack, float partialTicks)
    {
        float swelling = creeper.getSwelling(partialTicks);
        float swellingOffset = 1 + Mth.sin(swelling * 100) * swelling * 0.01F;

        swelling = Mth.clamp(swelling, 0, 1);
        swelling *= swelling;
        swelling *= swelling;

        float xzSwell = (1 + swelling * 0.4F) * swellingOffset;
        float ySwell = (1 + swelling * 0.1F) / swellingOffset;

        stack.scale(xzSwell, ySwell, xzSwell);
    }
}