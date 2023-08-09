package xyz.snaker.tq.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import xyz.snaker.tq.client.model.entity.FlutterflyModel;
import xyz.snaker.tq.level.entity.creature.Flutterfly;
import xyz.snaker.snakerlib.utility.ResourcePath;

/**
 * Created by SnakerBone on 26/05/2023
 **/
public class FlutterflyRenderer extends MobRenderer<Flutterfly, FlutterflyModel>
{
    public FlutterflyRenderer(EntityRendererProvider.Context context)
    {
        super(context, new FlutterflyModel(context.bakeLayer(FlutterflyModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Flutterfly entity)
    {
        return new ResourcePath("textures/entity/creature/flutterfly.png");
    }
}
