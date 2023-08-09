package xyz.snaker.tq.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;
import xyz.snaker.tq.rego.Rego;

/**
 * Created by SnakerBone on 15/07/2023
 **/
public class ComatoseNyliumBlock extends Block implements BonemealableBlock
{
    public ComatoseNyliumBlock()
    {
        super(Properties.of().mapColor(MapColor.NONE).requiresCorrectToolForDrops().strength(0.4F).sound(SoundType.NETHER_ORE).randomTicks());
    }

    private static boolean canBeNylium(BlockState state, LevelReader reader, BlockPos pos)
    {
        BlockPos posAbove = pos.above();
        BlockState statePosAbove = reader.getBlockState(posAbove);
        int lightLevel = LightEngine.getLightBlockInto(reader, state, pos, statePosAbove, posAbove, Direction.UP, statePosAbove.getLightBlock(reader, posAbove));
        return lightLevel < reader.getMaxLightLevel();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random)
    {
        if (!canBeNylium(state, level, pos)) {
            level.setBlockAndUpdate(pos, Rego.BLOCK_COMA_STONE.get().defaultBlockState());
        }
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient)
    {
        return level.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state)
    {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state)
    {

    }
}
