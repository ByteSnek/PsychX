package xyz.snaker.tq.client.render.block;

import xyz.snaker.snakerlib.math.BasicCube;
import xyz.snaker.snakerlib.utility.tools.RenderStuff;
import xyz.snaker.tq.client.render.type.ItemLikeRenderType;
import xyz.snaker.tq.rego.Blocks;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

/**
 * Created by SnakerBone on 28/04/2023
 **/
public class ShaderBlockRenderer<T extends BlockEntity> implements BlockEntityRenderer<T>, BlockEntityRendererProvider<T>
{
    private final RenderType type;

    public ShaderBlockRenderer(ItemLikeRenderType type)
    {
        this.type = type.get();
    }

    @Override
    public void render(@NotNull T blockEntity, float partialTick, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight, int packedOverlay)
    {
        VertexConsumer consumer = source.getBuffer(type);
        BasicCube.create(consumer, stack);
        RenderStuff.renderOverlayTexture(Blocks.COMASTONE_OVERLAY.get(), stack, source, packedLight, packedOverlay);
    }

    @Override
    public @NotNull BlockEntityRenderer<T> create(@NotNull Context context)
    {
        return this;
    }
}
