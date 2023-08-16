package xyz.snaker.snakerlib.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;

/**
 * Created by SnakerBone on 13/06/2023
 **/
public abstract class PreppedRenderer extends BlockEntityWithoutLevelRenderer
{
    /**
     * The entity model set. 99% of the time this is obtained from {@link Minecraft#getEntityModels()} unless specified otherwise
     **/
    protected EntityModelSet modelSet;

    /**
     * The block entity render dispatcher. 99% of the time this is obtained from {@link Minecraft#getBlockEntityRenderDispatcher()} unless specified otherwise
     **/
    protected BlockEntityRenderDispatcher renderDispatcher;

    public PreppedRenderer(BlockEntityRenderDispatcher renderDispatcher, EntityModelSet modelSet)
    {
        super(renderDispatcher, modelSet);
        this.renderDispatcher = renderDispatcher;
        this.modelSet = modelSet;
    }

    @Override
    public abstract void renderByItem(@NotNull ItemStack itemStack, @NotNull ItemDisplayContext context, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight, int packedOverlay);
}
