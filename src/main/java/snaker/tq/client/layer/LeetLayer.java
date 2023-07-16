package snaker.tq.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.utility.ShaderUtil;
import snaker.tq.client.RenderTypes;
import snaker.tq.client.model.entity.LeetModel;
import snaker.tq.level.entity.mob.Leet;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class LeetLayer extends RenderLayer<Leet, LeetModel>
{
    public LeetLayer(RenderLayerParent<Leet, LeetModel> pRenderer)
    {
        super(pRenderer);
    }

    @Override
    public void render(@NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight, @NotNull Leet leet, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch)
    {
        getParentModel().renderToBuffer(stack, source.getBuffer(RenderTypes.OBJ_WATERCOLOUR), packedLight, ShaderUtil.packOverlay(leet), 1, 1, 1, 1);
    }
}