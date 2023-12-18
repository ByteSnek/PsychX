package xyz.snaker.tq.level.levelgen.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import org.jetbrains.annotations.NotNull;

import xyz.snaker.tq.rego.TrunkPlacers;

/**
 * Created by SnakerBone on 25/08/2023
 **/
public class IllusiveTrunkPlacer extends TrunkPlacer
{
    private static final Codec<UniformInt> BRANCH_START_CODEC = ExtraCodecs.validate(UniformInt.CODEC, uniformInt -> uniformInt.getMaxValue() - uniformInt.getMinValue() < 1 ? DataResult.error(() -> "Need at least 2 blocks variation for the branch starts to fit both branches") : DataResult.success(uniformInt));
    public static final Codec<IllusiveTrunkPlacer> CODEC = RecordCodecBuilder.create(instance ->
            trunkPlacerParts(instance).and(instance.group(
                            IntProvider.codec(1, 3)
                                    .fieldOf("branch_count")
                                    .forGetter(placer -> placer.branchCount),
                            IntProvider.codec(2, 16)
                                    .fieldOf("branch_horizontal_length")
                                    .forGetter(placer -> placer.branchHorizontalLength),
                            IntProvider.codec(-16, 0, BRANCH_START_CODEC)
                                    .fieldOf("branch_start_offset_from_top")
                                    .forGetter(placer -> placer.branchStartOffsetFromTop),
                            IntProvider.codec(-16, 16)
                                    .fieldOf("branch_end_offset_from_top")
                                    .forGetter(placer -> placer.branchEndOffsetFromTop)))
                    .apply(instance, IllusiveTrunkPlacer::new));

    private final IntProvider branchCount;
    private final IntProvider branchHorizontalLength;

    private final UniformInt branchStartOffsetFromTop;
    private final UniformInt secondBranchStartOffsetFromTop;

    private final IntProvider branchEndOffsetFromTop;

