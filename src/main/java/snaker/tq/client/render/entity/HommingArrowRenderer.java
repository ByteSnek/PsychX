package snaker.tq.client.render.entity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import snaker.tq.level.entity.projectile.HommingArrow;
import snaker.snakerlib.utility.ResourcePath;

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
        return new ResourcePath("textures/entity/projectile/homming_arrow.png");
    }
}
