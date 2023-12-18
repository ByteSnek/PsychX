package xyz.snaker.tq.client.layer;

import xyz.snaker.snakerlib.utility.Rendering;
import xyz.snaker.tq.client.Shaders;
import xyz.snaker.tq.client.model.entity.UtterflyModel;
import xyz.snaker.tq.client.renderer.type.EntityRenderType;
import xyz.snaker.tq.level.entity.utterfly.Utterfly;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

import com.mojang.blaze3d.vertex.PoseStack;

import org.jetbrains.annotations.NotNull;

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
            case 1 -> colour = Rendering.hexToRGB("FFE800");
            case 2 -> colour = Rendering.hexToRGB("FF8300");
            case 3, 4 -> colour = Rendering.hexToRGB("FF0000");
            default -> colour = Rendering.hexToRGB("000000");
        }

        Shaders.getPulse().enqueueTask(() ->
        {
            Shaders.getPulseColour().set(colour);
            Shaders.getPulseAlpha().set(chargeStatus);
        });

        Rendering.renderLayer(this, stack, source, EntityRenderType.PULSE.get(), utterfly, packedLight);
    }
}
