package snaker.snakerlib.utility;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import snaker.snakerlib.SnakerLib;

/**
 * Created by SnakerBone on 17/06/2023
 **/
public class TextFormatting
{
    public static final ChatFormatting[] ALL_FORMATTINGS = ChatFormatting.values();
    public static final ChatFormatting[] ALL_COLOURS = SnakerLib.filterEnumValues(ChatFormatting.values(), ChatFormatting::isColor, ChatFormatting[]::new);
    public static final ChatFormatting[] ALL_FORMATS = SnakerLib.filterEnumValues(ChatFormatting.values(), ChatFormatting::isFormat, ChatFormatting[]::new);
    public static final ChatFormatting[] ALL_SHADES = {ChatFormatting.BLACK, ChatFormatting.DARK_GRAY, ChatFormatting.GRAY, ChatFormatting.WHITE};
    public static final ChatFormatting[] PRIME_COLOURS = {ChatFormatting.RED, ChatFormatting.GREEN, ChatFormatting.BLUE, ChatFormatting.YELLOW};
    public static final ChatFormatting[] RAINBOW_COLOURS = {ChatFormatting.RED, ChatFormatting.GREEN, ChatFormatting.BLUE, ChatFormatting.YELLOW, ChatFormatting.LIGHT_PURPLE, ChatFormatting.AQUA};
    public static final ChatFormatting[] DARK_COLOURS = {ChatFormatting.DARK_RED, ChatFormatting.DARK_GREEN, ChatFormatting.DARK_BLUE, ChatFormatting.DARK_PURPLE, ChatFormatting.DARK_AQUA};
    public static final ChatFormatting[] LIGHT_COLOURS = {ChatFormatting.LIGHT_PURPLE};
    public static final ChatFormatting[] WARM_COLOURS = {ChatFormatting.DARK_RED, ChatFormatting.RED, ChatFormatting.GOLD, ChatFormatting.YELLOW};
    public static final ChatFormatting[] COOL_COLOURS = {ChatFormatting.GREEN, ChatFormatting.DARK_GREEN, ChatFormatting.BLUE, ChatFormatting.DARK_BLUE, ChatFormatting.LIGHT_PURPLE, ChatFormatting.DARK_PURPLE, ChatFormatting.DARK_AQUA};
    public static final ChatFormatting[] DARK_SHADES = {ChatFormatting.BLACK, ChatFormatting.DARK_GRAY};
    public static final ChatFormatting[] LIGHT_SHADES = {ChatFormatting.GRAY, ChatFormatting.WHITE};

    public static String cycle(String value, ChatFormatting[] buffer, double offset, int mul)
    {
        StringBuilder builder = new StringBuilder(value.length() * 3);
        if (!(offset <= 0)) {
            int time = (int) Math.floor(Util.getMillis() / offset);
            int index = time % buffer.length;
            for (int i = 0; i < value.length(); i++) {
                char character = value.charAt(i);
                int colour = ((i * mul) + buffer.length - index) % buffer.length;
                builder.append(buffer[colour].toString());
                builder.append(character);
            }
        }
        return builder.toString();
    }
}
