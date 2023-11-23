package bytesnek.tq.client.renderer.entity;

import java.awt.*;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import com.mojang.blaze3d.vertex.PoseStack;

import org.jetbrains.annotations.NotNull;

import bytesnek.snakerlib.resources.ResourceReference;
import bytesnek.tq.client.fx.RayFX;
import bytesnek.tq.level.entity.projectile.CosmicRay;

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
        return new ResourceReference("textures/solid.png");
    }

    @Override
    public void render(@NotNull CosmicRay bullet, float entityYaw, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight)
    {
        RayFX.create(stack, source, Color.WHITE, 40, 40, 16, 0, 0, 0);
        super.render(bullet, entityYaw, partialTicks, stack, source, packedLight);
    }
}
