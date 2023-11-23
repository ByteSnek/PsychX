package bytesnek.tq.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import bytesnek.snakerlib.resources.ResourceReference;
import bytesnek.tq.client.model.entity.SnipeModel;
import bytesnek.tq.level.entity.mob.Snipe;

/**
 * Created by SnakerBone on 26/05/2023
 **/
public class SnipeRenderer extends MobRenderer<Snipe, SnipeModel>
{
    public SnipeRenderer(EntityRendererProvider.Context context)
    {
        super(context, new SnipeModel(context.bakeLayer(SnipeModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Snipe snipe)
    {
        return new ResourceReference("textures/entity/mob/snipe.png");
    }
}
