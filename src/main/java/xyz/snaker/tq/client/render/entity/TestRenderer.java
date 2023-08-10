package xyz.snaker.tq.client.render.entity;

import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.tq.client.layer.TestLayer;
import xyz.snaker.tq.client.model.entity.TestModel;
import xyz.snaker.tq.level.entity.mob.Test;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import com.mojang.blaze3d.vertex.PoseStack;

/**
 * Created by SnakerBone on 30/07/2023
 **/
public class TestRenderer extends MobRenderer<Test, TestModel>
{
    public TestRenderer(EntityRendererProvider.Context context)
    {
        super(context, new TestModel(context.bakeLayer(TestModel.LAYER_LOCATION)), 0);
        addLayer(new TestLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Test test)
    {
        return ResourcePath.SOLID_TEXTURE;
    }

    @Override
    public void render(@NotNull Test test, float entityYaw, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight)
    {
        super.render(test, entityYaw, partialTicks, stack, source, packedLight);
    }
}
