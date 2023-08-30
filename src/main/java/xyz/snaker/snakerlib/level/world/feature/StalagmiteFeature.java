package xyz.snaker.snakerlib.level.world.feature;

import java.util.ArrayList;
import java.util.List;

import xyz.snaker.snakerlib.math.Maths;
import xyz.snaker.snakerlib.utility.random.Frequency;
import xyz.snaker.snakerlib.utility.tools.WorldStuff;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.BlockBlobFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

import org.jetbrains.annotations.NotNull;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public class StalagmiteFeature extends BlockBlobFeature
{
    private WorldGenLevel level;
    private final Frequency frequency;
    private final List<Block> blocksToPlaceOn;
    private boolean isLevelSet;
    private final int minHeightRange;
    private final int maxHeightRange;

    public StalagmiteFeature(Codec<BlockStateConfiguration> codec, Frequency frequency, List<Block> blocksToPlaceOn, int minHeightRange, int maxHeightRange)
    {
        super(codec);
        this.frequency = frequency;
        this.blocksToPlaceOn = blocksToPlaceOn;
        this.minHeightRange = minHeightRange;
        this.maxHeightRange = maxHeightRange;
    }

    public void setLevel(WorldGenLevel level)
    {
        this.level = level;
        this.isLevelSet = level != null;
    }

    public WorldGenLevel getLevel()
    {
        if (isLevelSet) {
            return level;
        } else {
            throw new RuntimeException("Level Not Set");
        }
    }

    public boolean placeBlock(BlockPos pos, BlockState state)
    {
        return level.setBlock(pos, state, Block.UPDATE_ALL);
    }

    @Override
    public boolean place(@NotNull FeaturePlaceContext<BlockStateConfiguration> context)
    {
        setLevel(context.level());

        BlockStateConfiguration config = context.config();
        BlockPos origin = context.origin();
        BlockState state = config.state;
        RandomSource random = context.random();
        Pair<Direction, Direction> pair = getDirectionPair(random);

        if (random.nextInt(frequency.getValue()) == 0) {
            for (Block block : blocksToPlaceOn) {
                if (level.getBlockState(origin.below()).is(block)) {
                    if (WorldStuff.isFreeAroundPos(level, origin, false)) {
                        for (int i = 0; i < maxHeightRange; i++) {
                            placeBlock(origin.above(i), state);
                        }
                        for (int i = 0; i < Maths.middle(minHeightRange, maxHeightRange); i++) {
                            BlockPos pos = origin.relative(pair.getFirst());
                            placeBlock(pos.above(i), state);
                        }
                        for (int i = 0; i < minHeightRange; i++) {
                            BlockPos pos = origin.relative(pair.getSecond());
                            placeBlock(pos.above(i), state);
                        }
                    }
                    return true;
                }
            }
        }

        return false;
    }

    private Pair<Direction, Direction> getDirectionPair(RandomSource random)
    {
        List<Pair<Direction, Direction>> variants = new ArrayList<>();

        variants.add(new Pair<>(Direction.NORTH, Direction.EAST));
        variants.add(new Pair<>(Direction.NORTH, Direction.WEST));
        variants.add(new Pair<>(Direction.SOUTH, Direction.EAST));
        variants.add(new Pair<>(Direction.SOUTH, Direction.WEST));

        variants.add(new Pair<>(Direction.EAST, Direction.NORTH));
        variants.add(new Pair<>(Direction.WEST, Direction.NORTH));
        variants.add(new Pair<>(Direction.EAST, Direction.SOUTH));
        variants.add(new Pair<>(Direction.WEST, Direction.SOUTH));

        return Util.getRandom(variants, random);
    }
}
