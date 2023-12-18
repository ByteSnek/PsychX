package xyz.snaker.tq.level.levelgen;

import xyz.snaker.snakerlib.utility.Worlds;
import xyz.snaker.tq.rego.Levels;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;

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
