package bytesnek.tq.client.renderer.entity;

import xyz.snaker.snakerlib.math.Tensor;
import xyz.snaker.snakerlib.resources.ResourceReference;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import com.mojang.blaze3d.vertex.PoseStack;

import org.jetbrains.annotations.NotNull;

import bytesnek.tq.client.model.entity.FlutterflyModel;
import bytesnek.tq.level.entity.creature.Flutterfly;

/**
 * Created by SnakerBone on 26/05/2023
 **/
public class FlutterflyRenderer extends MobRenderer<Flutterfly, FlutterflyModel>
{
    public FlutterflyRenderer(EntityRendererProvider.Context context)
    {
        super(context, new FlutterflyModel(context.bakeLayer(FlutterflyModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Flutterfly entity)
    {
        return new ResourceReference("textures/entity/creature/flutterfly.png");
    }

    @Override
    public void render(@NotNull Flutterfly flutterfly, float entityYaw, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight)
    {
        Tensor builder = new Tensor(stack);

        if (flutterfly.isBaby()) {
            builder.scale(0.5);
        }

        super.render(flutterfly, entityYaw, partialTicks, stack, source, packedLight);
    }
}
