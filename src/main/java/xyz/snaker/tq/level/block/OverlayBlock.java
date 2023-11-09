package xyz.snaker.tq.level.block;

import xyz.snaker.snakerlib.utility.block.BlockProperties;
import xyz.snaker.tq.utility.NoCreativeTab;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

/**
 * Created by SnakerBone on 23/08/2023
 **/
@NoCreativeTab
public class OverlayBlock extends Block
{
    public OverlayBlock(Properties properties)
    {
        super(properties);
    }

    public OverlayBlock()
    {
        this(BlockProperties.CUTOUT.apply(MapColor.NONE));
    }
}
