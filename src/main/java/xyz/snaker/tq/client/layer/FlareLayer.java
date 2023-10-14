package xyz.snaker.tq.client.layer;

import xyz.snaker.snakerlib.utility.tools.RenderStuff;
import xyz.snaker.tq.client.model.entity.FlareModel;
import xyz.snaker.tq.client.render.type.EntityRenderType;
import xyz.snaker.tq.level.entity.mob.Flare;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

import com.mojang.blaze3d.vertex.PoseStack;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class FlareLayer extends RenderLayer<Flare, FlareModel>
{
    public FlareLayer(RenderLayerParent<Flare, FlareModel> parent)
    {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight, @NotNull Flare flare, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch)
    {
        RenderStuff.renderLayer(this, stack, source, EntityRenderType.FIRE.get(), flare, packedLight);
    }
}
