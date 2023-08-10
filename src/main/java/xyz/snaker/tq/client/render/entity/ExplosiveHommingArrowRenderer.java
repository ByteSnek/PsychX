package xyz.snaker.tq.client.render.entity;

import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.tq.level.entity.projectile.ExplosiveHommingArrow;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

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
        return new ResourcePath("textures/entity/projectile/homming_arrow.png");
    }
}
