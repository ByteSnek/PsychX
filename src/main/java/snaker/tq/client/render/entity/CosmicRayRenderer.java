package snaker.tq.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.resources.Identifier;
import snaker.tq.client.fx.RayFX;
import snaker.tq.level.entity.projectile.CosmicRay;

import java.awt.*;

/**
 * Created by SnakerBone on 20/02/2023
 **/
public class CosmicRayRenderer extends ArrowRenderer<CosmicRay>
{
    public CosmicRayRenderer(EntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull CosmicRay bullet)
    {
        return new Identifier("textures/clear.png");
    }

    @Override
    public void render(@NotNull CosmicRay bullet, float entityYaw, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight)
    {
        RayFX.create(stack, source, Color.WHITE, 40, 40, 16, 0, 0, 0);
        super.render(bullet, entityYaw, partialTicks, stack, source, packedLight);
    }
}