package snaker.snakerlib.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 13/06/2023
 **/
public abstract class PreppedRenderer extends BlockEntityWithoutLevelRenderer
{
    protected EntityModelSet modelSet;
    protected BlockEntityRenderDispatcher renderDispatcher;

    public PreppedRenderer(BlockEntityRenderDispatcher renderDispatcher, EntityModelSet modelSet)
    {
        super(renderDispatcher, modelSet);
        this.renderDispatcher = renderDispatcher;
        this.modelSet = modelSet;
    }

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager manager)
    {
        super.onResourceManagerReload(manager);
    }

    @Override
    public abstract void renderByItem(@NotNull ItemStack itemStack, @NotNull ItemDisplayContext context, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight, int packedOverlay);
}
