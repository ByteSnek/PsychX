package xyz.snaker.tq.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import static xyz.snaker.tq.rego.Blocks.Tags.COMATOSE_BLOCKS;

/**
 * Created by SnakerBone on 9/12/2023
 **/
public class ComaFireBlock extends BaseFireBlock
{
    public static final Properties PROPERTIES = BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_MAGENTA)
            .replaceable()
            .noCollission()
            .instabreak()
            .lightLevel(state -> 10)
            .sound(SoundType.WOOL)
            .pushReaction(PushReaction.DESTROY);

    public ComaFireBlock()
    {
        super(PROPERTIES, 2);
    }

    @Override
    @ApiStatus.OverrideOnly
    @SuppressWarnings("deprecation")
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos)
    {
        return canSurvive(state, level, pos) ? defaultBlockState() : Blocks.AIR.defaultBlockState();
    }

    @Override
    @ApiStatus.OverrideOnly
    @SuppressWarnings("deprecation")
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos)
    {
        BlockPos below = pos.below();
        BlockState stateBelow = level.getBlockState(below);

        return stateBelow.is(COMATOSE_BLOCKS);
    }

    @Override
    protected boolean canBurn(@NotNull BlockState state)
    {
        return true;
    }
}
