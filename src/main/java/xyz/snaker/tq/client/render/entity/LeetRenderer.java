package xyz.snaker.tq.client.render.entity;

import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.tq.client.layer.LeetLayer;
import xyz.snaker.tq.client.model.entity.LeetModel;
import xyz.snaker.tq.level.entity.mob.Leet;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class LeetRenderer extends MobRenderer<Leet, LeetModel>
{
    public LeetRenderer(EntityRendererProvider.Context context)
    {
        super(context, new LeetModel(context.bakeLayer(LeetModel.LAYER_LOCATION)), 0);
        addLayer(new LeetLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Leet leet)
    {
        return ResourcePath.SOLID_TEXTURE;
    }
}