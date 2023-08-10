package xyz.snaker.tq.client.render.block;

import xyz.snaker.snakerlib.math.BasicCube;
import xyz.snaker.tq.client.RenderTypes;
import xyz.snaker.tq.level.block.entity.ShaderBlockEntity;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

/**
 * Created by SnakerBone on 28/04/2023
 **/
public class ShaderBlockRenderer<T extends ShaderBlockEntity<T>> implements BlockEntityRenderer<T>
{
    private final RenderType type;

    public ShaderBlockRenderer(RenderType type)
    {
        this.type = type;
    }

    @Override
    public void render(@NotNull T blockEntity, float partialTick, @NotNull PoseStack stack, MultiBufferSource source, int packedLight, int packedOverlay)
    {
        VertexConsumer consumer = source.getBuffer(type);
        BasicCube.create(consumer, stack);
    }

    public static class Flare extends ShaderBlockRenderer<ShaderBlockEntity.Flare>
    {
        public Flare(BlockEntityRendererProvider.Context context)
        {
            super(RenderTypes.OBJ_FIRE);
        }
    }

    public static class MultiColour extends ShaderBlockRenderer<ShaderBlockEntity.MultiColour>
    {
        public MultiColour(BlockEntityRendererProvider.Context context)
        {
            super(RenderTypes.OBJ_MULTICOLOUR);
        }
    }

    public static class Snowflake extends ShaderBlockRenderer<ShaderBlockEntity.Snowflake>
    {
        public Snowflake(BlockEntityRendererProvider.Context context)
        {
            super(RenderTypes.OBJ_SNOWFLAKE);
        }
    }

    public static class Starry extends ShaderBlockRenderer<ShaderBlockEntity.Starry>
    {
        public Starry(BlockEntityRendererProvider.Context context)
        {
            super(RenderTypes.OBJ_BLACK_STARS);
        }
    }

    public static class Swirl extends ShaderBlockRenderer<ShaderBlockEntity.Swirl>
    {
        public Swirl(BlockEntityRendererProvider.Context context)
        {
            super(RenderTypes.OBJ_SWIRL);
        }
    }

    public static class WaterColour extends ShaderBlockRenderer<ShaderBlockEntity.WaterColour>
    {
        public WaterColour(BlockEntityRendererProvider.Context context)
        {
            super(RenderTypes.OBJ_WATERCOLOUR);
        }
    }
}
