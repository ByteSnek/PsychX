package bytesnek.tq.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import bytesnek.snakerlib.resources.ResourceReference;
import bytesnek.tq.client.model.entity.CrankyFlutterflyModel;
import bytesnek.tq.level.entity.mob.CrankyFlutterfly;

/**
 * Created by SnakerBone on 26/05/2023
 **/
public class CrankyFlutterflyRenderer extends MobRenderer<CrankyFlutterfly, CrankyFlutterflyModel>
{
    public CrankyFlutterflyRenderer(EntityRendererProvider.Context context)
    {
        super(context, new CrankyFlutterflyModel(context.bakeLayer(CrankyFlutterflyModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull CrankyFlutterfly entity)
    {
        return new ResourceReference("textures/entity/boss/utterfly2.png");
    }
}
