package bytesnek.tq.config;

import net.minecraftforge.common.ForgeConfigSpec;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Created by SnakerBone on 16/07/2023
 **/
public class Config
{
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    static {
        Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Config.Common::new);
        Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Config.Client::new);

        COMMON = commonSpecPair.getLeft();
        COMMON_SPEC = commonSpecPair.getRight();

        CLIENT = clientSpecPair.getLeft();
        CLIENT_SPEC = clientSpecPair.getRight();
    }

    public static class Common
    {
        public final ForgeConfigSpec.BooleanValue visionConvolveActive;
        public final ForgeConfigSpec.BooleanValue healthRepairKeybindingsActive;
        public final ForgeConfigSpec.IntValue comaStageProgressionOccurrence;
        public final ForgeConfigSpec.BooleanValue flashBangOverlay;
        public final ForgeConfigSpec.DoubleValue flashBangDuration;

        public Common(ForgeConfigSpec.Builder builder)
        {
            builder.push("common");
            visionConvolveActive = builder.comment("Should the vision convolve effect always be active when in the comatose dimension (default: true)").define("visionConvolveActive", true);
            healthRepairKeybindingsActive = builder.comment("Should SHIFT + Keypad Enter repair the players health if it is NaN (default: false)").define("healthRepairKeybindingsActive", false);
            comaStageProgressionOccurrence = builder.comment("The coma stage progression occurence. The higher the number means the less often it will progress (default: 10)").defineInRange("comaStageProgressionOccurrence", 10, 1, 100);
            flashBangOverlay = builder.comment("Should the flash bang overlay be active (default: true)").define("flashBangOverlay", true);
            flashBangDuration = builder.comment("The duration of the flash bang effect in seconds given off when a Flare dies (default: 5)").defineInRange("flashBangDuration", 5.0, 0.0, 30.0);
            builder.pop();
        }
    }

    public static class Client
    {
        public final ForgeConfigSpec.BooleanValue showComaStage;

        public Client(ForgeConfigSpec.Builder builder)
        {
            builder.push("client");
            showComaStage = builder.comment("Should the coma stage be shown in the top left hand corner of the screen (default: true)").define("showComaStage", true);
            builder.pop();
        }
    }
}
