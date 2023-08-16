package xyz.snaker.snakerlib.utility.tools;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.function.Predicate;

import xyz.snaker.snakerlib.SnakerLib;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;

/**
 * Created by SnakerBone on 17/06/2023
 **/
public class ChatStuff
{
    /**
     * All the Minecraft chat format values
     **/
    public static final ChatFormatting[] ALL_FORMATTINGS = ChatFormatting.values();

    /**
     * All the Minecraft chat colours
     **/
    public static final ChatFormatting[] ALL_COLOURS = ChatStuff.filterEnumValues(ChatFormatting.values(), ChatFormatting::isColor, ChatFormatting[]::new);

    /**
     * All the Minecraft chat formats
     **/
    public static final ChatFormatting[] ALL_FORMATS = ChatStuff.filterEnumValues(ChatFormatting.values(), ChatFormatting::isFormat, ChatFormatting[]::new);

    /**
     * All the Minecraft chat colour shades
     **/
    public static final ChatFormatting[] ALL_SHADES = {ChatFormatting.BLACK, ChatFormatting.DARK_GRAY, ChatFormatting.GRAY, ChatFormatting.WHITE};

    /**
     * All the Minecraft chat prime colours
     **/
    public static final ChatFormatting[] PRIME_COLOURS = {ChatFormatting.RED, ChatFormatting.GREEN, ChatFormatting.BLUE, ChatFormatting.YELLOW};

    /**
     * All the Minecraft chat rainbow colours
     **/
    public static final ChatFormatting[] RAINBOW_COLOURS = {ChatFormatting.RED, ChatFormatting.GREEN, ChatFormatting.BLUE, ChatFormatting.YELLOW, ChatFormatting.LIGHT_PURPLE, ChatFormatting.AQUA};

    /**
     * All the Minecraft chat dark colours
     **/
    public static final ChatFormatting[] DARK_COLOURS = {ChatFormatting.DARK_RED, ChatFormatting.DARK_GREEN, ChatFormatting.DARK_BLUE, ChatFormatting.DARK_PURPLE, ChatFormatting.DARK_AQUA};

    /**
     * All the Minecraft chat light colours
     **/
    public static final ChatFormatting[] LIGHT_COLOURS = {ChatFormatting.LIGHT_PURPLE};

    /**
     * All the Minecraft chat warm colours
     **/
    public static final ChatFormatting[] WARM_COLOURS = {ChatFormatting.DARK_RED, ChatFormatting.RED, ChatFormatting.GOLD, ChatFormatting.YELLOW};

    /**
     * All the Minecraft chat cool colours
     **/
    public static final ChatFormatting[] COOL_COLOURS = {ChatFormatting.GREEN, ChatFormatting.DARK_GREEN, ChatFormatting.BLUE, ChatFormatting.DARK_BLUE, ChatFormatting.LIGHT_PURPLE, ChatFormatting.DARK_PURPLE, ChatFormatting.DARK_AQUA};

    /**
     * All the Minecraft chat dark colour shades
     **/
    public static final ChatFormatting[] DARK_SHADES = {ChatFormatting.BLACK, ChatFormatting.DARK_GRAY};

    /**
     * All the Minecraft chat light colour shades
     **/
    public static final ChatFormatting[] LIGHT_SHADES = {ChatFormatting.GRAY, ChatFormatting.WHITE};

    /**
     * Cycles between chat formats in an array
     *
     * @param value  The string to format
     * @param values The chat format values
     * @param offset The cycle offset
     * @param mul    The cycle speed multiplier
     * @return The value with the formatting
     **/
    public static String cycle(String value, ChatFormatting[] values, double offset, int mul)
    {
        StringBuilder builder = new StringBuilder(value.length() * 3);
        if (!(offset <= 0)) {
            int time = (int) Math.floor(Util.getMillis() / offset);
            int index = time % values.length;
            for (int i = 0;
                 i < value.length();
                 i++) {
                char character = value.charAt(i);
                int colour = ((i * mul) + values.length - index) % values.length;
                builder.append(values[colour].toString());
                builder.append(character);
            }
        }
        return builder.toString();
    }

    /**
     * Filters enum values
     *
     * @param values   The enum values
     * @param filter   The filter to use
     * @param function The sorting of the values
     * @return The filtered enum values as an array
     **/
    static <V> V[] filterEnumValues(V[] values, Predicate<? super V> filter, IntFunction<V[]> function)
    {
        if (values != null && values.length > 0) {
            return Arrays.stream(values).filter(filter).toArray(function);
        } else {
            SnakerLib.LOGGER.error("Invalid enum or enum values");
            return null;
        }
    }
}
