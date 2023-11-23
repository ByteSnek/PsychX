package bytesnek.tq.client.layer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

import com.mojang.blaze3d.vertex.PoseStack;

import org.jetbrains.annotations.NotNull;

import bytesnek.snakerlib.utility.Rendering;
import bytesnek.tq.client.model.entity.CosmoModel;
import bytesnek.tq.level.entity.mob.Cosmo;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class CosmoLayer extends RenderLayer<Cosmo, CosmoModel>
{
    public CosmoLayer(RenderLayerParent<Cosmo, CosmoModel> parent)
    {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight, @NotNull Cosmo cosmo, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch)
    {
        Rendering.renderLayer(this, stack, source, Cosmo.getRenderType(cosmo.getVariant()), cosmo, packedLight);
    }
}