package snaker.tq.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.resources.Identifier;
import snaker.tq.client.layer.FlareLayer;
import snaker.tq.client.model.entity.FlareModel;
import snaker.tq.level.entity.mob.Flare;

/**
 * Created by SnakerBone on 26/05/2023
 **/
public class FlareRenderer extends MobRenderer<Flare, FlareModel>
{
    public FlareRenderer(EntityRendererProvider.Context context)
    {
        super(context, new FlareModel(context.bakeLayer(FlareModel.LAYER_LOCATION)), 0.5F);
        addLayer(new FlareLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Flare flare)
    {
        return new Identifier("textures/solid.png");
    }
}
