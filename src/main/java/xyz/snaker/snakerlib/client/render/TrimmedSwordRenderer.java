package xyz.snaker.snakerlib.client.render;

import xyz.snaker.snakerlib.client.model.TrimmedSwordModel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

/**
 * Created by SnakerBone on 3/07/2023
 **/
public abstract class TrimmedSwordRenderer<M extends TrimmedSwordModel> extends PreppedRenderer
{
    /**
     * The current sword model instance
     **/
    private final M model;

    /**
     * The current sword model render type
     **/
    private final RenderType renderType;

    public TrimmedSwordRenderer(BlockEntityRenderDispatcher renderDispatcher, EntityModelSet modelSet)
    {
        super(renderDispatcher, modelSet);
        this.model = getModel(modelSet);
        this.renderType = getRenderType();
    }

    /**
     * Gets the current sword model instance
     *
     * @param set The entity model set. 99% of the time this is obtained from {@link Minecraft#getEntityModels()} unless specified otherwise
     * @return The current sword model instance
     **/
    public abstract M getModel(EntityModelSet set);

    /**
     * Gets the current sword model render type
     *
     * @return The current sword model render type
     **/
    public abstract RenderType getRenderType();

    @Override
    public void renderByItem(@NotNull ItemStack itemStack, @NotNull ItemDisplayContext context, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight, int packedOverlay)
    {
        VertexConsumer consumer = source.getBuffer(renderType);
        if (context == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) {
            stack.mulPose(Axis.XP.rotationDegrees(32.5F));
            stack.translate(0.175, 0.2, -0.35);
            stack.scale(0.85F, 0.85F, 0.85F);
        }
        if (context == ItemDisplayContext.FIRST_PERSON_LEFT_HAND) {
            stack.mulPose(Axis.XP.rotationDegrees(32.5F));
            stack.translate(0, 0.2, -0.35);
            stack.scale(0.85F, 0.85F, 0.85F);
        }
        if (context == ItemDisplayContext.GUI) {
            stack.mulPose(Axis.YP.rotationDegrees(90));
            stack.mulPose(Axis.XP.rotationDegrees(55));
            double m01 = 0.3;
            double m02 = 0.1113;
            double m03 = 0.013;
            stack.translate(0, m01, -m01);
            stack.translate(0, -m01, -m01);
            stack.translate(0, -m02, -m02);
            stack.translate(0, -m03, m03);
            float scale = 1.1F;
            stack.scale(scale, scale, scale);
        }
        if (context == ItemDisplayContext.FIXED) {
            stack.rotateAround(Axis.YP.rotationDegrees(180), 0.5F, 0, 0.5F);
            stack.mulPose(Axis.YP.rotationDegrees(90));
            stack.mulPose(Axis.XP.rotationDegrees(55));
            double m01 = 0.3375 / 1.10075;
            double m02 = 0.1375;
            double m03 = 0.01375;
            stack.translate(-0.3, m01, -m01);
            stack.translate(-0.25, -m01, -m01);
            stack.translate(-0.3, -m02, -m02);
            stack.translate(-0.25, -m03, m03);
            float scale = 1.2F;
            stack.scale(scale, scale, scale);
        }
        Lighting.setupForFlatItems();
        model.renderToBuffer(stack, consumer, packedLight, packedOverlay, 1, 1, 1, 1);
    }
}
