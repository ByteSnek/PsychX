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
        public final ForgeConfigSpec.BooleanValue syncopeActiveInComatoseDimension;

        public Common(ForgeConfigSpec.Builder builder)
        {
            builder.push("common");
            syncopeActiveInComatoseDimension = builder.comment("Should the syncope effect always be active when in the comatose dimension (default: true)").define("syncopeActiveInComatoseDimension", true);
            builder.pop();
        }
    }

    public static class Client
    {
        public final ForgeConfigSpec.ConfigValue<String> syncopeOverlayColour;
        public final ForgeConfigSpec.DoubleValue syncopePhosphorIntensity;
        public final ForgeConfigSpec.DoubleValue syncopeColourConvolveSaturation;

        public Client(ForgeConfigSpec.Builder builder)
        {
            builder.push("client");
            syncopeOverlayColour = builder.comment("The hex colour of the syncope overlay effect (default: FF00FF)").define("syncopeOverlayColour", "FF00FF");
            syncopePhosphorIntensity = builder.comment("The syncope effect motion blur intensity (default: 0.99)").defineInRange("syncopePhosphorIntensity", 0.99, 0.0, 1.0);
            syncopeColourConvolveSaturation = builder.comment("The syncope effect RGB glitch saturation (default: 1.0)").defineInRange("syncopeColourConvolveSaturation", 1.0, 0.0, 1.0);
            builder.pop();
        }
    }
}
