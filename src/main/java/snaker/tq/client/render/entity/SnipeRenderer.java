package snaker.tq.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.resources.Identifier;
import snaker.tq.client.model.entity.SnipeModel;
import snaker.tq.level.entity.mob.Snipe;

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
        return new Identifier("textures/entity/mob/snipe.png");
    }
}
