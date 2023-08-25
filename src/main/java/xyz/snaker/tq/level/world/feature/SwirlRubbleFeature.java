package xyz.snaker.tq.level.world.feature;

import java.util.List;

import xyz.snaker.snakerlib.level.world.feature.RubbleFeature;
import xyz.snaker.snakerlib.utility.random.Frequency;
import xyz.snaker.tq.rego.Blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

import com.mojang.serialization.Codec;

/**
 * Created by SnakerBone on 25/08/2023
 **/
public class SwirlRubbleFeature extends RubbleFeature
{
    public SwirlRubbleFeature(Codec<BlockStateConfiguration> codec)
    {
        super(codec, Frequency.of(6), List.of(Blocks.COMASTONE.get()));
    }

    @Override
    public void placeStud(WorldGenLevel level, BlockPos pos, BlockState state)
    {
        level.setBlock(pos.above(), state, Block.UPDATE_ALL);
        level.setBlock(pos.below(), state, Block.UPDATE_ALL);
        for (BlockPos under = pos.below(); isReplaceable(level, under); under = under.below()) {
            level.setBlock(under, state, Block.UPDATE_ALL);
        }
    }

    @Override
    public void placeBase(WorldGenLevel level, RandomSource random, BlockPos pos, BlockState state)
    {
        if (level.setBlock(pos.north(), state, Block.UPDATE_ALL) && random.nextBoolean()) {
            placeStud(level, pos.north(), state);
        }
        if (level.setBlock(pos.south(), state, Block.UPDATE_ALL) && random.nextBoolean()) {
            placeStud(level, pos.south(), state);
        }
        if (level.setBlock(pos.east(), state, Block.UPDATE_ALL) && random.nextBoolean()) {
            placeStud(level, pos.east(), state);
        }
        if (level.setBlock(pos.west(), state, Block.UPDATE_ALL) && random.nextBoolean()) {
            placeStud(level, pos.west(), state);
        }
    }
}
