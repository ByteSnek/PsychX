package bytesnek.tq.level.world;

import xyz.snaker.snakerlib.utility.Worlds;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;

import bytesnek.tq.rego.Levels;

/**
 * Created by SnakerBone on 10/9/2023
 **/
@FunctionalInterface
public interface EntitySpawner
{
    EntitySpawner OVERWORLD = (level, pos, random, chance) -> Worlds.canSeeSky(level, pos) && Worlds.isDay(level) && random.nextDouble() < chance;
    EntitySpawner COMATOSE = (level, pos, random, chance) -> Worlds.isDimension(level, Levels.COMATOSE) && random.nextDouble() < chance;

    boolean check(ServerLevelAccessor level, BlockPos pos, RandomSource random, double chance);
}
