package snaker.tq.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.utility.ShaderUtil;
import snaker.tq.client.model.entity.CosmoModel;
import snaker.tq.level.entity.mob.Cosmo;

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
        getParentModel().renderToBuffer(stack, source.getBuffer(Cosmo.getRenderType(cosmo.getVariant())), packedLight, ShaderUtil.packOverlay(cosmo), 1, 1, 1, 1);
    }
}