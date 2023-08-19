package xyz.snaker.snakerlib.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IForgeShearable;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 19/08/2023
 **/
public class TallPlantBlock extends PlantBlock implements BonemealableBlock, IForgeShearable
{
    /**
     * The voxel shape of this block
     **/
    private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 13, 14);

    public TallPlantBlock(Properties properties, TagKey<Block> allowedBlocks, boolean allowDirt)
    {
        super(properties, allowedBlocks, SHAPE, allowDirt);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient)
    {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state)
    {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state)
    {
        DoublePlantBlock block = state.is(Blocks.FERN) ? (DoublePlantBlock) Blocks.LARGE_FERN : (DoublePlantBlock) Blocks.TALL_GRASS;
        if (block.defaultBlockState().canSurvive(level, pos) && level.isEmptyBlock(pos.above())) {
            DoublePlantBlock.placeAt(level, state, pos, Block.UPDATE_CLIENTS);
        }
    }
}