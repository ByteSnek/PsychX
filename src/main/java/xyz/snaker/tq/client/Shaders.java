package xyz.snaker.tq.client;

import java.util.function.Consumer;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.client.shader.Shader;
import xyz.snaker.snakerlib.resources.ResourceReference;
import xyz.snaker.snakerlib.utility.Rendering;
import xyz.snaker.snakerlib.utility.unsafe.TheUnsafe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class Shaders
{
    static long time;

    static RegisterShadersEvent delegate;

    static Shader swirl;
    static Uniform swirlTime;

    static Shader snowflake;
    static Uniform snowflakeTime;

    static Shader watercolour;
    static Uniform watercolourTime;

    static Shader multicolour;
    static Uniform multicolourTime;

    static Shader stars;
    static Uniform starsTime, starsColour, starsInvert, starsBackground;

    static Shader blackStars;
    static Uniform blackStarsTime, blackStarsColour, blackStarsInvert, blackStarsBackground;

    static Shader whiteStars;
    static Uniform whiteStarsTime, whiteStarsColour, whiteStarsInvert, whiteStarsBackground;

    static Shader redStars;
    static Uniform redStarsTime, redStarsColour, redStarsInvert, redStarsBackground;

    static Shader greenStars;
    static Uniform greenStarsTime, greenStarsColour, greenStarsInvert, greenStarsBackground;

    static Shader blueStars;
    static Uniform blueStarsTime, blueStarsColour, blueStarsInvert, blueStarsBackground;

    static Shader yellowStars;
    static Uniform yellowStarsTime, yellowStarsColour, yellowStarsInvert, yellowStarsBackground;

    static Shader pinkStars;
    static Uniform pinkStarsTime, pinkStarsColour, pinkStarsInvert, pinkStarsBackground;

    static Shader purpleStars;
    static Uniform purpleStarsTime, purpleStarsColour, purpleStarsInvert, purpleStarsBackground;

    static Shader fire;
    static Uniform fireTime;

    static Shader plain;
    static Uniform plainColour;

    static Shader blurFog;
    static Uniform blurFogTime, blurFogValue, blurFogColour, blurFogIntensity;

    static Shader pulse;
    static Uniform pulseTime, pulseColour, pulseAlpha;

    static Shader crystalized;
    static Uniform crystalizedTime, crystalizedLayers, crystalizedDensityRatio;

    static Shader clip;
    static Uniform clipTime;

    static Shader burn;
    static Uniform burnTime;
    static Uniform burnColour;
    static Uniform burnAlpha;

    static Shader strands;
    static Uniform strandsTime;

    static {
        time = System.currentTimeMillis();
    }

    static void register(RegisterShadersEvent event)
    {
        setDelegate(event);

        registerShader("strands", shaderInstance ->
        {
            strands = TheUnsafe.cast(shaderInstance);
            strandsTime = strands.getTimeUniform(true);
        });

        registerShader("burn", shaderInstance ->
        {
            burn = TheUnsafe.cast(shaderInstance);
            burnTime = burn.getTimeUniform(true);
            burnColour = burn.getColourUniform();
            burnAlpha = burn.getAlphaUniform();
            burn.enqueueTask(() ->
            {
                burnColour.set(Rendering.hexToVec3f("FBA8A1"));
                burnAlpha.set(1F);
            });
        });

        registerShader("clip", shaderInstance ->
        {
            clip = TheUnsafe.cast(shaderInstance);
            clipTime = clip.getTimeUniform(true, 40);
        });

        registerShader("swirl", shaderInstance ->
        {
            swirl = TheUnsafe.cast(shaderInstance);
            swirlTime = swirl.getTimeUniform(true);
        });

        registerShader("snowflake", shaderInstance ->
        {
            snowflake = TheUnsafe.cast(shaderInstance);
            snowflakeTime = snowflake.getTimeUniform(true);
        });

        registerShader("watercolour", shaderInstance ->
        {
            watercolour = TheUnsafe.cast(shaderInstance);
            watercolourTime = watercolour.getTimeUniform(true);
        });

        registerShader("multicolour", shaderInstance ->
        {
            multicolour = TheUnsafe.cast(shaderInstance);
            multicolourTime = multicolour.getTimeUniform(true);
        });

        registerShader("fire", shaderInstance ->
        {
            fire = TheUnsafe.cast(shaderInstance);
            fireTime = fire.getTimeUniform(true);
        });

        registerShader("stars", shaderInstance ->
        {
            stars = TheUnsafe.cast(shaderInstance);
            starsTime = stars.getTimeUniform(true);
            starsColour = stars.getColourUniform();
            starsBackground = stars.getUniform("Background");
            starsInvert = stars.getUniform("Invert");
        });

        registerShader("stars", shaderInstance ->
        {
            blackStars = TheUnsafe.cast(shaderInstance);
            blackStarsTime = blackStars.getTimeUniform();
            blackStarsColour = blackStars.getColourUniform();
            blackStarsBackground = blackStars.getUniform("Background");
            blackStarsInvert = blackStars.getUniform("Invert");
            blackStars.enqueueTask(() ->
            {
                blackStarsTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F);
                blackStarsColour.set(new Vector3f(1F));
                blackStarsBackground.set(0);
                blackStarsInvert.set(0);
            });
        });

        registerShader("stars", shaderInstance ->
        {
            whiteStars = TheUnsafe.cast(shaderInstance);
            whiteStarsTime = whiteStars.getTimeUniform();
            whiteStarsColour = whiteStars.getColourUniform();
            whiteStarsBackground = whiteStars.getUniform("Background");
            whiteStarsInvert = whiteStars.getUniform("Invert");
            whiteStars.enqueueTask(() ->
            {
                whiteStarsTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F);
                whiteStarsColour.set(new Vector3f(1F));
                whiteStarsBackground.set(0);
                whiteStarsInvert.set(1);
            });
        });

        registerShader("stars", shaderInstance ->
        {
            redStars = TheUnsafe.cast(shaderInstance);
            redStarsTime = redStars.getTimeUniform();
            redStarsColour = redStars.getColourUniform();
            redStarsBackground = redStars.getUniform("Background");
            redStarsInvert = redStars.getUniform("Invert");
            redStars.enqueueTask(() ->
            {
                redStarsTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F);
                redStarsColour.set(new Vector3f(0.85F, 0.1F, 0.25F));
                redStarsBackground.set(1);
                redStarsInvert.set(0);
            });
        });

        registerShader("stars", shaderInstance ->
        {
            greenStars = TheUnsafe.cast(shaderInstance);
            greenStarsTime = greenStars.getTimeUniform();
            greenStarsColour = greenStars.getColourUniform();
            greenStarsBackground = greenStars.getUniform("Background");
            greenStarsInvert = greenStars.getUniform("Invert");
            greenStars.enqueueTask(() ->
            {
                greenStarsTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F);
                greenStarsColour.set(new Vector3f(0.5F, 1F, 0.25F));
                greenStarsBackground.set(1);
                greenStarsInvert.set(0);
            });
        });

        registerShader("stars", shaderInstance ->
        {
            blueStars = TheUnsafe.cast(shaderInstance);
            blueStarsTime = blueStars.getTimeUniform();
            blueStarsColour = blueStars.getColourUniform();
            blueStarsBackground = blueStars.getUniform("Background");
            blueStarsInvert = blueStars.getUniform("Invert");
            blueStars.enqueueTask(() ->
            {
                blueStarsTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F);
                blueStarsColour.set(new Vector3f(0F, 0.5F, 1F));
                blueStarsBackground.set(1);
                blueStarsInvert.set(0);
            });
        });

        registerShader("stars", shaderInstance ->
        {
            yellowStars = TheUnsafe.cast(shaderInstance);
            yellowStarsTime = yellowStars.getTimeUniform();
            yellowStarsColour = yellowStars.getColourUniform();
            yellowStarsBackground = yellowStars.getUniform("Background");
            yellowStarsInvert = yellowStars.getUniform("Invert");
            yellowStars.enqueueTask(() ->
            {
                yellowStarsTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F);
                yellowStarsColour.set(new Vector3f(1F, 1F, 0F));
                yellowStarsBackground.set(1);
                yellowStarsInvert.set(0);
            });
        });

        registerShader("stars", shaderInstance ->
        {
            pinkStars = TheUnsafe.cast(shaderInstance);
            pinkStarsTime = pinkStars.getTimeUniform();
            pinkStarsColour = pinkStars.getColourUniform();
            pinkStarsBackground = pinkStars.getUniform("Background");
            pinkStarsInvert = pinkStars.getUniform("Invert");
            pinkStars.enqueueTask(() ->
            {
                pinkStarsTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F);
                pinkStarsColour.set(new Vector3f(1F, 0.5F, 0.8F));
                pinkStarsBackground.set(1);
                pinkStarsInvert.set(0);
            });
        });

        registerShader("stars", shaderInstance ->
        {
            purpleStars = TheUnsafe.cast(shaderInstance);
            purpleStarsTime = purpleStars.getTimeUniform();
            purpleStarsColour = purpleStars.getColourUniform();
            purpleStarsBackground = purpleStars.getUniform("Background");
            purpleStarsInvert = purpleStars.getUniform("Invert");
            purpleStars.enqueueTask(() ->
            {
                purpleStarsTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F);
                purpleStarsColour.set(new Vector3f(0.6F, 0F, 0.8F));
                purpleStarsBackground.set(1);
                purpleStarsInvert.set(0);
            });
        });

        registerShader("plain", shaderInstance ->
        {
            plain = TheUnsafe.cast(shaderInstance);
            plainColour = plain.getColourUniform();
            plain.enqueueTask(() -> plainColour.set(new Vector4f(0)));
        });

        registerShader("blur_fog", shaderInstance ->
        {
            blurFog = TheUnsafe.cast(shaderInstance);
            blurFogTime = blurFog.getTimeUniform(true);
            blurFogIntensity = blurFog.getUniform("Intensity");
            blurFogValue = blurFog.getUniform("Value");
            blurFogColour = blurFog.getColourUniform();
            blurFog.enqueueTask(() ->
            {
                Shaders.getBlurFogColour().set(Rendering.hexToVec3f("3E00A0"));
                Shaders.getBlurFogValue().set(Rendering.hexToVec3f("FFFFFF"));
                Shaders.getBlurFogIntensity().set(5F);
            });
        });

        registerShader("pulse", shaderInstance ->
        {
            pulse = TheUnsafe.cast(shaderInstance);
            pulseTime = pulse.getTimeUniform(true);
            pulseColour = pulse.getColourUniform();
            pulseAlpha = pulse.getAlphaUniform();
        });

        registerShader("crystalized", shaderInstance ->
        {
            crystalized = TheUnsafe.cast(shaderInstance);
            crystalizedTime = crystalized.getTimeUniform();
            crystalizedLayers = crystalized.getUniform("Layers");
            crystalizedDensityRatio = crystalized.getUniform("DensityRatio");
            crystalized.enqueueTask(() -> crystalizedTime.set(RenderSystem.getShaderGameTime()));
        });

        SnakerLib.LOGGER.infof("Registered all shaders in [] seconds", DurationFormatUtils.formatDuration(System.currentTimeMillis() - time, "s.S"));
    }

    private static void setDelegate(RegisterShadersEvent event)
    {
        delegate = event;
    }

    public static Shader getSwirly()
    {
        return swirl;
    }

    public static Shader getWinter()
    {
        return snowflake;
    }

    public static Shader getWcolour()
    {
        return watercolour;
    }

    public static Shader getMcolour()
    {
        return multicolour;
    }

    public static Shader getStars()
    {
        return stars;
    }

    public static Shader getBlackStars()
    {
        return blackStars;
    }

    public static Shader getWhiteStars()
    {
        return whiteStars;
    }

    public static Shader getRedStars()
    {
        return redStars;
    }

    public static Shader getGreenStars()
    {
        return greenStars;
    }

    public static Shader getBlueStars()
    {
        return blueStars;
    }

    public static Shader getYellowStars()
    {
        return yellowStars;
    }

    public static Shader getPinkStars()
    {
        return pinkStars;
    }

    public static Shader getPurpleStars()
    {
        return purpleStars;
    }

    public static Shader getFire()
    {
        return fire;
    }

    public static Shader getPlain()
    {
        return plain;
    }

    public static Shader getBlurFog()
    {
        return blurFog;
    }

    public static Shader getPulse()
    {
        return pulse;
    }

    public static Shader getCrystalized()
    {
        return crystalized;
    }

    public static Uniform getSwirlTime()
    {
        return swirlTime;
    }

    public static Uniform getSnowflakeTime()
    {
        return snowflakeTime;
    }

    public static Uniform getWatercolourTime()
    {
        return watercolourTime;
    }

    public static Uniform getMulticolourTime()
    {
        return multicolourTime;
    }

    public static Uniform getStarsTime()
    {
        return starsTime;
    }

    public static Uniform getStarsColour()
    {
        return starsColour;
    }

    public static Uniform getStarsBackground()
    {
        return starsBackground;
    }

    public static Uniform getStarsInvert()
    {
        return starsInvert;
    }

    public static Uniform getBlackStarsTime()
    {
        return blackStarsTime;
    }

    public static Uniform getWhiteStarsTime()
    {
        return whiteStarsTime;
    }

    public static Uniform getRedStarsTime()
    {
        return redStarsTime;
    }

    public static Uniform getGreenStarsTime()
    {
        return greenStarsTime;
    }

    public static Uniform getBlueStarsTime()
    {
        return blueStarsTime;
    }

    public static Uniform getYellowStarsTime()
    {
        return yellowStarsTime;
    }

    public static Uniform getPinkStarsTime()
    {
        return pinkStarsTime;
    }

    public static Uniform getPurpleStarsTime()
    {
        return purpleStarsTime;
    }

    public static Uniform getFireTime()
    {
        return fireTime;
    }

    public static Uniform getPlainColour()
    {
        return plainColour;
    }

    public static Uniform getBlurFogTime()
    {
        return blurFogTime;
    }

    public static Uniform getBlurFogValue()
    {
        return blurFogValue;
    }

    public static Uniform getBlurFogColour()
    {
        return blurFogColour;
    }

    public static Uniform getBlurFogIntensity()
    {
        return blurFogIntensity;
    }

    public static Uniform getPulseTime()
    {
        return pulseTime;
    }

    public static Uniform getPulseColour()
    {
        return pulseColour;
    }

    public static Uniform getPulseAlpha()
    {
        return pulseAlpha;
    }

    public static Uniform getCrystalizedTime()
    {
        return crystalizedTime;
    }

    public static Uniform getCrystalizedLayers()
    {
        return crystalizedLayers;
    }

    public static Uniform getCrystalizedDensityRatio()
    {
        return crystalizedDensityRatio;
    }

    public static Shader getClip()
    {
        return clip;
    }

    public static Uniform getClipTime()
    {
        return clipTime;
    }

    public static Shader getBurn()
    {
        return burn;
    }

    public static Uniform getBurnTime()
    {
        return burnTime;
    }

    public static Uniform getBurnColour()
    {
        return burnColour;
    }

    public static Uniform getBurnAlpha()
    {
        return burnAlpha;
    }

    public static Shader getStrands()
    {
        return strands;
    }

    public static Uniform getStrandsTime()
    {
        return strandsTime;
    }

    static Shader shader(String name, VertexFormat format)
    {
        return Shader.create(delegate.getResourceProvider(), new ResourceReference(name), format);
    }

    static void registerShader(String name, Consumer<ShaderInstance> instance)
    {
        delegate.registerShader(shader(name, DefaultVertexFormat.POSITION_TEX), instance);
    }

    static void registerShader(String name, VertexFormat format, Consumer<ShaderInstance> instance)
    {
        delegate.registerShader(shader(name, format), instance);
    }

    public static void initialize()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(Shaders::register);
    }
}