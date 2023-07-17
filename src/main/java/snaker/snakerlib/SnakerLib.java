package snaker.snakerlib;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;
import snaker.snakerlib.config.SnakerConfig;
import snaker.snakerlib.internal.*;
import snaker.snakerlib.internal.log4j.Log4jFilter;
import snaker.snakerlib.level.entity.SnakerBoss;
import snaker.snakerlib.math.Mh;

import java.io.File;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/**
 * Created by SnakerBone on 5/05/2023
 **/
public class SnakerLib
{
    private volatile boolean notify = true;

    private static long clientTickCount = 0;
    private static long serverTickCount = 0;

    public static final Component VIRTUAL_MACHINE_FORCE_CRASH_KEYBINDS_PRESSED = Component.literal("Left shift and F4 pressed.");
    public static final Component DISABLE_IN_CONFIG = Component.literal("You can disable this in the config (snakerlib-common.toml) if you wish");
    public static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    public static final SnakerLogger LOGGER = SnakerLoggerManager.getLogger(SnakerLib.getCallerClassReference());
    public static final Log4jFilter FILTER = new Log4jFilter();

    public static final String MODID = "snakerlib";
    public static final String SNAKERBONE_MODID = "snakerbone";
    public static final String TORNIQUETED_MODID = "tq";

    public static Path runFolder;

    public static final String[] DATAFLOW_ISSUES = {
            "Incorrect Key ", "Registry ", "Channel ", "Holder ", "Applying ", "[forge] ",
            "Could not authorize you against Realms server: Invalid session id",
            "Shader rendertype_entity_translucent_emissive could not find sampler named Sampler2 in the specified shader program.",
            "Missing sound for event: minecraft:entity.goat.screaming.horn_break",
            "Missing sound for event: minecraft:item.goat_horn.play",
            "Shader color_convolve could not find uniform named InSize in the specified shader program.",
            "Shader phosphor could not find uniform named InSize in the specified shader program.",
            "Unable to parse the boolean system property 'java.net.preferIPv6Addresses':system - using the default value: false"
    };

