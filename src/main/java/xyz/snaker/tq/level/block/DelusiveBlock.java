package xyz.snaker.tq.level.block;

import xyz.snaker.snakerlib.client.noise.SimplexNoise;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by SnakerBone on 15/07/2023
 **/
public class DelusiveBlock extends Block
{
    public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 15);

    public DelusiveBlock(Properties properties)
    {
        super(properties);
    }

    private static float getFractalNoise(int iteration, float size, BlockPos pos)
    {
        return iteration == 0 ? 0 : ((SimplexNoise.create(
                (pos.getX() + (iteration * size)) / size,
                (pos.getY() + (iteration * size)) / size,
                (pos.getZ() + (iteration * size)) / size) + 1F) * 0.5F) +
                getFractalNoise(iteration - 1, size, pos);
    }

    public static float fractalNoise(int iterations, float size, BlockPos pos)
    {
        return getFractalNoise(iterations, size, pos) / iterations;
    }

    private static int calculateVariant(BlockPos pos)
    {
        return (int) ((fractalNoise(3, 48F, pos) * 120F) % 16F) % 16;
    }

    public static float rippleFractialNoise(int iterations, float size, BlockPos pos, float minimum, float maximum, float frequency)
    {
        float diff = maximum - minimum;
        return Math.abs(((getFractalNoise(iterations, size, pos) * frequency) % (2 * diff)) - diff) + minimum;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context)
    {
        return defaultBlockState().setValue(VARIANT, calculateVariant(context.getClickedPos()));
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighbourState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighbourPos)
    {
        return defaultBlockState().setValue(VARIANT, calculateVariant(pos));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(VARIANT);
    }
}
