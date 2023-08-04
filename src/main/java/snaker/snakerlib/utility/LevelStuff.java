package snaker.snakerlib.utility;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.registries.RegistryObject;

public class LevelStuff
{
    public static <T extends LivingEntity> MobSpawnSettings.Builder addBiomeSpawn(MobSpawnSettings.Builder builder, MobCategory category, RegistryObject<EntityType<T>> entity, int weight, int minCount, int maxCount)
    {
        return builder.addSpawn(category, new MobSpawnSettings.SpawnerData(entity.get(), weight, minCount, maxCount));
    }

    public static boolean isDimension(ServerLevelAccessor level, ResourceKey<Level> wanted)
    {
        return level.getLevel().dimension().equals(wanted);
    }

    public static boolean isDimension(Level level, ResourceKey<Level> wanted)
    {
        return level.dimension().equals(wanted);
    }
}
