package xyz.snaker.tq.client.render.block;

import java.util.HashMap;
import java.util.Map;

import xyz.snaker.snakerlib.client.render.PreppedRenderer;
import xyz.snaker.snakerlib.math.BasicCube;
import xyz.snaker.tq.client.render.type.ItemLikeRenderType;
import xyz.snaker.tq.rego.Rego;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

/**
 * Created by SnakerBone on 28/04/2023
 **/
public class ShaderBlockItemRenderer extends PreppedRenderer
{
    public static final Map<Item, RenderType> TYPE = Util.make(new HashMap<>(), map ->
    {
        map.put(Rego.ITEM_SWIRL_BLOCK.get(), ItemLikeRenderType.SWIRL.get());
        map.put(Rego.ITEM_SNOWFLAKE_BLOCK.get(), ItemLikeRenderType.SNOWFLAKE.get());
        map.put(Rego.ITEM_WATERCOLOUR_BLOCK.get(), ItemLikeRenderType.WATERCOLOUR.get());
        map.put(Rego.ITEM_MULTICOLOUR_BLOCK.get(), ItemLikeRenderType.MULTICOLOUR.get());
        map.put(Rego.ITEM_FLARE_BLOCK.get(), ItemLikeRenderType.FIRE.get());
        map.put(Rego.ITEM_STARRY_BLOCK.get(), ItemLikeRenderType.BLACK_STARS.get());
        map.put(Rego.ITEM_GEOMETRIC_BLOCK.get(), ItemLikeRenderType.CLIP.get());
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
    }
}
