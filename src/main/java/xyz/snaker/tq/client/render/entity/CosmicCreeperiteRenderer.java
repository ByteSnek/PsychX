package xyz.snaker.tq.client.render.entity;

import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.tq.client.layer.CosmicCreeperiteLayer;
import xyz.snaker.tq.client.model.entity.CosmicCreeperiteModel;
import xyz.snaker.tq.level.entity.mob.CosmicCreeperite;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import com.mojang.blaze3d.vertex.PoseStack;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class CosmicCreeperiteRenderer extends MobRenderer<CosmicCreeperite, CosmicCreeperiteModel>
{
    public CosmicCreeperiteRenderer(EntityRendererProvider.Context context)
    {
        super(context, new CosmicCreeperiteModel(context.bakeLayer(CosmicCreeperiteModel.LAYER_LOCATION)), 0.5F);

        addLayer(new CosmicCreeperiteLayer(this, context.getModelSet()));
        addLayer(new CosmicCreeperiteLayer.Eyes(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull CosmicCreeperite creeperite)
    {
        return new ResourcePath("textures/entity/mob/cosmic_creeperite/cosmic_creeperite.png");
    }

    @Override
    protected float getWhiteOverlayProgress(CosmicCreeperite creeperite, float partialTicks)
    {
        float swelling = creeperite.getSwelling(partialTicks);

        return (swelling * 10) % 2 == 0 ? 0 : Mth.clamp(swelling, 0.5F, 1);
    }

    @Override
    protected void scale(CosmicCreeperite creeperite, PoseStack stack, float partialTicks)
    {
        float swelling = creeperite.getSwelling(partialTicks);
        float swellingOffset = 1 + Mth.sin(swelling * 100) * swelling * 0.01F;

        swelling = Mth.clamp(swelling, 0, 1);
        swelling *= swelling;
        swelling *= swelling;

        float xzSwell = (1 + swelling * 0.4F) * swellingOffset;
        float ySwell = (1 + swelling * 0.1F) / swellingOffset;

        stack.scale(xzSwell, ySwell, xzSwell);
    }
}