package xyz.snaker.tq.client.render.block;

import java.util.HashMap;
import java.util.Map;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.math.BasicCube;
import xyz.snaker.snakerlib.utility.tools.RenderStuff;
import xyz.snaker.tq.client.render.type.ItemLikeRenderType;
import xyz.snaker.tq.rego.BlockEntities;
import xyz.snaker.tq.rego.Blocks;

import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import static net.minecraft.world.level.block.Blocks.END_GATEWAY;

/**
 * Created by SnakerBone on 28/04/2023
 **/
public class ShaderBlockRenderer<T extends BlockEntity> implements BlockEntityRenderer<T>, BlockEntityRendererProvider<T>
{
    public static final Map<BlockEntityType<?>, ItemLikeRenderType> BLOCK_ENTITY_2_RENDER_TYPE = Util.make(new HashMap<>(), map ->
    {
        map.put(BlockEntities.SWIRL.get(), ItemLikeRenderType.SWIRL);
        map.put(BlockEntities.SNOWFLAKE.get(), ItemLikeRenderType.SNOWFLAKE);
        map.put(BlockEntities.WATERCOLOUR.get(), ItemLikeRenderType.WATERCOLOUR);
        map.put(BlockEntities.MULTICOLOUR.get(), ItemLikeRenderType.MULTICOLOUR);
        map.put(BlockEntities.FLARE.get(), ItemLikeRenderType.FIRE);
        map.put(BlockEntities.STARRY.get(), ItemLikeRenderType.BLACK_STARS);
        map.put(BlockEntities.GEOMETRIC.get(), ItemLikeRenderType.CLIP);
    });
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
        Block overlayBlock = END_GATEWAY;
        ItemLikeRenderType renderType = BLOCK_ENTITY_2_RENDER_TYPE.get(blockEntity.getType());
        switch (renderType) {
            case SWIRL -> overlayBlock = Blocks.SHIMMER_OVERLAY.get();
            case SNOWFLAKE -> overlayBlock = Blocks.TURQUOISE_OVERLAY.get();
            case WATERCOLOUR -> overlayBlock = Blocks.CREME_OVERLAY.get();
            case MULTICOLOUR -> overlayBlock = Blocks.SKIN_OVERLAY.get();
            case BLACK_STARS -> overlayBlock = Blocks.WHITE_OVERLAY.get();
            case CLIP, FIRE -> overlayBlock = Blocks.DARK_OVERLAY.get();
            default -> SnakerLib.LOGGER.warnf("RenderType '%s' is not an internal block item render type", renderType);
        }
        RenderStuff.renderOverlayTexture(overlayBlock, stack, source, packedLight, packedOverlay);
    }

    @Override
    public @NotNull BlockEntityRenderer<T> create(@NotNull Context context)
    {
        return this;
    }
}
