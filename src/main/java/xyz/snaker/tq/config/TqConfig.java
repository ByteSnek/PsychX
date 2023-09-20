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
        public Client(ForgeConfigSpec.Builder builder)
        {
            builder.push("client");
            builder.pop();
        }
    }
}
