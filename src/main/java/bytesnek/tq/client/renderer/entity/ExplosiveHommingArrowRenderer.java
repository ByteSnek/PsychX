package bytesnek.tq.client.renderer.entity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import bytesnek.snakerlib.resources.ResourceReference;
import bytesnek.tq.level.entity.projectile.ExplosiveHommingArrow;

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
