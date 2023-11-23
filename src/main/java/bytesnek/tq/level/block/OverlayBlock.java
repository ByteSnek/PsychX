package bytesnek.tq.level.block;

import bytesnek.snakerlib.utility.block.BlockProperties;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

import bytesnek.tq.utility.NoCreativeTab;

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
