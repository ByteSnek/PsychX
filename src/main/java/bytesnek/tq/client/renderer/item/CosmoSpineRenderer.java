package bytesnek.tq.client.renderer.item;

import java.util.HashMap;
import java.util.Map;

import bytesnek.snakerlib.client.render.PreppedRenderer;
import bytesnek.snakerlib.math.Tensor;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import com.mojang.blaze3d.vertex.PoseStack;

import org.jetbrains.annotations.NotNull;

import bytesnek.tq.client.model.item.CosmoSpineModel;
import bytesnek.tq.client.renderer.type.ItemLikeRenderType;
import bytesnek.tq.rego.Items;

/**
 * Created by SnakerBone on 12/03/2023
 **/
public class CosmoSpineRenderer extends PreppedRenderer
{
    public static final Map<Item, RenderType> TYPE = Util.make(new HashMap<>(), map ->
    {
        map.put(Items.RED_COSMO_SPINE.get(), ItemLikeRenderType.RED_STARS.get());
        map.put(Items.GREEN_COSMO_SPINE.get(), ItemLikeRenderType.GREEN_STARS.get());
        map.put(Items.BLUE_COSMO_SPINE.get(), ItemLikeRenderType.BLUE_STARS.get());
        map.put(Items.YELLOW_COSMO_SPINE.get(), ItemLikeRenderType.YELLOW_STARS.get());
        map.put(Items.PINK_COSMO_SPINE.get(), ItemLikeRenderType.PINK_STARS.get());
        map.put(Items.PURPLE_COSMO_SPINE.get(), ItemLikeRenderType.PURPLE_STARS.get());
        map.put(Items.ALPHA_COSMO_SPINE.get(), ItemLikeRenderType.BLACK_STARS.get());
    });
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
        Tensor pose = new Tensor(stack);
        switch (context) {
            case GUI -> pose.translate(0.56);
            case FIXED -> stack.translate(0.56, 0.56, 0.5);
            default -> pose.translate(0.5);
        }
        model.renderToBuffer(stack, source.getBuffer(type), packedLight, packedOverlay, 1, 1, 1, 1);
    }
}
