package bytesnek.tq.client.renderer.entity;

import xyz.snaker.snakerlib.math.Tensor;
import xyz.snaker.snakerlib.resources.ResourceReference;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import com.mojang.blaze3d.vertex.PoseStack;

import org.jetbrains.annotations.NotNull;

import bytesnek.tq.client.model.entity.FrolickerModel;
import bytesnek.tq.level.entity.creature.Frolicker;

/**
 * Created by SnakerBone on 26/05/2023
 **/
public class FrolickerRenderer extends MobRenderer<Frolicker, FrolickerModel>
{
    public FrolickerRenderer(EntityRendererProvider.Context context)
    {
        super(context, new FrolickerModel(context.bakeLayer(FrolickerModel.LAYER_LOCATION)), 0);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Frolicker frolicker)
    {
        return new ResourceReference("textures/entity/creature/frolicker.png");
    }

    @Override
    public void render(@NotNull Frolicker frolicker, float entityYaw, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource buffer, int packedLight)
    {
        Tensor tensor = new Tensor(stack);

        if (frolicker.isBaby()) {
            tensor.scale(0.5);
        }

        tensor.setFunnyStatus(frolicker.canDoFunny());
        tensor.funny(frolicker);

        super.render(frolicker, entityYaw, partialTicks, stack, buffer, packedLight);
    }
}
