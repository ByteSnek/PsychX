package snaker.tq.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.utility.ShaderUtil;
import snaker.tq.client.RenderTypes;
import snaker.tq.client.Shaders;
import snaker.tq.client.model.entity.UtterflyModel;
import snaker.tq.level.entity.boss.Utterfly;

/**
 * Created by SnakerBone on 25/03/2023
 **/
public class UtterflyLayer extends RenderLayer<Utterfly, UtterflyModel>
{
    public UtterflyLayer(RenderLayerParent<Utterfly, UtterflyModel> parent)
    {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight, @NotNull Utterfly utterfly, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch)
    {
        float[] colour;
        float chargeStatus = utterfly.getCharging() ? 1F : 0F;

        int phase = utterfly.getPhase();

        switch (phase) {
            case 1 -> colour = ShaderUtil.hexToVec3("FFE800");
            case 2 -> colour = ShaderUtil.hexToVec3("FF8300");
            case 3, 4 -> colour = ShaderUtil.hexToVec3("FF0000");
            default -> colour = ShaderUtil.hexToVec3("000000");
        }

        Shaders.getPulse().enqueueTask(() ->
        {
            Shaders.getPulseRGB().set(colour);
            Shaders.getPulseAlpha().set(chargeStatus);
        });

        getParentModel().renderToBuffer(stack, source.getBuffer(RenderTypes.ENT_PULSE), packedLight, ShaderUtil.packOverlay(utterfly), 1, 1, 1, 1);
    }
}