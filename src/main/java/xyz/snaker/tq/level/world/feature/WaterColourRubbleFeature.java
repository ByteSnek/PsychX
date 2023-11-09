package xyz.snaker.tq.level.world.feature;

import xyz.snaker.snakerlib.level.levelgen.feature.RubbleFeature;
import xyz.snaker.tq.level.world.dimension.Comatose;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

import com.mojang.serialization.Codec;

/**
 * Created by SnakerBone on 27/08/2023
 **/
public class WaterColourRubbleFeature extends RubbleFeature
{
    public WaterColourRubbleFeature(Codec<BlockStateConfiguration> codec)
    {
        super(codec, 5, Comatose.getBlockList(), 2, 4);
    }

    @Override
    public void placeStud(BlockPos pos, BlockState state)
    {
        placeBlock(pos.above(), state);
        placeBlock(pos.below(), state);
        for (BlockPos under = pos.below(); isReplaceableAt(under); under = under.below()) {
            placeBlock(under, state);
        }
    }

    @Override
    public void placeBase(RandomSource random, BlockPos pos, BlockState state)
    {
        if (placeBlock(pos.north(), state) && random.nextBoolean()) {
            placeStud(pos.north(), state);
        }
        if (placeBlock(pos.south(), state) && random.nextBoolean()) {
            placeStud(pos.south(), state);
        }
        if (placeBlock(pos.east(), state) && random.nextBoolean()) {
            placeStud(pos.east(), state);
        }
        if (placeBlock(pos.west(), state) && random.nextBoolean()) {
            placeStud(pos.west(), state);
        }
    }
}
