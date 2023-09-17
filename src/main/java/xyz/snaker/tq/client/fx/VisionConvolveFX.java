package xyz.snaker.tq.client.fx;

import java.io.FileWriter;
import java.net.URL;

import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.snakerlib.utility.tools.RenderStuff;
import xyz.snaker.tq.config.TqConfig;
import xyz.snaker.tq.rego.Effects;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.joml.Matrix3d;
import org.joml.Vector3d;

/**
 * Created by SnakerBone on 17/09/2023
 **/
public class VisionConvolveFX
{
    public static final VisionConvolveFX INSTANCE = new VisionConvolveFX();

    private final ResourceLocation postEffect = new ResourcePath("shaders/post/vision_convolve.json");

    private Matrix3d colourMatrix = new Matrix3d(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0);
    private Vector3d blurVec = new Vector3d(0.99, 0.99, 0.99);
    private double saturation = 1.0;

    private boolean effectActivePreviousTick;

    private VisionConvolveFX() {}

    @SubscribeEvent
    public void renderTick(TickEvent.PlayerTickEvent event)
    {
        if (event.player == Minecraft.getInstance().player) {
            MobEffectInstance effectInstance = event.player.getEffect(Effects.VISION_CONVOLVE.get());
            int duration = effectInstance == null ? 0 : effectInstance.getDuration();
            if (duration > 1) {
                if (!effectActivePreviousTick) {
                    effectActivePreviousTick = true;
                    Minecraft.getInstance().tell(() -> Minecraft.getInstance().gameRenderer.loadEffect(postEffect));
                }
            } else {
                if (effectActivePreviousTick) {
                    effectActivePreviousTick = false;
                    Minecraft.getInstance().tell(() -> Minecraft.getInstance().gameRenderer.shutdownEffect());
                }
            }
        }
    }

    @SubscribeEvent
    public void onResourceManagerReload(AddReloadListenerEvent event)
    {
        URL url = getClass().getClassLoader().getResource("\\assets\\tq\\shaders\\post\\vision_convolve.json");
        if (url != null) {
            String path = url.getPath().replaceAll("/%\\d+!", "");
            colourMatrix = RenderStuff.hexToMatrix3d(TqConfig.CLIENT.visionConvolveColour.get());
            blurVec = new Vector3d(TqConfig.CLIENT.visionConvolveIntensity.get());
            saturation = TqConfig.CLIENT.visionConvolveSaturation.get();
            blurVec.sub(0.1, 0.1, 0.1);
            saturation *= 3;
            Serializer.writeJson(path, colourMatrix, blurVec, saturation);
        }
    }

    private static class Serializer
    {
        public static void writeJson(String path, Matrix3d colours, Vector3d blur, double saturation)
        {
            JsonObject root = new JsonObject();
            JsonArray targets = new JsonArray();

            targets.add("swap");
            targets.add("previous");
            targets.add("a");
            targets.add("b");
            targets.add("c");

            root.add("targets", targets);

            JsonArray passes = new JsonArray();
            JsonObject colourConvolve = new JsonObject();

            colourConvolve.addProperty("name", "color_convolve");
            colourConvolve.addProperty("intarget", "minecraft:main");
            colourConvolve.addProperty("outtarget", "a");

            JsonArray colourConvolveUniforms = new JsonArray();
            JsonObject saturationUniform = new JsonObject();
            JsonArray saturationUniformValues = new JsonArray();

            saturationUniform.addProperty("name", "Saturation");
            saturationUniformValues.add(saturation);
            saturationUniform.add("values", saturationUniformValues);
            colourConvolveUniforms.add(saturationUniform);
            colourConvolve.add("uniforms", colourConvolveUniforms);

            JsonObject colourConvolveMatrix = new JsonObject();

            colourConvolveMatrix.addProperty("name", "color_convolve");
            colourConvolveMatrix.addProperty("intarget", "a");
            colourConvolveMatrix.addProperty("outtarget", "b");

            JsonArray colourConvolveMatrixUniforms = new JsonArray();
            JsonObject colourConvolveMatrixRed = new JsonObject();
            JsonObject colourConvolveMatrixGreen = new JsonObject();
            JsonObject colourConvolveMatrixBlue = new JsonObject();

            colourConvolveMatrixRed.addProperty("name", "RedMatrix");
            colourConvolveMatrixGreen.addProperty("name", "GreenMatrix");
            colourConvolveMatrixBlue.addProperty("name", "BlueMatrix");

            JsonArray redValues = new JsonArray();
            JsonArray greenValues = new JsonArray();
            JsonArray blueValues = new JsonArray();

            redValues.add(colours.m00);
            redValues.add(colours.m01);
            redValues.add(colours.m02);

            greenValues.add(colours.m10);
            greenValues.add(colours.m11);
            greenValues.add(colours.m12);

            blueValues.add(colours.m20);
            blueValues.add(colours.m21);
            blueValues.add(colours.m22);

            colourConvolveMatrixRed.add("values", redValues);
            colourConvolveMatrixGreen.add("values", greenValues);
            colourConvolveMatrixBlue.add("values", blueValues);

            colourConvolveMatrixUniforms.add(colourConvolveMatrixRed);
            colourConvolveMatrixUniforms.add(colourConvolveMatrixGreen);
            colourConvolveMatrixUniforms.add(colourConvolveMatrixBlue);

            colourConvolveMatrix.add("uniforms", colourConvolveMatrixUniforms);

            JsonObject deconverge = new JsonObject();

            deconverge.addProperty("name", "deconverge");
            deconverge.addProperty("intarget", "a");
            deconverge.addProperty("outtarget", "c");

            JsonObject phosphor = new JsonObject();

            phosphor.addProperty("name", "phosphor");
            phosphor.addProperty("intarget", "c");
            phosphor.addProperty("outtarget", "swap");

            JsonArray auxTargets = new JsonArray();
            JsonObject auxTarget = new JsonObject();

            auxTarget.addProperty("name", "PrevSampler");
            auxTarget.addProperty("id", "previous");

            auxTargets.add(auxTarget);
            phosphor.add("auxtargets", auxTargets);

            JsonArray phosphorUniforms = new JsonArray();
            JsonObject phosphorUniform = new JsonObject();
            JsonArray phosphorUniformValues = new JsonArray();

            phosphorUniform.addProperty("name", "Phosphor");

            phosphorUniformValues.add(blur.x);
            phosphorUniformValues.add(blur.y);
            phosphorUniformValues.add(blur.z);

            phosphorUniform.add("values", phosphorUniformValues);
            phosphorUniforms.add(phosphorUniform);
            phosphor.add("uniforms", phosphorUniforms);

            JsonObject blitPrevious = new JsonObject();

            blitPrevious.addProperty("name", "blit");
            blitPrevious.addProperty("intarget", "swap");
            blitPrevious.addProperty("outtarget", "previous");

            JsonObject blitMain = new JsonObject();

            blitMain.addProperty("name", "blit");
            blitMain.addProperty("intarget", "swap");
            blitMain.addProperty("outtarget", "minecraft:main");

            passes.add(colourConvolve);
            passes.add(colourConvolveMatrix);
            passes.add(deconverge);
            passes.add(phosphor);
            passes.add(blitPrevious);
            passes.add(blitMain);

            root.add("passes", passes);

            try (FileWriter writer = new FileWriter(path)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(root, writer);
            } catch (Exception e) {
                SnakerLib.LOGGER.error(e.getMessage());
            }
        }
    }
}