    public SnakerLib()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::configLoadEvent);
        bus.addListener(this::modSetupEvent);

        //DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> SnakerSprites::initialize);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SnakerConfig.COMMON_SPEC, "snakerlib-common.toml");
        SnakerConfig.load(SnakerConfig.COMMON_SPEC, FMLPaths.CONFIGDIR.get().resolve("snakerlib-common.toml").toString());

        Log4jFilter.applicate(FILTER);
    }

    public static void initialize()
    {
        MinecraftForge.EVENT_BUS.register(new SnakerLib());
    }

    /**
     * Checks if a key is being pressed
     *
     * @param key A {@link GLFW} printable key
     * @return True if the key is currently being pressed
     **/
    @Via(Accessibility.CLIENT)
    public static boolean isKeyDown(int key)
    {
        return GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), key) == GLFW.GLFW_PRESS;
    }

    public static boolean tickOffs(float tickOffset)
    {
        return getClientTickCount() % tickOffset == 0;
    }

    public static boolean tickOffs(float other, float tickOffset)
    {
        return other % tickOffset == 0;
    }

    public static boolean secOffs(int secOffset)
    {
        return getClientTickCount() % Mh.secondsToTicks(secOffset) == 0;
    }

    public static boolean secOffs(float other, int secOffset)
    {
        return other % Mh.secondsToTicks(secOffset) == 0;
    }

    public static Class<?> getCallerClassReference()
    {
        return STACK_WALKER.getCallerClass();
    }

    public static ClassLoader getCallerClassLoaderReference()
    {
        return getCallerClassReference().getClassLoader();
    }

    @SafeVarargs
    public static <V> V randomFromObjects(final RandomSource random, V... values)
    {
        return random.nextBoolean() ? values[random.nextInt(1, values.length) % values.length] : values[random.nextInt(values.length)];
    }

    @SafeVarargs
    public static <V> V randomFromObjects(final RandomSource random, boolean copy, V... values)
    {
        if (copy) {
            return random.nextBoolean() ? randomFromObjects(random, values) : random.nextBoolean() ? values[random.nextInt(1, values.length) % values.length] : values[random.nextInt(values.length)];
        } else {
            return randomFromObjects(random, values);
        }
    }

    public static <V> V[] filterEnumValues(V[] values, Predicate<? super V> filter, IntFunction<V[]> function)
    {
        if (values != null && values.length > 0) {
            return Arrays.stream(values).filter(filter).toArray(function);
        } else {
            SnakerLib.LOGGER.error("Invalid enum or enum values");
            return null;
        }
    }

    public static <S extends AbstractSet<V>, V> void populateSet(S set, V value)
    {
        if (set.isEmpty()) {
            set.add(value);
        } else {
            set.clear();
            set.add(value);
        }
    }

    public static <M extends AbstractMap<K, V>, K, V> void populateMap(M map, K key, V value)
    {
        if (map.isEmpty()) {
            map.put(key, value);
        } else {
            map.clear();
            map.put(key, value);
        }
    }

    /**
     * Force crashes the JVM by starving its memory
     * <p>
     * Extremely dangerous, inefficient and nothing is saved or backed up during the process
     * <p>
     * <b>There should be no reason at all to be implementing this method outside of the developer environment</b>
     *
     * @param reason The reason for crashing so it can be logged if implemented
     **/
    public static void forceCrashJVM(String reason)
    {
        String clazz = SnakerLib.getCallerClassReference().toString();
        String regex = ".*[a-zA-Z]+.*";
        String br = "\n";
        StringBuilder builder = new StringBuilder("-+-");
        builder.append("-+-".repeat(36));
        SnakerLib.LOGGER.worker("JVM crash detected on " + Thread.currentThread().getName());
        SnakerLib.LOGGER.fatal(String.format(br + "%24s" + br, builder), ColourCode.RED, MarkerType.NONE);
        SnakerLib.LOGGER.fatal(String.format("The JVM was forcefully crashed by %s" + br, clazz), ColourCode.RED, MarkerType.NONE);
        if (!clazz.contains("SnakerLib")) {
            SnakerLib.LOGGER.fatal(String.format("The JVM was not crashed by SnakerLib. Please go report it to %s" + br, clazz), ColourCode.RED, MarkerType.NONE);
        }
        if (!reason.matches(regex)) {
            SnakerLib.LOGGER.fatal("The reason for the crash was not specified" + br, ColourCode.RED, MarkerType.NONE);
        } else {
            SnakerLib.LOGGER.fatal(String.format("The reason was: %s" + br, reason), ColourCode.RED, MarkerType.NONE);
        }
        SnakerLib.LOGGER.fatal(String.format("%24s", builder), ColourCode.RED, MarkerType.NONE);
        MemoryUtil.memSet(0, 0, 1);
    }

    public static long getClientTickCount()
    {
        return clientTickCount;
    }

    public static long getServerTickCount()
    {
        return serverTickCount;
    }

    public static long getVMNanosecondsCount()
    {
        return (long) (GLFW.glfwGetTime() * 1000000000);
    }

    public static long getVMMicrosecondsCount()
    {
        return (long) (GLFW.glfwGetTime() * 1000000);
    }

    public static long getVMMillisecondsCount()
    {
        return (long) (GLFW.glfwGetTime() * 1000);
    }

    public static long getVMTickCount()
    {
        return (long) (GLFW.glfwGetTime() * 20);
    }

    public static long getVMSecondCount()
    {
        return (long) GLFW.glfwGetTime();
    }

    public static long getVMMinuteCount()
    {
        return (long) (GLFW.glfwGetTime() / 60);
    }

    public static long getVMHourCount()
    {
        return (long) (GLFW.glfwGetTime() / 3600);
    }

    public static long getVMDayCount()
    {
        return (long) (GLFW.glfwGetTime() / 86400);
    }

    public static long getVMWeekCount()
    {
        return (long) (GLFW.glfwGetTime() / 604800);
    }

    public static long getVMMonthCount()
    {
        return (long) (GLFW.glfwGetTime() / 2628000);
    }

    public static long getVMYearCount()
    {
        return (long) (GLFW.glfwGetTime() / 31536000);
    }

    public static long getVMDecadeCount()
    {
        return (long) (GLFW.glfwGetTime() / 315360000);
    }

    public static long getVMMillenniumCount()
    {
        return (long) (GLFW.glfwGetTime() / 3153600000L);
    }

    public static long getVMCenturieCount()
    {
        return (long) (GLFW.glfwGetTime() / 31536000000L);
    }

    public static long getVMMillenniaCount()
    {
        return (long) (GLFW.glfwGetTime() / 315360000000L);
    }

    public static long getVMDecamillenniaCount()
    {
        return (long) (GLFW.glfwGetTime() / 3153600000000L);
    }

    public static long getVMHectomillenniaCount()
    {
        return (long) (GLFW.glfwGetTime() / 31536000000000L);
    }

    public static long getVMKilomillenniaCount()
    {
        return (long) (GLFW.glfwGetTime() / 315360000000000L);
    }

    public static long getVMMegamillenniaCount()
    {
        return (long) (GLFW.glfwGetTime() / 3153600000000000L);
    }

    public static long getVMTeramillenniaCount()
    {
        return (long) (GLFW.glfwGetTime() / 31536000000000000L);
    }

    public static long getVMPetamillenniaCount()
    {
        return (long) (GLFW.glfwGetTime() / 315360000000000000L);
    }

    public static long getVMExamillenniaCount()
    {
        return (long) (GLFW.glfwGetTime() / 3153600000000000000L);
    }

    /**
     * Deletes any JVM crash files that are present in the run directory
     **/
    public static void deleteJVMCrashFiles()
    {
        if (runFolder != null) {
            File file = new File(runFolder.toUri());
            File[] files = file.listFiles();
            if (files != null) {
                for (File jvmFile : files) {
                    if (jvmFile.getName().startsWith("hs")) {
                        if (jvmFile.delete()) {
                            SnakerLib.LOGGER.system(String.format("Successfully deleted JVM crash file '%s'", jvmFile.getName()), ColourCode.WHITE, MarkerType.SYSTEM);
                        } else {
                            SnakerLib.LOGGER.system(String.format("Could not delete JVM crash file '%s'", jvmFile.getName()), ColourCode.WHITE, MarkerType.SYSTEM);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    protected void clientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase == TickEvent.Phase.END || !Minecraft.getInstance().isPaused()) {
            if (isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
                if (isKeyDown(GLFW.GLFW_KEY_F4)) {
                    if (SnakerConfig.COMMON.forceCrashJvmKeyBindings.get()) {
                        forceCrashJVM(VIRTUAL_MACHINE_FORCE_CRASH_KEYBINDS_PRESSED.getString() + " " + DISABLE_IN_CONFIG.getString());
                    }
                }
            }
            clientTickCount++;
        }
    }

    public void modSetupEvent(FMLCommonSetupEvent event)
    {
        File runDir = new File(runFolder.toUri());
        File crashDir = new File(runFolder.resolve("crash-reports").toUri());
        File[] runFiles = runDir.listFiles();
        File[] crashFiles = crashDir.listFiles();
        if (SnakerConfig.COMMON.removeJvmCrashFilesOnStartup.get()) {
            if (runFiles != null) {
                if (Arrays.stream(runFiles).anyMatch(f -> f.getName().startsWith("hs"))) {
                    deleteJVMCrashFiles();
                }
            }
        }
        if (SnakerConfig.COMMON.removeMinecraftCrashFilesOnStartup.get()) {
            if (crashFiles != null) {
                for (File crashFile : crashFiles) {
                    if (crashFile.delete()) {
                        SnakerLib.LOGGER.system(String.format("Successfully deleted crash file '%s'", crashFile.getName()), ColourCode.WHITE, MarkerType.SYSTEM);
                    } else {
                        SnakerLib.LOGGER.system(String.format("Could not delete crash file '%s'", crashFile.getName()), ColourCode.WHITE, MarkerType.SYSTEM);
                    }
                }
            }
        }
    }

    public void configLoadEvent(ModConfigEvent.Loading event)
    {
        runFolder = event.getConfig().getFullPath().getParent().getParent();
    }

    @SubscribeEvent
    protected void serverTick(TickEvent.ServerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.END) {
            serverTickCount++;
        }
    }

    @SubscribeEvent
    protected void serverStopped(LevelSavingEvent event) throws InterruptedException
    {
        var bosses = SnakerBoss.BOSS_INSTANCES;
        if (!bosses.isEmpty()) {
            for (SnakerBoss boss : new CopyOnWriteArrayList<>(bosses)) {
                boss.discard();
                if (notify) {
                    SnakerLib.LOGGER.info(String.format("Successfully discarded %s bosses", bosses.size()));
                    notify = false;
                }
            }
            Thread.sleep(500);
            bosses.clear();
            notify = true;
        }
    }
}