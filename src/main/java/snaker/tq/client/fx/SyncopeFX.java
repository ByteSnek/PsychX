package snaker.tq.client.fx;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import snaker.snakerlib.resources.Identifier;
import snaker.tq.rego.Rego;

/**
 * Created by SnakerBone on 6/07/2023
 **/
public class SyncopeFX
{
    public static final ResourceLocation SYNCOPE_SHADER = new Identifier("shaders/post/syncope.json");
    public boolean effectActiveLastTick = false;

    @SubscribeEvent
    public void renderTick(TickEvent.PlayerTickEvent event)
    {
        if (event.player == Minecraft.getInstance().player) {
            MobEffectInstance effectInstance = event.player.getEffect(Rego.EFFECT_SYNCOPE.get());
            int duration = effectInstance == null ? 0 : effectInstance.getDuration();
            if (duration > 1) {
                if (!effectActiveLastTick) {
                    effectActiveLastTick = true;
                    Minecraft.getInstance().tell(() -> Minecraft.getInstance().gameRenderer.loadEffect(SYNCOPE_SHADER));
                }
            } else {
                if (effectActiveLastTick) {
                    effectActiveLastTick = false;
                    Minecraft.getInstance().tell(() -> Minecraft.getInstance().gameRenderer.shutdownEffect());
                }
            }
        }
    }


    //public static class Serializer
    //{
    //    public static final String PATH = "src/main/resources/assets/tq/shaders/post/syncope.json";
    //    public static final Serializer INSTANCE = new Serializer();

    //    public void setColourConvolveSaturation(double value)
    //    {
    //        try {
    //            JsonObject root = JsonParser.parseReader(new FileReader(PATH)).getAsJsonObject();
    //            JsonArray passes = root.getAsJsonArray("passes");
    //            JsonObject colourConvolve = passes.get(0).getAsJsonObject();
    //            JsonArray colourConvolveUniforms = colourConvolve.getAsJsonArray("uniforms");
    //            JsonObject colourConvolveUniform = colourConvolveUniforms.get(0).getAsJsonObject();
    //            JsonArray colourConvolverUniformValues = colourConvolveUniform.getAsJsonArray("values");

    //            colourConvolverUniformValues.set(0, new JsonPrimitive(value));

    //            try (FileWriter writer = new FileWriter(PATH)) {
    //                Gson gson = new GsonBuilder().setPrettyPrinting().create();
    //                gson.toJson(root, writer);
    //            } catch (Exception e) {
    //                e.printStackTrace();
    //            }

    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //    }

    //    public void setPhosphorIntensity(double value)
    //    {
    //        try {
    //            JsonObject root = JsonParser.parseReader(new FileReader(PATH)).getAsJsonObject();
    //            JsonArray passes = root.getAsJsonArray("passes");
    //            JsonObject phosophor = passes.get(3).getAsJsonObject();
    //            JsonArray phosphorUniforms = phosophor.getAsJsonArray("uniforms");
    //            JsonObject phosphorUniform = phosphorUniforms.get(0).getAsJsonObject();
    //            JsonArray phosphorUniformValues = phosphorUniform.getAsJsonArray("values");

    //            for (int i = 0; i < phosphorUniformValues.size(); i++) {
    //                phosphorUniformValues.set(i, new JsonPrimitive(value));
    //            }

    //            try (FileWriter writer = new FileWriter(PATH)) {
    //                Gson gson = new GsonBuilder().setPrettyPrinting().create();
    //                gson.toJson(root, writer);
    //            } catch (Exception e) {
    //                e.printStackTrace();
    //            }

    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //    }
    //}
}