    public IllusiveTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, IntProvider branchCount, IntProvider branchHorizontalLength, UniformInt branchStartOffsetFromTop, IntProvider branchEndOffsetFromTop)
    {
        super(baseHeight, heightRandA, heightRandB);
        this.branchCount = branchCount;
        this.branchHorizontalLength = branchHorizontalLength;
        this.branchStartOffsetFromTop = branchStartOffsetFromTop;
        this.secondBranchStartOffsetFromTop = UniformInt.of(branchStartOffsetFromTop.getMinValue(), branchStartOffsetFromTop.getMaxValue() - 1);
        this.branchEndOffsetFromTop = branchEndOffsetFromTop;
    }

    @Override
    protected @NotNull TrunkPlacerType<?> type()
    {
        return TrunkPlacers.ILLUSIVE.get();
    }

    @Override
    public @NotNull List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> blockSetter, @NotNull RandomSource random, int freeTreeHeight, @NotNull BlockPos pos, @NotNull TreeConfiguration config)
    {
        setDirtAt(level, blockSetter, random, pos.below(), config);

        int branchOffsetA = branchStartOffsetFromTop.sample(random);
        int branchOffsetB = secondBranchStartOffsetFromTop.sample(random);
        int branchHeightA = Math.max(0, freeTreeHeight - 1 + branchOffsetA);
        int branchHeightB = Math.max(0, freeTreeHeight - 1 + branchOffsetB);

        if (branchHeightB >= branchHeightA) {
            branchHeightB++;
        }

        int branches = branchCount.sample(random);
        boolean isExactlyThreeBranch = branches == 3;
        boolean isMoreThanTwoBranch = branches >= 2;
        int treeHeight;

        if (isExactlyThreeBranch) {
            treeHeight = freeTreeHeight;
        } else if (isMoreThanTwoBranch) {
            treeHeight = Math.max(branchHeightA, branchHeightB) + 1;
        } else {
            treeHeight = branchHeightA + 1;
        }

        for (int i = 0; i < treeHeight; i++) {
            placeLog(level, blockSetter, random, pos.above(i), config);
        }

        List<FoliagePlacer.FoliageAttachment> foliageAttachments = new ArrayList<>();

        if (isExactlyThreeBranch) {
            foliageAttachments.add(new FoliagePlacer.FoliageAttachment(pos.above(treeHeight), 0, false));
        }

        BlockPos.MutableBlockPos treePos = new BlockPos.MutableBlockPos();

        Direction directionA = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        Direction directionB = getRandomPerpendicularDirection(directionA, random);
        Direction directionC = random.nextBoolean() ? directionA.getOpposite() : directionA;
        Direction directionD = random.nextBoolean() ? directionB.getOpposite() : directionB;

        Function<BlockState, BlockState> stateSetterA = state -> state.trySetValue(RotatedPillarBlock.AXIS, directionA.getAxis());
        Function<BlockState, BlockState> stateSetterB = state -> state.trySetValue(RotatedPillarBlock.AXIS, directionB.getAxis());
        Function<BlockState, BlockState> stateSetterC = state -> state.trySetValue(RotatedPillarBlock.AXIS, directionC.getAxis());
        Function<BlockState, BlockState> stateSetterD = state -> state.trySetValue(RotatedPillarBlock.AXIS, directionD.getAxis());

        foliageAttachments.add(generateBranch(level, blockSetter, random, freeTreeHeight, pos, config, stateSetterA, directionA, branchHeightA, branchHeightA < treeHeight - 1, treePos));
        foliageAttachments.add(generateBranch(level, blockSetter, random, freeTreeHeight, pos, config, stateSetterB, directionB, branchHeightA, branchHeightA < treeHeight - 1, treePos));
        foliageAttachments.add(generateBranch(level, blockSetter, random, freeTreeHeight, pos, config, stateSetterC, directionC, branchHeightA, branchHeightA < treeHeight - 1, treePos));
        foliageAttachments.add(generateBranch(level, blockSetter, random, freeTreeHeight, pos, config, stateSetterD, directionD, branchHeightA, branchHeightA < treeHeight - 1, treePos));

        if (isMoreThanTwoBranch) {
            foliageAttachments.add(generateBranch(level, blockSetter, random, freeTreeHeight, pos, config, stateSetterA, directionA.getOpposite(), branchHeightB, branchHeightB < treeHeight - 1, treePos));
            foliageAttachments.add(generateBranch(level, blockSetter, random, freeTreeHeight, pos, config, stateSetterB, directionB.getOpposite(), branchHeightB, branchHeightB < treeHeight - 1, treePos));
            foliageAttachments.add(generateBranch(level, blockSetter, random, freeTreeHeight, pos, config, stateSetterC, directionC.getOpposite(), branchHeightB, branchHeightB < treeHeight - 1, treePos));
            foliageAttachments.add(generateBranch(level, blockSetter, random, freeTreeHeight, pos, config, stateSetterD, directionD.getOpposite(), branchHeightB, branchHeightB < treeHeight - 1, treePos));
        }

        return foliageAttachments;
    }

    private FoliagePlacer.FoliageAttachment generateBranch(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> posSetter, RandomSource random, int freeTreeHeight, BlockPos pos, TreeConfiguration config, Function<BlockState, BlockState> stateSetter, Direction direction, int branchOffsetB, boolean isBranchHigherThanTree, BlockPos.MutableBlockPos treePos)
    {
        treePos.set(pos).move(Direction.UP, branchOffsetB);

        int branchOffsetA = branchEndOffsetFromTop.sample(random);
        int treeHeight = freeTreeHeight - 1 + branchOffsetA;
        boolean isTreeHigh = isBranchHigherThanTree || treeHeight < branchOffsetB;
        int horizontalLength = branchHorizontalLength.sample(random);
        int horizontalExtLength = horizontalLength + (isTreeHigh ? 1 : 0);

        BlockPos branchPos = pos.relative(direction, horizontalExtLength).above(treeHeight);

        int extraRolls = isTreeHigh ? 2 : 1;

        for (int i = 0; i < extraRolls; i++) {
            placeLog(level, posSetter, random, treePos.move(direction), config, stateSetter);
        }

        Direction extDirection = branchPos.getY() > treePos.getY() ? Direction.UP : Direction.DOWN;

        while (true) {
            int branchManhattan = treePos.distManhattan(branchPos);

            if (branchManhattan == 0) {
                return new FoliagePlacer.FoliageAttachment(branchPos.above(), 0, false);
            }

            float adjustedBranchExt = (float) Math.abs(branchPos.getY() - treePos.getY()) / (float) branchManhattan;
            boolean moveBranch = random.nextFloat() < adjustedBranchExt;

            treePos.move(moveBranch ? extDirection : direction);

            placeLog(level, posSetter, random, treePos, config, moveBranch ? Function.identity() : stateSetter);
        }
    }

    private static Direction getRandomPerpendicularDirection(Direction direction, RandomSource random)
    {
        return direction == Direction.NORTH || direction == Direction.SOUTH ? random.nextBoolean() ? Direction.EAST : Direction.WEST : random.nextBoolean() ? Direction.NORTH : Direction.SOUTH;
    }
}
