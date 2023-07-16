package snaker.tq.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.math.PoseStackBuilder;
import snaker.snakerlib.resources.Identifier;
import snaker.tq.client.model.entity.FrolickerModel;
import snaker.tq.level.entity.creature.Frolicker;

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
        return new Identifier("textures/entity/creature/frolicker.png");
    }

    @Override
    public void render(@NotNull Frolicker frolicker, float entityYaw, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource buffer, int packedLight)
    {
        PoseStackBuilder pose = new PoseStackBuilder(stack);
        if (frolicker.isBaby()) {
            pose.scale(0.5);
        }
        pose.setFunnyStatus(frolicker.canDoFunny());
        pose.funny(frolicker);
        super.render(frolicker, entityYaw, partialTicks, stack, buffer, packedLight);
    }
}
