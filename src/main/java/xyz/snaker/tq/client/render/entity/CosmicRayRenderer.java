package xyz.snaker.tq.client.render.entity;

import java.awt.*;

import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.tq.client.fx.RayFX;
import xyz.snaker.tq.level.entity.projectile.CosmicRay;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;

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
        return ResourcePath.NO_TEXTURE;
    }

    @Override
    public void render(@NotNull CosmicRay bullet, float entityYaw, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight)
    {
        RayFX.create(stack, source, Color.WHITE, 40, 40, 16, 0, 0, 0);
        super.render(bullet, entityYaw, partialTicks, stack, source, packedLight);
    }
}
