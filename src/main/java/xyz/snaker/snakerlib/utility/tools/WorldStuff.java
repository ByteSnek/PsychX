package xyz.snaker.snakerlib.utility.tools;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.MobSpawnSettings;
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
}
