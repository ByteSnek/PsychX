package xyz.snaker.tq.client.render.type;

import java.util.function.Supplier;

import xyz.snaker.snakerlib.client.render.processor.SimpleRenderTypeProcessor;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.client.Shaders;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.datafixers.util.Pair;

/**
 * Created by SnakerBone on 12/08/2023
 **/
public enum ItemLikeRenderType implements SimpleRenderTypeProcessor
{
    BLACK_STARS(Shaders::getBlackStars),
    WHITE_STARS(Shaders::getWhiteStars),
    RED_STARS(Shaders::getRedStars),
    GREEN_STARS(Shaders::getGreenStars),
    BLUE_STARS(Shaders::getBlueStars),
    YELLOW_STARS(Shaders::getYellowStars),
    PINK_STARS(Shaders::getPinkStars),
    PURPLE_STARS(Shaders::getPurpleStars),
    PULSE(Shaders::getPulse),
    FIRE(Shaders::getFire),
    BLUR_FOG(Shaders::getBlurFog),
    PLAIN(Shaders::getPlain),
    SWIRL(Shaders::getSwirl),
    SNOWFLAKE(Shaders::getSnowflake),
    WATERCOLOUR(Shaders::getWatercolour),
    MULTICOLOUR(Shaders::getMulticolour),
    CLIP(Shaders::getClip),
    BURN(Shaders::getBurn);

    private final Supplier<ShaderInstance> shader;
    private final RenderType type;

    ItemLikeRenderType(Supplier<ShaderInstance> shader)
    {
        this.shader = shader;
        this.type = create(Tourniqueted.MODID + ":" + name().toLowerCase(), new Pair<>(DefaultVertexFormat.BLOCK, normal(shader)));
    }

    public Supplier<ShaderInstance> supplier()
    {
        return shader;
    }

    public ShaderInstance shader()
    {
        return shader.get();
    }

    public RenderType get()
    {
        return type;
    }
}
