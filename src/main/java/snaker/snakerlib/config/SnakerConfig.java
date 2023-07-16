package snaker.snakerlib.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import snaker.snakerlib.SnakerLib;

import java.io.File;
import java.util.List;

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

    public static void load(ForgeConfigSpec config, String path)
    {
        CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }

    public static class Common
    {
        public final ForgeConfigSpec.BooleanValue playerVulnerableInCreative;
        public final ForgeConfigSpec.BooleanValue forceCrashJvmKeyBindings;
        public final ForgeConfigSpec.BooleanValue removeJvmCrashFilesOnStartup;
        public final ForgeConfigSpec.BooleanValue removeMinecraftCrashFilesOnStartup;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> regexLogFilter;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> textLogFilter;

        public Common(ForgeConfigSpec.Builder builder)
        {
            builder.push("common");
            playerVulnerableInCreative = builder.comment("Should mobs target the player when in creative mode (default: true)").define("playerVulnerableInCreative", true);
            forceCrashJvmKeyBindings = builder.comment("Should Left Shift + F4 force crash the JVM for debugging purposes (default: true)").define("forceCrashJvmKeyBindings", true);
            removeJvmCrashFilesOnStartup = builder.comment("Should JVM crash files be deleted on startup for optimization purposes (default: true)").define("removeJvmCrashFilesOnStartup", true);
            removeMinecraftCrashFilesOnStartup = builder.comment("Should Minecraft crash files be deleted on startup for optimization purposes (default: false)").define("removeMinecraftCrashFilesOnStartup", false);
            regexLogFilter = builder.comment("Regex phrases to filter out from the console, It's recommended to keep these presets and just add onto them to better improve performance").defineList("regexLogFilter", Lists.newArrayList("Registry ", "Channel "), o -> true);
            textLogFilter = builder.comment("Text phrases to filter out from the console, It's recommended to keep these presets and just add onto them to better improve performance").defineList("textLogFilter", Lists.newArrayList(SnakerLib.DATAFLOW_ISSUES), o -> true);
            builder.pop();
        }
    }
}
