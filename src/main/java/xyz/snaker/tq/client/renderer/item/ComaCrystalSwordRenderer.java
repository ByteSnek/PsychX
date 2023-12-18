package xyz.snaker.tq.client.renderer.item;

import xyz.snaker.snakerlib.client.render.GenericSwordRenderer;
import xyz.snaker.snakerlib.client.render.SRTP;
import xyz.snaker.snakerlib.resources.ResourceReference;
import xyz.snaker.tq.client.Shaders;
import xyz.snaker.tq.client.model.item.ComaCrystalSwordModel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 28/11/2023
 **/
public class ComaCrystalSwordRenderer extends GenericSwordRenderer<ComaCrystalSwordModel> implements SRTP
{
    public ComaCrystalSwordRenderer()
    {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public ComaCrystalSwordModel getModel(EntityModelSet set)
    {
        return new ComaCrystalSwordModel(set.bakeLayer(ComaCrystalSwordModel.LAYER_LOCATION));
    }

    @Override
    public RenderType getRenderType()
    {
        return RenderType.entitySolid(new ResourceReference("textures/item/coma_crystal_sword.png"));
    }

    @Override
    public void renderByItem(@NotNull ItemStack itemStack, @NotNull ItemDisplayContext context, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight, int packedOverlay)
    {
        super.renderByItem(itemStack, context, stack, source, packedLight, packedOverlay);

        ComaCrystalSwordModel model = getModel(modelSet);
        RenderType type = create(null, new Pair<>(DefaultVertexFormat.POSITION_TEX, sampler(Shaders::getCrystalized, new ResourceReference("textures/sampler/noise_green.png"), true, false)));

        stack.translate(0, 1.5, 0);

        model.innerHilt.render(stack, source.getBuffer(type), packedLight, packedOverlay);
        model.innerBauble.render(stack, source.getBuffer(type), packedLight, packedOverlay);
        model.innerBlade.render(stack, source.getBuffer(type), packedLight, packedOverlay);
    }
}
