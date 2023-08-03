package snaker.tq.client.render.entity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import snaker.tq.level.entity.projectile.ExplosiveHommingArrow;
import snaker.tq.utility.ResourcePath;

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
