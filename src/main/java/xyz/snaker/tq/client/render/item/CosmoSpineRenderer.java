package xyz.snaker.tq.client.render.item;

import xyz.snaker.snakerlib.client.render.PreppedRenderer;
import xyz.snaker.snakerlib.math.PoseStackBuilder;
import xyz.snaker.tq.client.model.item.CosmoSpineModel;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import com.mojang.blaze3d.vertex.PoseStack;

/**
 * Created by SnakerBone on 12/03/2023
 **/
public class CosmoSpineRenderer extends PreppedRenderer
{
    private final RenderType type;
    private CosmoSpineModel model;

    public CosmoSpineRenderer(RenderType type)
    {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.model = new CosmoSpineModel(modelSet.bakeLayer(CosmoSpineModel.LAYER_LOCATION));
        this.type = type;
    }

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager manager)
    {
        super.onResourceManagerReload(manager);
        this.model = new CosmoSpineModel(modelSet.bakeLayer(CosmoSpineModel.LAYER_LOCATION));
    }

    @Override
    public void renderByItem(@NotNull ItemStack itemStack, @NotNull ItemDisplayContext context, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight, int packedOverlay)
    {
        PoseStackBuilder pose = new PoseStackBuilder(stack);
        switch (context) {
            case GUI -> pose.translate(0.56);
            case FIXED -> stack.translate(0.56, 0.56, 0.5);
            default -> pose.translate(0.5);
        }
        model.renderToBuffer(stack, source.getBuffer(type), packedLight, packedOverlay, 1, 1, 1, 1);
    }
}
