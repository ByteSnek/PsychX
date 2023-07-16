package snaker.snakerlib.utility;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;

import java.util.function.BiPredicate;

public interface BlockPredicate extends BiPredicate<WorldGenLevel, BlockPos>
{
    default boolean matches(WorldGenLevel world, BlockPos pos)
    {
        return test(world, pos);
    }
}
