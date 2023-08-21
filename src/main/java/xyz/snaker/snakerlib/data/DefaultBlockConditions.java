package xyz.snaker.snakerlib.data;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Created by SnakerBone on 21/08/2023
 **/
@FunctionalInterface
public interface DefaultBlockConditions
{
    DefaultBlockConditions ALWAYS = (state, level, pos) -> true;
    DefaultBlockConditions NEVER = (state, level, pos) -> false;

    boolean apply(BlockState state, BlockGetter level, BlockPos pos);
}
