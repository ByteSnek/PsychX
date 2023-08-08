package snaker.snakerlib;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.lang3.RandomStringUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;
import snaker.snakerlib.config.SnakerConfig;
import snaker.snakerlib.internal.LevelSavingEvent;
import snaker.snakerlib.internal.Single;
import snaker.snakerlib.internal.StringNuker;
import snaker.snakerlib.internal.log4j.Log4jFilter;
import snaker.snakerlib.internal.log4j.SnakerLogger;
import snaker.snakerlib.internal.log4j.SnakerLoggerManager;
import snaker.snakerlib.level.entity.SnakerBoss;
import snaker.snakerlib.math.Maths;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public static final SnakerLogger LOGGER = SnakerLoggerManager.INSTANCE.apply(SnakerLib.NAME);
    public static final Log4jFilter FILTER = new Log4jFilter();
    public static final Single<String> DELEGATE_MOD = new Single<>();

    public static final String MODID = "snakerlib";
    public static final String NAME = "SnakerLib";

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
        Class<?> clazz = STACK_WALKER.getCallerClass();
        String modid = clazz.getAnnotation(Mod.class).value();
        if (!isInvalidString(modid)) {
            DELEGATE_MOD.set(modid);
            String modName = DELEGATE_MOD.get();
            SnakerLib.LOGGER.infof("Successfully initialized mod '%s' to SnakerLib", modName);
        }
        MinecraftForge.EVENT_BUS.register(new SnakerLib());
    }

    /**
     * Checks if a key is being pressed
     *
     * @param key A {@link GLFW} printable key
     * @return True if the key is currently being pressed
     **/
    public static boolean isKeyDown(int key)
    {
        return GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), key) == GLFW.GLFW_PRESS;
    }

    public static String placeholder(Locale locale, int limit, boolean modid)
    {
        return modid ? MODID + ":" + RandomStringUtils.randomAlphanumeric(limit).toLowerCase(locale) : RandomStringUtils.randomAlphanumeric(limit).toLowerCase(locale);
    }

    public static String placeholder(Locale locale, int limit)
    {
        return placeholder(locale, limit, false);
    }

    public static String placeholder(Locale locale, boolean modid)
    {
        return placeholder(locale, 8, modid);
    }

    public static String placeholder(Locale locale)
    {
        return placeholder(locale, false);
    }

    public static String placeholder()
    {
        return placeholder(Locale.ROOT);
    }

    public static String placeholderWithId()
    {
        return placeholder(Locale.ROOT, true);
    }

    public static String untranslateComponent(MutableComponent component, boolean leaveCaps)
    {
        StringNuker nuker = new StringNuker(component.getString());
        nuker.replaceAllAndDestroy("\\p{P}");
        return leaveCaps ? nuker.result() : nuker.result().toLowerCase();
    }

    public static int hexToInt(String hexCode)
    {
        StringNuker nuker = new StringNuker(hexCode);
        nuker.replaceAllAndDestroy("#");
        return Integer.parseInt(nuker.result(), 16);
    }

    public static float hexToFloat(String hexCode)
    {
        StringNuker nuker = new StringNuker(hexCode);
        nuker.replaceAllAndDestroy("#");
        return Float.parseFloat(nuker.result());
    }

    public static int randomHex()
    {
        Random random = new Random();
        return random.nextInt(0xffffff + 1);
    }

    public static String untranslateComponent(MutableComponent component)
    {
        return untranslateComponent(component, false);
    }

    public static String i18nt(String text)
    {
        if (!text.isEmpty()) {
            return Stream.of(text.trim().split("\\s|\\p{Pc}")).filter(word -> word.length() > 0).map(word -> word.substring(0, 1).toUpperCase() + word.substring(1)).collect(Collectors.joining(" "));
        } else {
            return text;
        }
    }

    public static String i18nt(String text, Rarity rarity)
    {
        switch (rarity) {
            case UNCOMMON -> {
                return "§e" + i18nt(text);
            }
            case RARE -> {
                return "§b" + i18nt(text);
            }
            case EPIC -> {
                return "§d" + i18nt(text);
            }
            default -> {
                return i18nt(text);
            }
        }
    }

    public static String i18nf(String text)
    {
        return !text.isEmpty() ? text.replaceAll("\\s+", "_").toLowerCase() : text;
    }

    public static boolean isInvalidString(String stringToCheck, boolean notify, boolean crash)
    {
        if (stringToCheck == null || stringToCheck.isEmpty()) {
            return true;
        } else {
            String regex = ".*[a-zA-Z]+.*";
            if (!stringToCheck.matches(regex)) {
                if (notify) {
                    SnakerLib.LOGGER.warnf("String '%s' is not a valid string", stringToCheck);
                    if (crash) {
                        throw new RuntimeException(String.format("Invalid string: %s", stringToCheck));
                    }
                }
                if (!notify && crash) {
                    throw new RuntimeException(String.format("Invalid string: %s", stringToCheck));
                }
            }
            return !stringToCheck.matches(regex);
        }
    }

    public static boolean isInvalidString(String string, boolean notify)
    {
        return isInvalidString(string, notify, false);
    }

    public static boolean isInvalidString(String string)
    {
        return isInvalidString(string, false);
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
        return getClientTickCount() % Maths.secondsToTicks(secOffset) == 0;
    }

    public static boolean secOffs(float other, int secOffset)
    {
        return other % Maths.secondsToTicks(secOffset) == 0;
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
     * @param crashReason The reason for crashing so it can be logged if implemented
     **/
    public static void forceCrashJVM(String crashReason)
    {
        String className = SnakerLib.getCallerClassReference().toString();
        String threadName = Thread.currentThread().getName();
        String regex = ".*[a-zA-Z]+.*";
        String lineBreak = "\n";

        StringBuilder reportBuilder = new StringBuilder("-+-");

        reportBuilder.append("-+-".repeat(36));

        SnakerLib.LOGGER.warnf("JVM crash detected on %s", threadName);
        SnakerLib.LOGGER.errorf("%s %24s %s", lineBreak, reportBuilder, lineBreak);
        SnakerLib.LOGGER.errorf("The JVM was forcefully crashed by %s %s", className, lineBreak);

        if (!className.contains("SnakerLib")) {
            SnakerLib.LOGGER.errorf("The JVM was not crashed by SnakerLib. Please go report it to %s %s", className, lineBreak);
        }

        if (!crashReason.matches(regex)) {
            SnakerLib.LOGGER.errorf("The reason for the crash was not specified %s", lineBreak);
        } else {
            SnakerLib.LOGGER.errorf("%s %s", crashReason, lineBreak);
        }

        SnakerLib.LOGGER.errorf("%24s", reportBuilder);
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
                        String fileName = jvmFile.getName();
                        if (jvmFile.delete()) {
                            SnakerLib.LOGGER.infof("Successfully deleted JVM crash file '%s'", fileName);
                        } else {
                            SnakerLib.LOGGER.infof("Could not delete JVM crash file '%s'", fileName);
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
                    String fileName = crashFile.getName();
                    if (crashFile.delete()) {
                        SnakerLib.LOGGER.infof("Successfully deleted crash file '%s'", fileName);
                    } else {
                        SnakerLib.LOGGER.infof("Could not delete crash file '%s'", fileName);
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
                int bossesSize = bosses.size();
                boss.discard();
                if (notify) {
                    SnakerLib.LOGGER.infof("Successfully discarded %s bosses", bossesSize);
                    notify = false;
                }
            }
            Thread.sleep(500);
            bosses.clear();
            notify = true;
        }
    }
}