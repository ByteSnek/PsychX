package xyz.snaker.snakerlib.config;

import net.minecraftforge.common.ForgeConfigSpec;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Created by SnakerBone on 29/05/2023
 **/
public class SnakerConfig
{
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON = specPair.getLeft();
        COMMON_SPEC = specPair.getRight();
    }

    public static class Common
    {
        public final ForgeConfigSpec.BooleanValue playerVulnerableInCreative;
        public final ForgeConfigSpec.BooleanValue forceCrashJvmKeyBindings;
        public final ForgeConfigSpec.BooleanValue removeJvmCrashFilesOnStartup;
        public final ForgeConfigSpec.BooleanValue removeMinecraftCrashFilesOnStartup;

        public Common(ForgeConfigSpec.Builder builder)
        {
            builder.push("common");
            playerVulnerableInCreative = builder.comment("Should mobs target the player when in creative mode (default: true)").define("playerVulnerableInCreative", true);
            forceCrashJvmKeyBindings = builder.comment("Should Left Shift + F4 force crash the JVM for debugging purposes (default: true)").define("forceCrashJvmKeyBindings", true);
            removeJvmCrashFilesOnStartup = builder.comment("Should JVM crash files be deleted on startup for optimization purposes (default: true)").define("removeJvmCrashFilesOnStartup", true);
            removeMinecraftCrashFilesOnStartup = builder.comment("Should Minecraft crash files be deleted on startup for optimization purposes (default: false)").define("removeMinecraftCrashFilesOnStartup", false);
            builder.pop();
        }
    }
}
