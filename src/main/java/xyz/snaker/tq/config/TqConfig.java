package xyz.snaker.tq.config;

import net.minecraftforge.common.ForgeConfigSpec;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Created by SnakerBone on 16/07/2023
 **/
public class TqConfig
{
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    static {
        Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(TqConfig.Common::new);
        Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(TqConfig.Client::new);

        COMMON = commonSpecPair.getLeft();
        COMMON_SPEC = commonSpecPair.getRight();

        CLIENT = clientSpecPair.getLeft();
        CLIENT_SPEC = clientSpecPair.getRight();
    }

    public static class Common
    {
        public final ForgeConfigSpec.BooleanValue visionConvolveActive;

        public Common(ForgeConfigSpec.Builder builder)
        {
            builder.push("common");

            visionConvolveActive = builder.comment("Should the vision convolve effect always be active when in the comatose dimension (default: true)").define("visionConvolveActive", true);

            builder.pop();
        }
    }

    public static class Client
    {
        public final ForgeConfigSpec.ConfigValue<String> syncopeColour;
        public final ForgeConfigSpec.DoubleValue syncopeIntensity;
        public final ForgeConfigSpec.DoubleValue syncopeSaturation;

        public final ForgeConfigSpec.ConfigValue<String> visionConvolveColour;
        public final ForgeConfigSpec.DoubleValue visionConvolveIntensity;
        public final ForgeConfigSpec.DoubleValue visionConvolveSaturation;

        public Client(ForgeConfigSpec.Builder builder)
        {
            builder.push("client");

            syncopeColour = builder.comment("Syncope hex colour (default: FF00FF)").define("syncopeColour", "FF00FF");
            syncopeIntensity = builder.comment("Syncope intensity (default: 0.99)").defineInRange("syncopeIntensity", 0.99, 0.0, 1.0);
            syncopeSaturation = builder.comment("Syncope colour saturation (default: 1.0)").defineInRange("syncopeSaturation", 1.0, 0.0, 1.0);

            visionConvolveColour = builder.comment("Vision convolve hex colour (default: FF00FF)").define("visionConvolveColour", "FF00FF");
            visionConvolveIntensity = builder.comment("Vision convolve intensity (default: 0.99)").defineInRange("visionConvolveIntensity", 0.99, 0.0, 1.0);
            visionConvolveSaturation = builder.comment("Vision convolve saturation (default: 1.0)").defineInRange("visionConvolveSaturation", 1.0, 0.0, 1.0);

            builder.pop();
        }
    }
}
