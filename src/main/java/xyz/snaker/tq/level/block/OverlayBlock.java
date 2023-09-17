package xyz.snaker.tq.level.block;

import xyz.snaker.snakerlib.data.DefaultBlockProperties;
import xyz.snaker.tq.utility.IgnoreCreativeTab;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

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
}
