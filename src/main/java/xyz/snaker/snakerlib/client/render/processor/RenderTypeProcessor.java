package xyz.snaker.snakerlib.client.render.processor;

import java.util.function.Supplier;
import javax.annotation.Nullable;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.util.Pair;

import static net.minecraft.client.renderer.RenderStateShard.*;

/**
 * Created by SnakerBone on 12/08/2023
 **/
public interface RenderTypeProcessor
{
    RenderType create(@Nullable String name, Pair<VertexFormat, RenderType.CompositeState> pair);

    default RenderType.CompositeState normal(Supplier<ShaderInstance> shader)
    {
        return RenderType.CompositeState.builder()
                .setShaderState(new RenderStateShard.ShaderStateShard(shader))
                .setLightmapState(LIGHTMAP)
                .setCullState(NO_CULL)
                .setOverlayState(OVERLAY)
                .createCompositeState(false);
    }

    default RenderType.CompositeState entity(Supplier<ShaderInstance> shader)
    {
        return RenderType.CompositeState.builder()
                .setShaderState(new ShaderStateShard(shader))
                .setCullState(NO_CULL)
                .setDepthTestState(EQUAL_DEPTH_TEST)
                .setLightmapState(LIGHTMAP)
                .createCompositeState(false);
    }

    default RenderType.CompositeState translucent(Supplier<ShaderInstance> shader)
    {
        return RenderType.CompositeState.builder()
                .setShaderState(new ShaderStateShard(shader))
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setLightmapState(LIGHTMAP)
                .setCullState(NO_CULL)
                .setDepthTestState(EQUAL_DEPTH_TEST)
                .createCompositeState(false);
    }

    default RenderType.CompositeState sampler(Supplier<ShaderInstance> shader, boolean blur, boolean mipmap, ResourceLocation... samplers)
    {
        MultiTextureStateShard.Builder builder = MultiTextureStateShard.builder();
        for (ResourceLocation sampler : samplers) {
            builder.add(sampler, blur, mipmap);
        }
        return RenderType.CompositeState.builder()
                .setShaderState(new ShaderStateShard(shader))
                .setTextureState(builder.build())
                .setCullState(CULL)
                .setWriteMaskState(COLOR_DEPTH_WRITE)
                .setTransparencyState(NO_TRANSPARENCY)
                .createCompositeState(true);
    }

    default RenderType.CompositeState sampler(Supplier<ShaderInstance> shader, ResourceLocation sampler, boolean blur, boolean mipmap)
    {
        return sampler(shader, blur, mipmap, sampler, sampler);
    }
}
