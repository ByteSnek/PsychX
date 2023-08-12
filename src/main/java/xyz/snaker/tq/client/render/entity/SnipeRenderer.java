package xyz.snaker.tq.client.render.entity;

import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.tq.client.model.entity.SnipeModel;
import xyz.snaker.tq.level.entity.mob.Snipe;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

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
        return new ResourcePath("textures/entity/mob/snipe.png");
    }
}