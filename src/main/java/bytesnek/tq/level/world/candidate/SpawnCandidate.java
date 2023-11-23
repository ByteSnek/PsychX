package bytesnek.tq.level.world.candidate;

import java.util.function.Supplier;

import bytesnek.snakerlib.resources.ResourceReference;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

import bytesnek.tq.rego.Entities;

/**
 * Created by SnakerBone on 2/11/2023
 **/
public enum SpawnCandidate
{
    FLUTTERFLY(Entities.FLUTTERFLY, MobCategory.CREATURE, 35, 1, 3),
    FROLICKER(Entities.FROLICKER, MobCategory.CREATURE, 35, 1, 3),
    SNIPE(Entities.SNIPE, MobCategory.MONSTER, 10, 1, 1),
    FLARE(Entities.FLARE, MobCategory.MONSTER, 15, 1, 2),
    COSMIC_CREEPER(Entities.COSMIC_CREEPER, MobCategory.MONSTER, 10, 1, 1),
    COSMO(Entities.COSMO, MobCategory.MONSTER, 15, 1, 2);

    private final String name;

    private final MobSpawnSettings.SpawnerData spawnerData;
    private final ResourceKey<BiomeModifier> modifierKey;
    private final EntityType<?> entity;
    private final MobCategory category;

    private final int weight;
    private final int minCount;
    private final int maxCount;

    <T extends Entity> SpawnCandidate(Supplier<EntityType<T>> entity, MobCategory category, int weight, int minCount, int maxCount)
    {
        this.name = name().toLowerCase();
        this.spawnerData = new MobSpawnSettings.SpawnerData(entity.get(), weight, minCount, maxCount);
        this.modifierKey = createBiomeModifierKey(name);
        this.entity = entity.get();
        this.category = category;
        this.weight = weight;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    static ResourceKey<BiomeModifier> createBiomeModifierKey(String name)
    {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceReference(name));
    }

    public MobSpawnSettings.SpawnerData getSpawnerData()
    {
        return spawnerData;
    }

    public ResourceKey<BiomeModifier> getModifierKey()
    {
        return modifierKey;
    }

    public EntityType<?> getEntity()
    {
        return entity;
    }

    public MobCategory getCategory()
    {
        return category;
    }

    public int getWeight()
    {
        return weight;
    }

    public int getMinCount()
    {
        return minCount;
    }

    public int getMaxCount()
    {
        return maxCount;
    }
}
