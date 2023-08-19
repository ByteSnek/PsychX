package xyz.snaker.snakerlib.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by SnakerBone on 18/08/2023
 * <p>
 * A bush block that fixes the issue in {@link BushBlock#canSurvive(BlockState, LevelReader, BlockPos)}
 **/
public class PlantBlock extends BushBlock
{
    /**
     * Checks whether this can be placed on dirt or not
     **/
    private final boolean allowDirt;

    /**
     * Blocks that this can be placed on
     **/
    private final TagKey<Block> allowedBlocks;

    /**
     * The voxel shape of this block
     **/
    public final VoxelShape shape;

    public PlantBlock(Properties properties, TagKey<Block> allowedBlocks, @Nullable VoxelShape shape, boolean allowDirt)
    {
        super(properties);
        this.allowedBlocks = allowedBlocks;
        this.shape = shape;
        this.allowDirt = allowDirt;
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, CollisionContext context)
    {
        return shape == null ? super.getShape(state, level, pos, context) : shape;
    }

    @Override
    public boolean mayPlaceOn(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos)
    {
        return allowDirt ? state.is(allowedBlocks) || super.mayPlaceOn(state, level, pos) : state.is(allowedBlocks);
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos)
    {
        return !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos)
    {
        return mayPlaceOn(level.getBlockState(pos.below()), level, pos.below());
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos)
    {
        return state.getFluidState().isEmpty();
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull PathComputationType type)
    {
        return type == PathComputationType.AIR && !hasCollision || super.isPathfindable(state, level, pos, type);
    }

    @Override
    public @NotNull BlockState getPlant(@NotNull BlockGetter level, @NotNull BlockPos pos)
    {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() != this) {
            return defaultBlockState();
        }
        return state;
    }
}
