package xyz.snaker.tq.level.block;

import java.util.List;

import xyz.snaker.snakerlib.data.DefaultBlockProperties;
import xyz.snaker.tq.utility.IgnoreCreativeTab;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by SnakerBone on 23/08/2023
 **/
@IgnoreCreativeTab
public class OverlayBlock extends Block
{
    public OverlayBlock(Properties properties)
    {
        super(properties);
    }

    public OverlayBlock()
    {
        this(DefaultBlockProperties.CUTOUT.apply(MapColor.NONE));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag)
    {
        tooltip.add(Component.translatable("tooltip.tq.possible_issue"));
    }
}
