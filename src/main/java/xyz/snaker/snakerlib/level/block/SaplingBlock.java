package xyz.snaker.snakerlib.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 18/08/2023
 **/
public class SaplingBlock extends PlantBlock implements BonemealableBlock
{
    /**
     * This sapling's current stage property as an integer
     **/
    private static final IntegerProperty STAGE = BlockStateProperties.STAGE;

    /**
     * The voxel shape of this block
     **/
    private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 12, 14);

    /**
     * The tree grower responsible for growing this sapling into a tree
     **/
    private final AbstractTreeGrower treeGrower;

    public SaplingBlock(AbstractTreeGrower treeGrower, Properties properties, TagKey<Block> allowedBlocks, boolean allowDirt)
    {
        super(properties, allowedBlocks, SHAPE, allowDirt);
        this.treeGrower = treeGrower;
        this.registerDefaultState(0);
    }

    /**
     * Advances this sapling to the next growing stage
     *
     * @param level  The current server level
     * @param pos    The position of this sapling
     * @param state  The block state of this sapling
     * @param random Random source generator
     **/
    private void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random)
    {
        var stage = state.getValue(STAGE);
        var cycle = state.cycle(STAGE);
        var generator = level.getChunkSource().getGenerator();
        if (stage == 0) {
            level.setBlock(pos, cycle, Block.UPDATE_INVISIBLE);
        } else {
            treeGrower.growTree(level, generator, pos, state, random);
        }
    }

    /**
     * Registers this saplings default state property
     *
     * @param value The value of the stage
     **/
    private void registerDefaultState(int value)
    {
        registerDefaultState(stateDefinition.any().setValue(STAGE, value));
    }

    @Override
    @SuppressWarnings({"RedundantSuppression", "deprecation"})
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context)
    {
        return SHAPE;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(@NotNull BlockState state, ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random)
    {
        if (level.isLoaded(pos)) {
            if (level.getMaxLocalRawBrightness(pos.above()) >= 9 && random.nextInt(7) == 0) {
                advanceTree(level, pos, state, random);
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient)
    {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state)
    {
        return level.random.nextFloat() < 0.45;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, BlockState state)
    {
        advanceTree(level, pos, state, random);
    }

    @Override
    public void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(STAGE);
    }
}
