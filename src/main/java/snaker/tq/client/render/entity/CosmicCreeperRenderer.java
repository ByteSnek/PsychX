package snaker.tq.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import snaker.tq.client.layer.CosmicCreeperLayer;
import snaker.tq.client.model.entity.CosmicCreeperModel;
import snaker.tq.level.entity.mob.CosmicCreeper;
import snaker.snakerlib.utility.ResourcePath;

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
        return new ResourcePath("textures/entity/mob/cosmic_creeper/cosmic_creeper.png");
    }
}