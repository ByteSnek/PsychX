package bytesnek.tq.client.renderer.entity;

import xyz.snaker.snakerlib.resources.ResourceReference;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import bytesnek.tq.level.entity.projectile.HommingArrow;

/**
 * Created by SnakerBone on 4/01/2023
 **/
public class HommingArrowRenderer extends ArrowRenderer<HommingArrow>
{
    public HommingArrowRenderer(EntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull HommingArrow entity)
    {
        return new ResourceReference("textures/entity/projectile/homming_arrow.png");
    }
}
