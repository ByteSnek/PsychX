package snaker.tq.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import snaker.tq.client.layer.AntiCosmoLayer;
import snaker.tq.client.model.entity.AntiCosmoModel;
import snaker.tq.level.entity.boss.AntiCosmo;
import snaker.snakerlib.utility.ResourcePath;

/**
 * Created by SnakerBone on 18/06/2023
 **/
public class AntiCosmoRenderer extends MobRenderer<AntiCosmo, AntiCosmoModel>
{
    public AntiCosmoRenderer(EntityRendererProvider.Context context)
    {
        super(context, new AntiCosmoModel(context.bakeLayer(AntiCosmoModel.LAYER_LOCATION)), 0.5F);
        addLayer(new AntiCosmoLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AntiCosmo cosmo)
    {
        return ResourcePath.SOLID_TEXTURE;
    }

    @Override
    public void render(@NotNull AntiCosmo cosmo, float entityYaw, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight)
    {
        super.render(cosmo, entityYaw, partialTicks, stack, source, packedLight);
    }
}
