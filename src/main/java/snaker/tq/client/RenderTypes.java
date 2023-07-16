package snaker.tq.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import snaker.snakerlib.utility.SnakerUtil;

import java.util.function.Supplier;

import static net.minecraft.client.renderer.RenderStateShard.*;

/**
 * Created by SnakerBone on 17/06/2023
 **/
public class RenderTypes
{
    public static RenderType OBJ_BLUR_FOG = custom(DefaultVertexFormat.BLOCK, normal(Shaders::getBlurFog));
    public static RenderType OBJ_PLAIN = custom(DefaultVertexFormat.BLOCK, normal(Shaders::getPlain));
    public static RenderType OBJ_BLACK_STARS = custom(DefaultVertexFormat.BLOCK, normal(Shaders::getBlackStars));
    public static RenderType OBJ_WHITE_STARS = custom(DefaultVertexFormat.BLOCK, normal(Shaders::getWhiteStars));
    public static RenderType OBJ_RED_STARS = custom(DefaultVertexFormat.BLOCK, normal(Shaders::getRedStars));
    public static RenderType OBJ_GREEN_STARS = custom(DefaultVertexFormat.BLOCK, normal(Shaders::getGreenStars));
    public static RenderType OBJ_BLUE_STARS = custom(DefaultVertexFormat.BLOCK, normal(Shaders::getBlueStars));
    public static RenderType OBJ_YELLOW_STARS = custom(DefaultVertexFormat.BLOCK, normal(Shaders::getYellowStars));
    public static RenderType OBJ_PINK_STARS = custom(DefaultVertexFormat.BLOCK, normal(Shaders::getPinkStars));
    public static RenderType OBJ_PURPLE_STARS = custom(DefaultVertexFormat.BLOCK, normal(Shaders::getPurpleStars));
    public static RenderType OBJ_SWIRL = custom(DefaultVertexFormat.BLOCK, normal(Shaders::getSwirl));
    public static RenderType OBJ_SNOWFLAKE = custom(DefaultVertexFormat.BLOCK, normal(Shaders::getSnowflake));
    public static RenderType OBJ_WATERCOLOUR = custom(DefaultVertexFormat.BLOCK, normal(Shaders::getWatercolour));
    public static RenderType OBJ_MULTICOLOUR = custom(DefaultVertexFormat.BLOCK, normal(Shaders::getMulticolour));
    public static RenderType OBJ_FIRE = custom(DefaultVertexFormat.BLOCK, normal(Shaders::getFire));
    public static RenderType ENT_FIRE = custom(DefaultVertexFormat.BLOCK, entity(Shaders::getFire));
    public static RenderType ENT_BLACK_STARS = custom(DefaultVertexFormat.BLOCK, entity(Shaders::getBlackStars));
    public static RenderType ENT_WHITE_STARS = custom(DefaultVertexFormat.BLOCK, entity(Shaders::getWhiteStars));
    public static RenderType ENT_RED_STARS = custom(DefaultVertexFormat.BLOCK, entity(Shaders::getRedStars));
    public static RenderType ENT_GREEN_STARS = custom(DefaultVertexFormat.BLOCK, entity(Shaders::getGreenStars));
    public static RenderType ENT_BLUE_STARS = custom(DefaultVertexFormat.BLOCK, entity(Shaders::getBlueStars));
    public static RenderType ENT_YELLOW_STARS = custom(DefaultVertexFormat.BLOCK, entity(Shaders::getYellowStars));
    public static RenderType ENT_PINK_STARS = custom(DefaultVertexFormat.BLOCK, entity(Shaders::getPinkStars));
    public static RenderType ENT_PURPLE_STARS = custom(DefaultVertexFormat.BLOCK, entity(Shaders::getPurpleStars));
    public static RenderType ENT_PULSE = custom(DefaultVertexFormat.POSITION_TEX, translucent(Shaders::getPulse));

    public static RenderType custom(VertexFormat format, RenderType.CompositeState state)
    {
        return RenderType.create(SnakerUtil.PLACEHOLDER, format, VertexFormat.Mode.QUADS, 256, state);
    }

    public static RenderType.CompositeState normal(Supplier<ShaderInstance> shader)
    {
        return RenderType.CompositeState.builder()
                .setShaderState(new ShaderStateShard(shader))
                .setLightmapState(LIGHTMAP)
                .setCullState(NO_CULL)
                .setOverlayState(OVERLAY)
                .createCompositeState(false);
    }

    public static RenderType.CompositeState entity(Supplier<ShaderInstance> shader)
    {
        return RenderType.CompositeState.builder()
                .setShaderState(new ShaderStateShard(shader))
                .setCullState(NO_CULL)
                .setDepthTestState(EQUAL_DEPTH_TEST)
                .setLightmapState(LIGHTMAP)
                .createCompositeState(false);
    }

    public static RenderType.CompositeState translucent(Supplier<ShaderInstance> shader)
    {
        return RenderType.CompositeState.builder()
                .setShaderState(new ShaderStateShard(shader))
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setLightmapState(LIGHTMAP)
                .setCullState(NO_CULL)
                .setDepthTestState(EQUAL_DEPTH_TEST)
                .createCompositeState(false);
    }

    public static RenderType.CompositeState sampler(Supplier<ShaderInstance> shader, boolean blur, boolean mipmap, ResourceLocation... samplers)
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

    public static RenderType.CompositeState sampler(Supplier<ShaderInstance> shader, ResourceLocation sampler, boolean blur, boolean mipmap)
    {
        return sampler(shader, blur, mipmap, sampler, sampler);
    }
}
