package snaker.tq.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.resources.Identifier;
import snaker.tq.client.model.entity.FlutterflyModel;
import snaker.tq.level.entity.creature.Flutterfly;

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
        return new Identifier("textures/entity/creature/flutterfly.png");
    }
}
