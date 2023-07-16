package snaker.tq.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.resources.Identifier;
import snaker.tq.client.layer.LeetLayer;
import snaker.tq.client.model.entity.LeetModel;
import snaker.tq.level.entity.mob.Leet;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class LeetRenderer extends MobRenderer<Leet, LeetModel>
{
    public LeetRenderer(EntityRendererProvider.Context context)
    {
        super(context, new LeetModel(context.bakeLayer(LeetModel.LAYER_LOCATION)), 0.5F);
        addLayer(new LeetLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Leet leet)
    {
        return new Identifier("textures/solid.png");
    }
}