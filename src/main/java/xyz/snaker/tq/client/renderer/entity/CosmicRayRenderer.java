package xyz.snaker.tq.client.renderer.entity;

import xyz.snaker.snakerlib.resources.ResourceReference;
import xyz.snaker.tq.client.renderer.RayEffect;
import xyz.snaker.tq.level.entity.crystal.ComaCrystal;
import xyz.snaker.tq.level.entity.projectile.CosmicRay;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import com.mojang.blaze3d.vertex.PoseStack;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 20/02/2023
 **/
public class CosmicRayRenderer extends EntityRenderer<CosmicRay>
{
    public CosmicRayRenderer(EntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull CosmicRay ray)
    {
        return new ResourceReference("textures/solid.png");
    }

    @Override
    public void render(@NotNull CosmicRay ray, float entityYaw, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight)
    {
        RayEffect.Builder builder = new RayEffect.Builder();

        if (ray.getOwner() instanceof ComaCrystal) {
            builder.setColour(0xFF800080)
                    .setMinSize(40)
                    .setMaxSize(80)
                    .setRayCount(64)
                    .build()
                    .render(stack, source);
        } else {
            builder.setColour(0x33FFFFFF)
                    .setMinSize(20)
                    .setMaxSize(50)
                    .setRayCount(64)
                    .build()
                    .render(stack, source);
        }

        super.render(ray, entityYaw, partialTicks, stack, source, packedLight);
    }
}
