package xyz.snaker.tq.level.world;

import xyz.snaker.snakerlib.utility.tools.WorldStuff;
import xyz.snaker.tq.rego.Levels;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;

/**
 * Created by SnakerBone on 10/9/2023
 **/
@FunctionalInterface
public interface EntitySpawner
{
    EntitySpawner BUTTERFLY = (level, pos, random, weight) -> EntitySpawner.OVERWORLD.check(level, pos, random, Mth.clamp(random.nextInt(), 5, weight)) && (level.getBiome(pos).is(BiomeTags.IS_JUNGLE) || level.getBiome(pos).is(BiomeTags.IS_MOUNTAIN));
    EntitySpawner OVERWORLD = (level, pos, random, weight) -> WorldStuff.canSeeSky(level, pos) && WorldStuff.isDay(level) && WorldStuff.random(random, weight);
    EntitySpawner COMATOSE = (level, pos, random, weight) -> WorldStuff.isDimension(level, Levels.COMATOSE) && WorldStuff.random(random, weight);

    boolean check(ServerLevelAccessor level, BlockPos pos, RandomSource random, int weight);
}
