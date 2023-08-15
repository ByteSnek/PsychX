package xyz.snaker.snakerlib.utility.tools;

import java.util.function.Supplier;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;

/**
 * Created by SnakerBone on 20/02/2023
 **/
public class BlockStuff
{
    /**
     * Adds a plant to vanillas flower pot
     *
     * @param plant  The plant to add
     * @param potted The potted variant of the plant
     * @return The flower pot block
     **/
    public static FlowerPotBlock addPotPlant(Block plant, Supplier<Block> potted)
    {
        FlowerPotBlock block = UnsafeStuff.cast(Blocks.FLOWER_POT);
        if (block != null) {
            block.addPlant(ResourceStuff.getResourceLocation(plant), potted);
            return block;
        } else {
            throw new NullPointerException("Flower pot is null");
        }
    }
}