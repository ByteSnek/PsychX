package xyz.snaker.tq.client;

import java.util.function.Consumer;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.client.shader.Shader;
import xyz.snaker.snakerlib.utility.ResourcePath;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class Shaders
{
    private static Shader swirl;
    private static Uniform swirlTime;

    private static Shader snowflake;
    private static Uniform snowflakeTime;

    private static Shader watercolour;
    private static Uniform watercolourTime;

    private static Shader multicolour;
    private static Uniform multicolourTime;

    private static Shader stars;
    private static Uniform starsTime, starsColour, starsInvert, starsBackground;

    private static Shader blackStars;
    private static Uniform blackStarsTime, blackStarsColour, blackStarsInvert, blackStarsBackground;

    private static Shader whiteStars;
    private static Uniform whiteStarsTime, whiteStarsColour, whiteStarsInvert, whiteStarsBackground;

    private static Shader redStars;
    private static Uniform redStarsTime, redStarsColour, redStarsInvert, redStarsBackground;

    private static Shader greenStars;
    private static Uniform greenStarsTime, greenStarsColour, greenStarsInvert, greenStarsBackground;

    private static Shader blueStars;
    private static Uniform blueStarsTime, blueStarsColour, blueStarsInvert, blueStarsBackground;

    private static Shader yellowStars;
    private static Uniform yellowStarsTime, yellowStarsColour, yellowStarsInvert, yellowStarsBackground;

    private static Shader pinkStars;
    private static Uniform pinkStarsTime, pinkStarsColour, pinkStarsInvert, pinkStarsBackground;

    private static Shader purpleStars;
    private static Uniform purpleStarsTime, purpleStarsColour, purpleStarsInvert, purpleStarsBackground;

    private static Shader fire;
    private static Uniform fireTime;

    private static Shader plain;
    private static Uniform plainColour;

    private static Shader blurFog;
    private static Uniform blurFogTime, blurFogHSV, blurFogRGB, blurFogIntensity;

    private static Shader pulse;
    private static Uniform pulseTime, pulseRGB, pulseAlpha;

    private static Shader crystalized;
    private static Uniform crystalizedTime, crystalizedLayers, crystalizedDensityRatio;

    private static void register(RegisterShadersEvent event)
    {
        registerShader(event, "swirl", shaderInstance ->
        {
            swirl = (Shader) shaderInstance;
            swirlTime = swirl.getUniform("Time");
            swirl.enqueueTask(() -> swirlTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F));
        });

        registerShader(event, "snowflake", shaderInstance ->
        {
            snowflake = (Shader) shaderInstance;
            snowflakeTime = snowflake.getUniform("Time");
            snowflake.enqueueTask(() -> snowflakeTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F));
        });

        registerShader(event, "watercolour", shaderInstance ->
        {
            watercolour = (Shader) shaderInstance;
            watercolourTime = watercolour.getUniform("Time");
            watercolour.enqueueTask(() -> watercolourTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F));
        });

        registerShader(event, "multicolour", shaderInstance ->
        {
            multicolour = (Shader) shaderInstance;
            multicolourTime = multicolour.getUniform("Time");
            multicolour.enqueueTask(() -> multicolourTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F));
        });

        registerShader(event, "fire", shaderInstance ->
        {
            fire = (Shader) shaderInstance;
            fireTime = fire.getUniform("Time");
            fire.enqueueTask(() -> fireTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F));
        });

        registerShader(event, "stars", shaderInstance ->
        {
            stars = (Shader) shaderInstance;
            starsTime = stars.getUniform("Time");
            starsColour = stars.getUniform("Colour");
            starsBackground = stars.getUniform("Background");
            starsInvert = stars.getUniform("Invert");
            stars.enqueueTask(() -> starsTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F));
        });

        registerShader(event, "stars", shaderInstance ->
        {
            blackStars = (Shader) shaderInstance;
            blackStarsTime = blackStars.getUniform("Time");
            blackStarsColour = blackStars.getUniform("Colour");
            blackStarsBackground = blackStars.getUniform("Background");
            blackStarsInvert = blackStars.getUniform("Invert");
            blackStars.enqueueTask(() ->
            {
                blackStarsTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F);
                blackStarsColour.set(1F, 1F, 1F);
                blackStarsBackground.set(0);
                blackStarsInvert.set(0);
            });
        });

        registerShader(event, "stars", shaderInstance ->
        {
            whiteStars = (Shader) shaderInstance;
            whiteStarsTime = whiteStars.getUniform("Time");
            whiteStarsColour = whiteStars.getUniform("Colour");
            whiteStarsBackground = whiteStars.getUniform("Background");
            whiteStarsInvert = whiteStars.getUniform("Invert");
            whiteStars.enqueueTask(() ->
            {
                whiteStarsTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F);
                whiteStarsColour.set(1F, 1F, 1F);
                whiteStarsBackground.set(0);
                whiteStarsInvert.set(1);
            });
        });

        registerShader(event, "stars", shaderInstance ->
        {
            redStars = (Shader) shaderInstance;
            redStarsTime = redStars.getUniform("Time");
            redStarsColour = redStars.getUniform("Colour");
            redStarsBackground = redStars.getUniform("Background");
            redStarsInvert = redStars.getUniform("Invert");
            redStars.enqueueTask(() ->
            {
                redStarsTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F);
                redStarsColour.set(0.85F, 0.1F, 0.25F);
                redStarsBackground.set(1);
                redStarsInvert.set(0);
            });
        });

        registerShader(event, "stars", shaderInstance ->
        {
            greenStars = (Shader) shaderInstance;
            greenStarsTime = greenStars.getUniform("Time");
            greenStarsColour = greenStars.getUniform("Colour");
            greenStarsBackground = greenStars.getUniform("Background");
            greenStarsInvert = greenStars.getUniform("Invert");
            greenStars.enqueueTask(() ->
            {
                greenStarsTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F);
                greenStarsColour.set(0.5F, 1F, 0.25F);
                greenStarsBackground.set(1);
                greenStarsInvert.set(0);
            });
        });

        registerShader(event, "stars", shaderInstance ->
        {
            blueStars = (Shader) shaderInstance;
            blueStarsTime = blueStars.getUniform("Time");
            blueStarsColour = blueStars.getUniform("Colour");
            blueStarsBackground = blueStars.getUniform("Background");
            blueStarsInvert = blueStars.getUniform("Invert");
            blueStars.enqueueTask(() ->
            {
                blueStarsTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F);
                blueStarsColour.set(0F, 0.5F, 1F);
                blueStarsBackground.set(1);
                blueStarsInvert.set(0);
            });
        });

        registerShader(event, "stars", shaderInstance ->
        {
            yellowStars = (Shader) shaderInstance;
            yellowStarsTime = yellowStars.getUniform("Time");
            yellowStarsColour = yellowStars.getUniform("Colour");
            yellowStarsBackground = yellowStars.getUniform("Background");
            yellowStarsInvert = yellowStars.getUniform("Invert");
            yellowStars.enqueueTask(() ->
            {
                yellowStarsTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F);
                yellowStarsColour.set(1F, 1F, 0F);
                yellowStarsBackground.set(1);
                yellowStarsInvert.set(0);
            });
        });

        registerShader(event, "stars", shaderInstance ->
        {
            pinkStars = (Shader) shaderInstance;
            pinkStarsTime = pinkStars.getUniform("Time");
            pinkStarsColour = pinkStars.getUniform("Colour");
            pinkStarsBackground = pinkStars.getUniform("Background");
            pinkStarsInvert = pinkStars.getUniform("Invert");
            pinkStars.enqueueTask(() ->
            {
                pinkStarsTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F);
                pinkStarsColour.set(1F, 0.5F, 0.8F);
                pinkStarsBackground.set(1);
                pinkStarsInvert.set(0);
            });
        });

        registerShader(event, "stars", shaderInstance ->
        {
            purpleStars = (Shader) shaderInstance;
            purpleStarsTime = purpleStars.getUniform("Time");
            purpleStarsTime = purpleStars.getUniform("Time");
            purpleStarsColour = purpleStars.getUniform("Colour");
            purpleStarsBackground = purpleStars.getUniform("Background");
            purpleStarsInvert = purpleStars.getUniform("Invert");
            purpleStars.enqueueTask(() ->
            {
                purpleStarsTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F);
                purpleStarsColour.set(0.6F, 0F, 0.8F);
                purpleStarsBackground.set(1);
                purpleStarsInvert.set(0);
            });
        });

        registerShader(event, "plain", shaderInstance ->
        {
            plain = (Shader) shaderInstance;
            plainColour = plain.getUniform("Colour");
            plain.enqueueTask(() -> plainColour.set(0F, 0F, 0F, 0F));
        });

        registerShader(event, "blur_fog", shaderInstance ->
        {
            blurFog = (Shader) shaderInstance;
            blurFogTime = blurFog.getUniform("Time");
            blurFogIntensity = blurFog.getUniform("Intensity");
            blurFogHSV = blurFog.getUniform("HSV");
            blurFogRGB = blurFog.getUniform("RGB");
            blurFog.enqueueTask(() -> blurFogTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F));
        });

        registerShader(event, "pulse", shaderInstance ->
        {
            pulse = (Shader) shaderInstance;
            pulseTime = pulse.getUniform("Time");
            pulseRGB = pulse.getUniform("RGB");
            pulseAlpha = pulse.getUniform("Alpha");
            pulse.enqueueTask(() -> pulseTime.set((SnakerLib.getClientTickCount() + Minecraft.getInstance().getFrameTime()) / 20F));
        });

        registerShader(event, "crystalized", shaderInstance ->
        {
            crystalized = (Shader) shaderInstance;
            crystalizedTime = crystalized.getUniform("Time");
            crystalizedLayers = crystalized.getUniform("Layers");
            crystalizedDensityRatio = crystalized.getUniform("DensityRatio");
            crystalized.enqueueTask(() -> crystalizedTime.set(RenderSystem.getShaderGameTime()));
        });
    }

    // ========== Shader Instances ========== //

    public static Shader getSwirl()
    {
        return swirl;
    }

    public static Shader getSnowflake()
    {
        return snowflake;
    }

    public static Shader getWatercolour()
    {
        return watercolour;
    }

    public static Shader getMulticolour()
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

    // ========== Shader Uniforms ========== //

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

    public static Uniform getBlurFogHSV()
    {
        return blurFogHSV;
    }

    public static Uniform getBlurFogRGB()
    {
        return blurFogRGB;
    }

    public static Uniform getBlurFogIntensity()
    {
        return blurFogIntensity;
    }

    public static Uniform getPulseTime()
    {
        return pulseTime;
    }

    public static Uniform getPulseRGB()
    {
        return pulseRGB;
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

    private static Shader shader(RegisterShadersEvent event, String name, VertexFormat format)
    {
        return Shader.create(event.getResourceProvider(), new ResourcePath(name), format);
    }

    private static void registerShader(RegisterShadersEvent event, String name, Consumer<ShaderInstance> instance)
    {
        event.registerShader(shader(event, name, DefaultVertexFormat.POSITION_TEX), instance);
    }

    public static void initialize()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(Shaders::register);
    }
}