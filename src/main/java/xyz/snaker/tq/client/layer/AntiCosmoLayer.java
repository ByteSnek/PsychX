package xyz.snaker.tq.client.layer;

import xyz.snaker.snakerlib.utility.tools.RenderStuff;
import xyz.snaker.tq.client.model.entity.AntiCosmoModel;
import xyz.snaker.tq.client.render.type.EntityRenderType;
import xyz.snaker.tq.level.entity.boss.AntiCosmo;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

import com.mojang.blaze3d.vertex.PoseStack;

/**
 * Created by SnakerBone on 18/06/2023
 **/
public class AntiCosmoLayer extends RenderLayer<AntiCosmo, AntiCosmoModel>
{
    public AntiCosmoLayer(RenderLayerParent<AntiCosmo, AntiCosmoModel> parent)
    {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight, @NotNull AntiCosmo cosmo, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch)
    {
        RenderStuff.renderLayer(this, stack, source, EntityRenderType.WHITE_STARS.get(), cosmo, packedLight);
    }
}
