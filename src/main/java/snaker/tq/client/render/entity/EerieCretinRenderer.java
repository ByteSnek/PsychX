package snaker.tq.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.resources.Identifier;
import snaker.tq.client.layer.EerieCretinLayer;
import snaker.tq.client.model.entity.EerieCretinModel;
import snaker.tq.level.entity.mob.EerieCretin;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class EerieCretinRenderer extends MobRenderer<EerieCretin, EerieCretinModel>
{
    public EerieCretinRenderer(EntityRendererProvider.Context context)
    {
        super(context, new EerieCretinModel(context.bakeLayer(EerieCretinModel.LAYER_LOCATION)), 0.5F);
        addLayer(new EerieCretinLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull EerieCretin cretin)
    {
        return new Identifier("textures/solid.png");
    }
}