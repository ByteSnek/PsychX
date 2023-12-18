package xyz.snaker.tq.client.renderer.type;

import java.util.function.Supplier;

import xyz.snaker.snakerlib.client.render.SRTP;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.client.Shaders;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.util.Pair;

/**
 * Created by SnakerBone on 12/08/2023
 **/
public enum EntityRenderType implements SRTP
{
    BLACK_STARS(Shaders::getBlackStars, DefaultVertexFormat.POSITION_TEX, true),
    WHITE_STARS(Shaders::getWhiteStars, DefaultVertexFormat.POSITION_TEX, true),
    RED_STARS(Shaders::getRedStars, DefaultVertexFormat.POSITION_TEX, true),
    GREEN_STARS(Shaders::getGreenStars, DefaultVertexFormat.POSITION_TEX, true),
    BLUE_STARS(Shaders::getBlueStars, DefaultVertexFormat.POSITION_TEX, true),
    YELLOW_STARS(Shaders::getYellowStars, DefaultVertexFormat.POSITION_TEX, true),
    PINK_STARS(Shaders::getPinkStars, DefaultVertexFormat.POSITION_TEX, true),
    PURPLE_STARS(Shaders::getPurpleStars, DefaultVertexFormat.POSITION_TEX, true),
    FIRE(Shaders::getFire, DefaultVertexFormat.POSITION_TEX, true),
    SWIRL(Shaders::getSwirly, DefaultVertexFormat.POSITION_TEX, true),
    PULSE(Shaders::getPulse, DefaultVertexFormat.POSITION_TEX, false);

    private final Supplier<ShaderInstance> shader;
    private final RenderType type;

    EntityRenderType(Supplier<ShaderInstance> shader, VertexFormat format, boolean solid)
    {
        this.shader = shader;
        this.type = create(Tourniqueted.MODID + ":" + name().toLowerCase(), new Pair<>(format, solid ? entity(shader) : translucent(shader)));
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
