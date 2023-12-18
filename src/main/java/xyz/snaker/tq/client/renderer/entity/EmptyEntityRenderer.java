package xyz.snaker.tq.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 3/12/2023
 **/
@SuppressWarnings("rawtypes")
public class EmptyEntityRenderer extends EntityRenderer
{
    public EmptyEntityRenderer(EntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    @SuppressWarnings("DataFlowIssue")
    public @NotNull ResourceLocation getTextureLocation(@NotNull Entity entity)
    {
        return null;
    }
}
