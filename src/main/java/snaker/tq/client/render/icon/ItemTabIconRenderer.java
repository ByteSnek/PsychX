package snaker.tq.client.render.icon;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.SnakerLib;
import snaker.snakerlib.client.render.CyclicalIconRenderer;
import snaker.tq.rego.Rego;

/**
 * Created by SnakerBone on 12/06/2023
 **/
public class ItemTabIconRenderer extends CyclicalIconRenderer
{
    private static ItemStack stackToRender;

    public ItemTabIconRenderer()
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
                            Rego.ITEM_RED_COSMO_SPINE.get().getDefaultInstance(),
                            Rego.ITEM_GREEN_COSMO_SPINE.get().getDefaultInstance(),
                            Rego.ITEM_BLUE_COSMO_SPINE.get().getDefaultInstance(),
                            Rego.ITEM_YELLOW_COSMO_SPINE.get().getDefaultInstance(),
                            Rego.ITEM_PINK_COSMO_SPINE.get().getDefaultInstance(),
                            Rego.ITEM_PURPLE_COSMO_SPINE.get().getDefaultInstance(),
                            Rego.ITEM_ALPHA_COSMO_SPINE.get().getDefaultInstance()
                    };

            float scale = 1;

            int tickCount = (int) SnakerLib.getClientTickCount();
            int index = ((tickCount - 40) / 100) % stacks.length;

            setStackToRender(stacks[index]);

            stack.translate(0.5F, 0.5F, 0);

            ItemStack fallback = Items.PORKCHOP.getDefaultInstance();
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
                    SnakerLib.LOGGER.warn("Tried to render: " + item.getClass().getSimpleName() + " but an error occured");
                    SnakerLib.LOGGER.logError(e);
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

        renderer.renderStatic(itemStack, ItemDisplayContext.GUI, packedLight, packedOverlay, stack, source, null, 15728880);
        source.endBatch();
    }
}