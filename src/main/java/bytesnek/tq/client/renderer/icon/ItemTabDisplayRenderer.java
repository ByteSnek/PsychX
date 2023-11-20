package bytesnek.tq.client.renderer.icon;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.client.render.CyclicalIconRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;

import org.jetbrains.annotations.NotNull;

import bytesnek.tq.rego.Items;

import static net.minecraft.world.item.Items.PORKCHOP;

/**
 * Created by SnakerBone on 12/06/2023
 **/
public class ItemTabDisplayRenderer extends CyclicalIconRenderer
{
    private static ItemStack stackToRender;

    public ItemTabDisplayRenderer()
    {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(@NotNull ItemStack itemStack, @NotNull ItemDisplayContext context, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight, int packedOverlay)
    {
        ClientLevel level = Minecraft.getInstance().level;

        if (level != null) {
            ItemStack[] stacks =
                    {
                            Items.RED_COSMO_SPINE.get().getDefaultInstance(),
                            Items.GREEN_COSMO_SPINE.get().getDefaultInstance(),
                            Items.BLUE_COSMO_SPINE.get().getDefaultInstance(),
                            Items.YELLOW_COSMO_SPINE.get().getDefaultInstance(),
                            Items.PINK_COSMO_SPINE.get().getDefaultInstance(),
                            Items.PURPLE_COSMO_SPINE.get().getDefaultInstance(),
                            Items.ALPHA_COSMO_SPINE.get().getDefaultInstance()
                    };

            float scale = 1;

            int tickCount = (int) SnakerLib.getClientTickCount();
            int index = ((tickCount - 40) / 100) % stacks.length;

            setStackToRender(stacks[index]);

            stack.translate(0.5F, 0.5F, 0);

            ItemStack fallback = PORKCHOP.getDefaultInstance();
            ItemStack item = stackToRender == null ? fallback : stackToRender;

            try {
                if (!item.equals(fallback)) {
                    renderStack(stack, item, scale, packedLight, packedOverlay);
                } else {
                    SnakerLib.LOGGER.error("Could not render item tab icon! Ignoring and rendering fallback");
                    renderStack(stack, item, scale, packedLight, packedOverlay);
                }
            } catch (Exception e) {
                if (notifyError) {
                    String errorMessage = e.getMessage();
                    String className = item.getClass().getSimpleName();
                    SnakerLib.LOGGER.warnf("Could not render []: []", className, errorMessage);
                    notifyError = false;
                }
            }
        }

    }

    public static void setStackToRender(ItemStack stack)
    {
        stackToRender = stack;
    }

    public static void renderStack(PoseStack stack, ItemStack itemStack, float scale, int packedLight, int packedOverlay)
    {
        stack.scale(scale, scale, scale);

        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        MultiBufferSource.BufferSource source = Minecraft.getInstance().renderBuffers().bufferSource();

        Lighting.setupForFlatItems();

        renderer.renderStatic(itemStack, ItemDisplayContext.GUI, packedLight, packedOverlay, stack, source, null, 0x9832132);
        source.endBatch();
    }
}
