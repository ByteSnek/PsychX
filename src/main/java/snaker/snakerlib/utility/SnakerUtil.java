package snaker.snakerlib.utility;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Rarity;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import snaker.snakerlib.SnakerLib;
import snaker.snakerlib.internal.StringNuker;
import snaker.snakerlib.math.Mh;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by SnakerBone on 20/02/2023
 **/
public class SnakerUtil
{
    public static final String PLACEHOLDER = SnakerLib.MODID + ":" + PlaceHolders.PH8;
    public static final String PLACEHOLDER_NO_MODID = PlaceHolders.PH8;

    public static String getBaseName(Class<?> clazz)
    {
        String pkg = clazz.getPackage().getName();
        return pkg.substring(pkg.lastIndexOf('.')).replace(".", "");
    }

    public static <T extends LivingEntity, E extends MobEffect> boolean addEffectDirect(T entity, E effect, @Nullable Integer duration, @Nullable Integer mul, boolean isAmbient, boolean showBubble, boolean showIcon)
    {
        return entity.addEffect(new MobEffectInstance(effect, duration == null ? Mh.secondsToTicks(10) : duration, mul == null ? 0 : mul, isAmbient, showBubble, showIcon));
    }

    public static <T extends LivingEntity, E extends MobEffect> boolean addEffectDirect(T entity, E effect, @Nullable Integer duration, @Nullable Integer mul)
    {
        return addEffectDirect(entity, effect, duration, mul, false, false, false);
    }

    public static <T extends LivingEntity, E extends MobEffect> boolean addEffectDirect(T entity, E effect)
    {
        return addEffectDirect(entity, effect, null, null);
    }

    public static <T extends LivingEntity> boolean isEntityRotating(@NotNull T entity)
    {
        return entity.getYRot() != entity.yRotO || entity.yBodyRot != entity.yBodyRotO || entity.yHeadRot != entity.yHeadRotO;
    }

    public static <T extends LivingEntity> boolean isEntityYRotating(@NotNull T entity)
    {
        return entity.getYRot() != entity.yRotO;
    }

    public static <T extends LivingEntity> boolean isEntityYBodyRotating(@NotNull T entity)
    {
        return entity.yBodyRot != entity.yBodyRotO;
    }

    public static <T extends LivingEntity> boolean isEntityYHeadRotating(@NotNull T entity)
    {
        return entity.yHeadRot != entity.yHeadRotO;
    }

    public static <T extends Entity> boolean isEntityMoving(@NotNull T entity)
    {
        return entity.getX() != entity.xo || entity.getY() != entity.yo || entity.getZ() != entity.zo;
    }

    public static <T extends Entity> boolean isEntityMovingX(@NotNull T entity)
    {
        return entity.getX() != entity.xo;
    }

    public static <T extends Entity> boolean isEntityMovingY(@NotNull T entity)
    {
        return entity.getY() != entity.yo;
    }

    public static <T extends Entity> boolean isEntityMovingZ(@NotNull T entity)
    {
        return entity.getZ() != entity.zo;
    }

    public static <T extends Entity> boolean isEntityMovingXZ(@NotNull T entity)
    {
        return entity.getX() != entity.xo || entity.getZ() != entity.zo;
    }

    public static <T extends Entity> boolean isEntityMovingXY(@NotNull T entity)
    {
        return entity.getX() != entity.xo || entity.getY() != entity.yo;
    }

    public static <T extends Entity> boolean isEntityMovingYZ(@NotNull T entity)
    {
        return entity.getY() != entity.yo || entity.getZ() != entity.zo;
    }

    public static String untranslate(String text)
    {
        return !text.isEmpty() ? text.replaceAll("\\s+", "_").toLowerCase() : text;
    }

    public static String translate(String text)
    {
        if (!text.isEmpty()) {
            return Stream.of(text.trim().split("\\s|\\p{Pc}")).filter(word -> word.length() > 0).map(word -> word.substring(0, 1).toUpperCase() + word.substring(1)).collect(Collectors.joining(" "));
        } else {
            return text;
        }
    }

    public static String translate(String text, Rarity rarity)
    {
        switch (rarity) {
            case UNCOMMON -> {
                return "§e" + translate(text);
            }
            case RARE -> {
                return "§b" + translate(text);
            }
            case EPIC -> {
                return "§d" + translate(text);
            }
            default -> {
                return translate(text);
            }
        }
    }

    public static String untranslateComponent(MutableComponent component, boolean leaveCaps)
    {
        StringNuker nuker = new StringNuker(component.getString());
        nuker.replaceAllAndDestroy("\\p{P}");
        return leaveCaps ? nuker.result() : nuker.result().toLowerCase();
    }

    public static String untranslateComponent(MutableComponent component)
    {
        return untranslateComponent(component, false);
    }

    public static int randomHex()
    {
        Random random = new Random();
        return random.nextInt(0xffffff + 1);
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

    @Nullable
    @SuppressWarnings("unchecked")
    public static <Anything> Anything shutUp(@Nullable Object object)
    {
        return (Anything) object;
    }

    private static String generatePlaceholder(int limit)
    {
        return RandomStringUtils.randomAlphanumeric(limit).toUpperCase();
    }

    static class PlaceHolders
    {
        static String PH2 = generatePlaceholder(2);
        static String PH4 = generatePlaceholder(4);
        static String PH8 = generatePlaceholder(8);
        static String PH16 = generatePlaceholder(16);
        static String PH32 = generatePlaceholder(32);
        static String PH64 = generatePlaceholder(64);
    }
}