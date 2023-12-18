package xyz.snaker.tq.client.renderer.entity;

import xyz.snaker.snakerlib.resources.ResourceReference;
import xyz.snaker.tq.client.model.entity.CrankyFlutterflyModel;
import xyz.snaker.tq.level.entity.mob.CrankyFlutterfly;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.NotNull;

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
