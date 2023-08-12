package xyz.snaker.tq.client.render.icon;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.client.render.CyclicalIconRenderer;
import xyz.snaker.tq.rego.Rego;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;

/**
 * Created by SnakerBone on 12/06/2023
 **/
public class BlockTabIconRenderer extends CyclicalIconRenderer
{
    private static ItemStack blockToRender;

    public BlockTabIconRenderer()
    {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(@NotNull ItemStack itemStack, @NotNull ItemDisplayContext context, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight, int packedOverlay)
    {
        ClientLevel level = Minecraft.getInstance().level;

        if (level != null) {
            ItemStack[] blocks =
                    {
                            Rego.ITEM_SWIRL_BLOCK.get().getDefaultInstance(),
                            Rego.ITEM_FLARE_BLOCK.get().getDefaultInstance(),
                            Rego.ITEM_MULTICOLOUR_BLOCK.get().getDefaultInstance(),
                            Rego.ITEM_SNOWFLAKE_BLOCK.get().getDefaultInstance(),
                            Rego.ITEM_STARRY_BLOCK.get().getDefaultInstance(),
                            Rego.ITEM_WATERCOLOUR_BLOCK.get().getDefaultInstance(),
                            Rego.ITEM_GEOMETRIC_BLOCK.get().getDefaultInstance()
                    };

            float scale = 1;

            int tickCount = (int) SnakerLib.getClientTickCount();
            int index = ((tickCount + 20) / 160) % blocks.length;

            setBlockToRender(blocks[index]);

            stack.translate(0.5F, 0.5F, 0);

            ItemStack fallback = Items.BEACON.getDefaultInstance();
            ItemStack block = blockToRender == null ? fallback : blockToRender;

            try {
                if (!block.equals(fallback)) {
                    renderBlock(stack, block, scale, packedLight, packedOverlay);
                } else {
                    SnakerLib.LOGGER.error("Could not render block tab icon! Ignoring and rendering fallback");
                    renderBlock(stack, block, scale, packedLight, packedOverlay);
                }
            } catch (Exception e) {
                if (notifyError) {
                    String errorMessage = e.getMessage();
                    String className = block.getClass().getSimpleName();
                    SnakerLib.LOGGER.errorf("Could not render %s: %s", className, errorMessage);
                    notifyError = false;
                }
            }
        }
    }

    public static void setBlockToRender(ItemStack stack)
    {
        blockToRender = stack;
    }

    public static void renderBlock(PoseStack stack, ItemStack itemStack, float scale, int packedLight, int packedOverlay)
    {
        stack.scale(scale, scale, scale);

        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        MultiBufferSource.BufferSource source = Minecraft.getInstance().renderBuffers().bufferSource();

        Lighting.setupFor3DItems();

        renderer.renderStatic(itemStack, ItemDisplayContext.GUI, packedLight, packedOverlay, stack, source, null, 15728880);
        source.endBatch();
    }
}
