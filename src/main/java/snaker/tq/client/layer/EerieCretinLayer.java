package snaker.tq.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.utility.ShaderUtil;
import snaker.tq.client.RenderTypes;
import snaker.tq.client.model.entity.EerieCretinModel;
import snaker.tq.level.entity.mob.EerieCretin;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class EerieCretinLayer extends RenderLayer<EerieCretin, EerieCretinModel>
{
    public EerieCretinLayer(RenderLayerParent<EerieCretin, EerieCretinModel> parent)
    {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight, @NotNull EerieCretin cretin, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch)
    {
        getParentModel().renderToBuffer(stack, source.getBuffer(RenderTypes.OBJ_SWIRL), packedLight, ShaderUtil.packOverlay(cretin), 1, 1, 1, 1);
    }
}