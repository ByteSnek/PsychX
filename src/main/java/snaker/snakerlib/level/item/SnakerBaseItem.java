package snaker.snakerlib.level.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by SnakerBone on 17/03/2023
 **/
public abstract class SnakerBaseItem extends Item implements SnakerItem
{
    public SnakerBaseItem()
    {
        super(new Properties());
    }

    public SnakerBaseItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag)
    {
        super.appendHoverText(stack, level, tooltip, flag);
        if (hasTooltipDetails(null)) {
            addTooltipDetails(null, stack, tooltip, flag.isAdvanced());
        }
        for (Key key : Key.values()) {
            if (hasTooltipDetails(key)) {
                if (key.isDown()) {
                    addTooltipDetails(key, stack, tooltip, flag.isAdvanced());
                } else {
                    tooltip.add(Component.literal("Hold " + ChatFormatting.YELLOW + key.getSerializedName() + ChatFormatting.GRAY + " for more information"));
                }
            }
        }
    }

    public void addTooltipDetails(@Nullable Key key, ItemStack stack, List<Component> tooltip, boolean advanced)
    {

    }

    public boolean hasTooltipDetails(@Nullable Key key)
    {
        return false;
    }

    public enum Key implements StringRepresentable
    {
        SHIFT(GLFW.GLFW_KEY_RIGHT_SHIFT, GLFW.GLFW_KEY_LEFT_SHIFT),
        CTRL(GLFW.GLFW_KEY_RIGHT_CONTROL, GLFW.GLFW_KEY_LEFT_CONTROL),
        ALT(GLFW.GLFW_KEY_RIGHT_ALT, GLFW.GLFW_KEY_LEFT_ALT);

        final String name;
        final int[] keys;

        Key(int... keys)
        {
            this.keys = keys;
            this.name = name();
        }

        public boolean isDown()
        {
            for (int key : keys) {
                if (GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), key) == GLFW.GLFW_PRESS) {
                    return true;
                }
            }

            return false;
        }

        @Override
        @Nonnull
        public String getSerializedName()
        {
            return StringUtils.capitalize(name.toLowerCase());
        }
    }
}
