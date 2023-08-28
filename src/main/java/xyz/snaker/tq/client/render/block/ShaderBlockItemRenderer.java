package xyz.snaker.tq.client.render.block;

import java.util.HashMap;
import java.util.Map;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.client.render.PreppedRenderer;
import xyz.snaker.snakerlib.math.BasicCube;
import xyz.snaker.snakerlib.utility.tools.RenderStuff;
import xyz.snaker.tq.client.render.type.ItemLikeRenderType;
import xyz.snaker.tq.rego.Blocks;
import xyz.snaker.tq.rego.Items;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import static net.minecraft.world.level.block.Blocks.END_GATEWAY;

/**
 * Created by SnakerBone on 28/04/2023
 **/
public class ShaderBlockItemRenderer extends PreppedRenderer
{
    public static final Map<Item, ItemLikeRenderType> ITEM_2_RENDER_TYPE = Util.make(new HashMap<>(), map ->
    {
        map.put(Items.SWIRL_BLOCK.get(), ItemLikeRenderType.SWIRL);
        map.put(Items.SNOWFLAKE_BLOCK.get(), ItemLikeRenderType.SNOWFLAKE);
        map.put(Items.WATERCOLOUR_BLOCK.get(), ItemLikeRenderType.WATERCOLOUR);
        map.put(Items.MULTICOLOUR_BLOCK.get(), ItemLikeRenderType.MULTICOLOUR);
        map.put(Items.FLARE_BLOCK.get(), ItemLikeRenderType.FIRE);
        map.put(Items.STARRY_BLOCK.get(), ItemLikeRenderType.BLACK_STARS);
        map.put(Items.GEOMETRIC_BLOCK.get(), ItemLikeRenderType.CLIP);
    });
    private final RenderType type;

    public ShaderBlockItemRenderer(RenderType type)
    {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.type = type;
    }

    @Override
    public void renderByItem(@NotNull ItemStack itemStack, @NotNull ItemDisplayContext context, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight, int packedOverlay)
    {
        VertexConsumer consumer = source.getBuffer(type);
        if (context == ItemDisplayContext.FIXED) {
            stack.translate(0, 0, 0);
        } else {
            stack.translate(0, 0.5, 0);
        }
        BasicCube.create(consumer, stack);
        Lighting.setupFor3DItems();
        Block overlayBlock = END_GATEWAY;
        if (itemStack.getItem() instanceof BlockItem blockItem) {
            ItemLikeRenderType renderType = ITEM_2_RENDER_TYPE.get(blockItem);
            switch (renderType) {
                case SWIRL -> overlayBlock = Blocks.SHIMMER_OVERLAY.get();
                case SNOWFLAKE -> overlayBlock = Blocks.TURQUOISE_OVERLAY.get();
                case WATERCOLOUR -> overlayBlock = Blocks.CREME_OVERLAY.get();
                case MULTICOLOUR -> overlayBlock = Blocks.SKIN_OVERLAY.get();
                case BLACK_STARS -> overlayBlock = Blocks.WHITE_OVERLAY.get();
                case CLIP, FIRE -> overlayBlock = Blocks.DARK_OVERLAY.get();
                default -> SnakerLib.LOGGER.warnf("RenderType '%s' is not an internal block item render type", renderType);
            }
        }
        RenderStuff.renderOverlayTexture(overlayBlock, stack, source, packedLight, packedOverlay);
    }
}
