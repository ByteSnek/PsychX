package xyz.snaker.tq.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import org.jetbrains.annotations.NotNull;
import xyz.snaker.snakerlib.utility.RenderStuff;
import xyz.snaker.tq.client.RenderTypes;
import xyz.snaker.tq.client.model.entity.AntiCosmoModel;
import xyz.snaker.tq.level.entity.boss.AntiCosmo;

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
        RenderStuff.renderLayer(this, stack, source, RenderTypes.ENT_WHITE_STARS, cosmo, packedLight);
    }
}
