package xyz.snaker.tq.utility.level;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;

/**
 * Created by SnakerBone on 18/10/2023
 **/
public class FlatLayer
{
    private final int height;
    private final Block block;

    FlatLayer(int height, Block block)
    {
        this.height = height;
        this.block = block;
    }

    public static FlatLayer create(int height, Block block)
    {
        return new FlatLayer(height, block);
    }

    public static FlatLayer create(Block block)
    {
        return new FlatLayer(1, block);
    }

    public static FlatLayerInfo direct(int height, Block block)
    {
        return new FlatLayer(height, block).getInfo();
    }

    public static FlatLayerInfo direct(Block block)
    {
        return new FlatLayer(1, block).getInfo();
    }

    public FlatLayerInfo getInfo()
    {
        return new FlatLayerInfo(height, block);
    }
}
