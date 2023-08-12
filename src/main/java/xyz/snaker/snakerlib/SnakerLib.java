package xyz.snaker.snakerlib;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import xyz.snaker.snakerlib.client.render.processor.SimpleRenderTypeProcessor;
import xyz.snaker.snakerlib.config.SnakerConfig;
import xyz.snaker.snakerlib.internal.LevelSavingEvent;
import xyz.snaker.snakerlib.internal.Single;
import xyz.snaker.snakerlib.internal.StringNuker;
import xyz.snaker.snakerlib.internal.log4j.SnakerLogger;
import xyz.snaker.snakerlib.internal.log4j.SnakerLoggerManager;
import xyz.snaker.snakerlib.level.entity.SnakerBoss;
import xyz.snaker.snakerlib.math.Maths;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
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

import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.util.Pair;

import lombok.Getter;

/**
 * Created by SnakerBone on 5/05/2023
 **/
public class SnakerLib
{
    private volatile boolean notify = true;
    private static volatile boolean initialized = false;
    private static volatile boolean registered = false;

    @Getter
    private static long clientTickCount = 0;
    @Getter
    private static long serverTickCount = 0;

    public static final Component VIRTUAL_MACHINE_FORCE_CRASH_KEYBINDS_PRESSED = Component.literal("Left shift and F4 pressed.");
    public static final Component DISABLE_IN_CONFIG = Component.literal("You can disable this in the config (snakerlib-common.toml) if you wish");
    public static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    public static final SnakerLogger LOGGER = SnakerLoggerManager.INSTANCE.apply(SnakerLib.NAME);
    public static final Single<String> DELEGATE_MOD = new Single<>();

    public static final String MODID = "snakerlib";
    public static final String NAME = "SnakerLib";

    public static Path runFolder;

    public SnakerLib()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::configLoadEvent);
        bus.addListener(this::modSetupEvent);

        //DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> SnakerSprites::initialize);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SnakerConfig.COMMON_SPEC, "snakerlib-common.toml");
    }

    public static void initialize()
    {
        Class<?> clazz = STACK_WALKER.getCallerClass();

        if (initialized) {
            throw new RuntimeException("SnakerLib has already been initialized");
        } else {
            if (!clazz.isAnnotationPresent(Mod.class)) {
                throw new RuntimeException(String.format("Could not initialize mod to SnakerLib: Class '%s' is not annotated with @Mod", clazz.getSimpleName()));
            }

            String modid = clazz.getAnnotation(Mod.class).value();

            if (isValidString(modid)) {
                DELEGATE_MOD.set(modid);
                String name = DELEGATE_MOD.get();
                SnakerLib.LOGGER.infof("Successfully initialized mod '%s' to SnakerLib", name);
                initialized = true;
            } else {
                throw new RuntimeException(String.format("Could not initialize mod '%s' to SnakerLib: modid is invalid", modid));
            }

            if (registered) {
                throw new RuntimeException("SnakerLib has already been registered");
            } else {
                MinecraftForge.EVENT_BUS.register(new SnakerLib());
                registered = true;
            }
        }
    }

    public static String getFieldName(Object obj, Class<?> parent, boolean lowercase)
    {
        Field[] fields = parent.getFields();

        if (obj == null) {
            throw new NullPointerException("Field cannot be null");
        }

        if (obj.getClass() != parent) {
            throw new RuntimeException("Field must be apart of the parent class");
        }

        if (Arrays.stream(fields).toList().isEmpty()) {
            SnakerLib.LOGGER.warnf("No recognizable fields found in class '%s'", parent.getSimpleName());
            return null;
        }

        for (Field field : fields) {
            Object object;

            try {
                object = field.get(parent);
            } catch (Exception e) {
                throw new Error(e);
            }

            if (obj.equals(object)) {
                return lowercase ? field.getName().toLowerCase() : field.getName();
            }
        }

        return null;
    }

    public static String getFieldName(Object obj, Class<?> parent)
    {
        return getFieldName(obj, parent, true);
    }

    public static String getFieldName(Object obj, boolean lowercase)
    {
        Class<?> parent = STACK_WALKER.getCallerClass();
        Field[] fields = parent.getFields();

        if (obj == null) {
            throw new NullPointerException("Field cannot be null");
        }

        if (obj.getClass() != parent) {
            SnakerLib.LOGGER.errorf("Hint: Caller class '%s' is not apart of the parent class", parent.getSimpleName());
            throw new RuntimeException("Field must be apart of the parent class");
        }

        if (Arrays.stream(fields).toList().isEmpty()) {
            SnakerLib.LOGGER.warnf("No recognizable fields found in class '%s'", parent.getSimpleName());
            return null;
        }

        for (Field field : fields) {
            Object object;

            try {
                object = field.get(parent);
            } catch (Exception e) {
                throw new Error(e);
            }

            if (obj.equals(object)) {
                return lowercase ? field.getName().toLowerCase() : field.getName();
            }
        }

        return null;
    }

    public static String getFieldName(Object obj)
    {
        return getFieldName(obj, true);
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
        return modid ? DELEGATE_MOD.get() + ":" + RandomStringUtils.randomAlphanumeric(limit).toLowerCase(locale) : RandomStringUtils.randomAlphanumeric(limit).toLowerCase(locale);
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

    public static boolean isValidString(String string, boolean notify, boolean crash)
    {
        if (string == null || string.isEmpty()) {
            return false;
        } else {
            String regex = ".*[a-zA-Z]+.*";
            if (!string.matches(regex)) {
                if (notify) {
                    SnakerLib.LOGGER.warnf("String '%s' is not a valid string", string);
                    if (crash) {
                        throw new RuntimeException(String.format("Invalid string: %s", string));
                    }
                }
                if (!notify && crash) {
                    throw new RuntimeException(String.format("Invalid string: %s", string));
                }
            }
            return string.matches(regex);
        }
    }

    public static boolean isValidString(String string, boolean notify)
    {
        return isValidString(string, notify, false);
    }

    public static boolean isValidString(String string)
    {
        return isValidString(string, false);
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
        String className = STACK_WALKER.getCallerClass().toString();
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

    public static SimpleRenderTypeProcessor createFreshProcessor()
    {
        return new SimpleRenderTypeProcessor()
        {
            @Override
            public RenderType.CompositeState normal(Supplier<ShaderInstance> shader)
            {
                return SimpleRenderTypeProcessor.super.normal(shader);
            }

            @Override
            public RenderType.CompositeState entity(Supplier<ShaderInstance> shader)
            {
                return SimpleRenderTypeProcessor.super.entity(shader);
            }

            @Override
            public RenderType.CompositeState translucent(Supplier<ShaderInstance> shader)
            {
                return SimpleRenderTypeProcessor.super.translucent(shader);
            }

            @Override
            public RenderType.CompositeState sampler(Supplier<ShaderInstance> shader, boolean blur, boolean mipmap, ResourceLocation... samplers)
            {
                return SimpleRenderTypeProcessor.super.sampler(shader, blur, mipmap, samplers);
            }

            @Override
            public RenderType.CompositeState sampler(Supplier<ShaderInstance> shader, ResourceLocation sampler, boolean blur, boolean mipmap)
            {
                return SimpleRenderTypeProcessor.super.sampler(shader, sampler, blur, mipmap);
            }

            @Override
            public RenderType create(@Nullable String name, Pair<VertexFormat, RenderType.CompositeState> pair)
            {
                return SimpleRenderTypeProcessor.super.create(name, pair);
            }
        };
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