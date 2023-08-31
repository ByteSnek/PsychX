package xyz.snaker.snakerlib.utility.tools;

import javax.annotation.Nullable;

import xyz.snaker.snakerlib.math.Maths;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by SnakerBone on 4/06/2023
 **/
public class WorldStuff
{
    /**
     * Adds a biome spawn
     *
     * @param builder  The spawn settings builder
     * @param category The mob category
     * @param entity   The entity
     * @param weight   The likeliness of the spawn to happen
     * @param minCount The minimum mob count of each spawn
     * @param maxCount The maximum mob count of each spawn
     * @return The builder
     **/
    public static <T extends LivingEntity> MobSpawnSettings.Builder addBiomeSpawn(MobSpawnSettings.Builder builder, MobCategory category, RegistryObject<EntityType<T>> entity, int weight, int minCount, int maxCount)
    {
        return builder.addSpawn(category, new MobSpawnSettings.SpawnerData(entity.get(), weight, minCount, maxCount));
    }

    /**
     * Checks the current dimension
     *
     * @param level  The current level accessor
     * @param wanted The resource key of the wanted dimension
     * @return True if the current dimension is the wanted dimension
     **/
    public static boolean isDimension(ServerLevelAccessor level, ResourceKey<Level> wanted)
    {
        return level.getLevel().dimension().equals(wanted);
    }

    public static boolean isBiome(ServerLevelAccessor level, BlockPos pos, ResourceKey<Biome> wanted)
    {
        return level.getLevel().getBiome(pos).is(wanted);
    }

    public static boolean isOverworld(ServerLevelAccessor level)
    {
        return isDimension(level, Level.OVERWORLD);
    }

    public static boolean isNether(ServerLevelAccessor level)
    {
        return isDimension(level, Level.NETHER);
    }

    public static boolean isEnd(ServerLevelAccessor level)
    {
        return isDimension(level, Level.END);
    }

    public static boolean canSeeSky(ServerLevelAccessor level, BlockPos pos)
    {
        return isOverworld(level) && level.getLevel().canSeeSky(pos);
    }

    public static boolean isDay(ServerLevelAccessor level)
    {
        return level.getLevel().isDay();
    }

    public static boolean isNight(ServerLevelAccessor level)
    {
        return level.getLevel().isNight();
    }

    public static boolean hasDaylightCycle(ServerLevelAccessor level)
    {
        return level.getLevel().dimensionType().bedWorks() && !level.getLevel().dimensionType().hasFixedTime();
    }

    public static boolean random(@Nullable RandomSource random, int bound)
    {
        RandomSource source = random == null ? RandomSource.create() : random;
        return source.nextInt(bound) == 0;
    }

    /**
     * Checks the current dimension
     *
     * @param level  The current level
     * @param wanted The resource key of the wanted dimension
     * @return True if the current dimension is the wanted dimension
     **/
    public static boolean isDimension(Level level, ResourceKey<Level> wanted)
    {
        return level.dimension().equals(wanted);
    }

    public static <T extends Entity> AABB getWorldBoundingBox(T entity)
    {
        return new AABB(entity.blockPosition()).inflate(Maths.LEVEL_AABB_RADIUS);
    }

    public static Direction getRandomHorizontalDirection(RandomSource random)
    {
        Direction[] directions = Direction.stream().filter(dir -> !(dir == Direction.UP || dir == Direction.DOWN)).toArray(Direction[]::new);
        return directions[random.nextInt(directions.length)];
    }

    public static Direction getRandomVerticalDirection(RandomSource random)
    {
        Direction[] directions = Direction.stream().filter(dir -> !(dir == Direction.NORTH || dir == Direction.SOUTH || dir == Direction.EAST || dir == Direction.WEST)).toArray(Direction[]::new);
        return directions[random.nextInt(directions.length)];
    }

    public static <L extends LevelReader> boolean isFreeAroundPos(L level, BlockPos pos, boolean vertical)
    {
        boolean checkVertical = !vertical || (state(level, pos.above()).is(Blocks.AIR) && state(level, pos.below()).is(Blocks.AIR));
        return state(level, pos.north()).is(Blocks.AIR) && state(level, pos.south()).is(Blocks.AIR) && state(level, pos.east()).is(Blocks.AIR) && state(level, pos.west()).is(Blocks.AIR) && checkVertical;
    }

    private static <L extends LevelReader> BlockState state(L level, BlockPos pos)
    {
        return level.getBlockState(pos);
    }
}
