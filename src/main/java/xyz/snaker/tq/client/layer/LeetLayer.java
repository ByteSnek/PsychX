package xyz.snaker.tq.client.layer;

import xyz.snaker.snakerlib.client.render.processor.SimpleRenderTypeProcessor;
import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.snakerlib.utility.tools.RenderStuff;
import xyz.snaker.snakerlib.utility.tools.StringStuff;
import xyz.snaker.tq.client.Shaders;
import xyz.snaker.tq.client.model.entity.LeetModel;
import xyz.snaker.tq.level.entity.mob.Leet;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;

import org.jetbrains.annotations.NotNull;

import static net.minecraft.client.renderer.RenderStateShard.*;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class LeetLayer extends RenderLayer<Leet, LeetModel> implements SimpleRenderTypeProcessor
{
    public LeetLayer(RenderLayerParent<Leet, LeetModel> pRenderer)
    {
        super(pRenderer);
    }

    @Override
    public void render(@NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight, @NotNull Leet leet, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch)
    {
        MultiTextureStateShard.Builder builder = MultiTextureStateShard.builder()
                .add(new ResourcePath("textures/sampler/noise_red.png"), false, false)
                .add(new ResourcePath("textures/sampler/noise_white.png"), false, false);

        RenderType type = RenderType.create(StringStuff.placeholderWithId(), DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, RenderType.TRANSIENT_BUFFER_SIZE, RenderType.CompositeState.builder()
                .setShaderState(new RenderStateShard.ShaderStateShard(Shaders::getCrystalized))
                .setTextureState(builder.build())
                .setCullState(NO_CULL)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .createCompositeState(false));

        RenderStuff.renderLayer(this, stack, source, type, leet, packedLight);
    }
}