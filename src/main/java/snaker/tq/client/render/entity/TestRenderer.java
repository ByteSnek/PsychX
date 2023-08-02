package snaker.tq.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.resources.Identifier;
import snaker.tq.client.layer.TestLayer;
import snaker.tq.client.model.entity.TestModel;
import snaker.tq.level.entity.Test;

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
        return new Identifier("textures/solid.png");
    }

    @Override
    public void render(@NotNull Test test, float entityYaw, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight)
    {
        super.render(test, entityYaw, partialTicks, stack, source, packedLight);
    }
}
