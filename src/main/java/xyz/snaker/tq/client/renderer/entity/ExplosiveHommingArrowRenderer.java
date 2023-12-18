package xyz.snaker.tq.client.renderer.entity;

import xyz.snaker.snakerlib.resources.ResourceReference;
import xyz.snaker.tq.level.entity.projectile.ExplosiveHommingArrow;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 4/01/2023
 **/
public class ExplosiveHommingArrowRenderer extends ArrowRenderer<ExplosiveHommingArrow>
{
    public ExplosiveHommingArrowRenderer(EntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ExplosiveHommingArrow entity)
    {
        return new ResourceReference("textures/entity/projectile/homming_arrow.png");
    }
}
