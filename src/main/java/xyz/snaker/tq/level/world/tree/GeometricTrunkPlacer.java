package xyz.snaker.tq.level.world.tree;

import java.util.List;
import java.util.function.BiConsumer;

import xyz.snaker.tq.rego.TrunkPlacers;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * Created by SnakerBone on 25/08/2023
 **/
public class GeometricTrunkPlacer extends TrunkPlacer
{
    public static final Codec<GeometricTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> trunkPlacerParts(instance).apply(instance, GeometricTrunkPlacer::new));

    public GeometricTrunkPlacer(int baseHeight, int heightRandA, int heightRandB)
    {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected @NotNull TrunkPlacerType<?> type()
    {
        return TrunkPlacers.GEOMETRIC.get();
    }

    @Override
    public @NotNull List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> blockSetter, @NotNull RandomSource random, int freeTreeHeight, @NotNull BlockPos pos, @NotNull TreeConfiguration config)
    {
        setDirtAt(level, blockSetter, random, pos.below(), config);
        int height = getTreeHeight(random);
        for (int i = 0; i < height; i++) {
            BlockPos above = pos.above(i);
            placeLog(level, blockSetter, random, above, config);
            if (i == 0) {
                placeLog(level, blockSetter, random, above.north(), config);
                placeLog(level, blockSetter, random, above.north().west(), config);
                placeLog(level, blockSetter, random, above.north(2), config);
                placeLog(level, blockSetter, random, above.south(), config);
                placeLog(level, blockSetter, random, above.south().east(), config);
                placeLog(level, blockSetter, random, above.south(2), config);
                placeLog(level, blockSetter, random, above.east(), config);
                placeLog(level, blockSetter, random, above.east().north(), config);
                placeLog(level, blockSetter, random, above.east(2), config);
                placeLog(level, blockSetter, random, above.west(), config);
                placeLog(level, blockSetter, random, above.west().south(), config);
                placeLog(level, blockSetter, random, above.west(2), config);
            } else if (i == 1 || i == 2) {
                placeLog(level, blockSetter, random, above.north(), config);
                placeLog(level, blockSetter, random, above.south(), config);
                placeLog(level, blockSetter, random, above.east(), config);
                placeLog(level, blockSetter, random, above.west(), config);
            }
        }
        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pos.above(height - 1), 0, false));
    }
}
