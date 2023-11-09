package xyz.snaker.tq.level.world.biome;

import xyz.snaker.tq.level.world.candidate.BiomeCandidate;
import xyz.snaker.tq.level.world.candidate.FeatureCandidate;
import xyz.snaker.tq.level.world.candidate.SpawnCandidate;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

/**
 * Created by SnakerBone on 9/11/2023
 **/
public abstract class TourniquetedBiome implements BiomeCreator
{
    private final BiomeCandidate candidate;
    private final MobSpawnSettings.Builder spawns;
    private final BiomeGenerationSettings.Builder generation;

    public TourniquetedBiome(BiomeCandidate candidate, BootstapContext<Biome> context)
    {
        this.candidate = candidate;
        this.spawns = candidate.getMobSpawnSettingsBuilder();
        this.generation = candidate.getBiomeGenerationSettingsBuilder(context);
    }

    public void addSpawn(SpawnCandidate candidate)
    {
        spawns.addSpawn(candidate.getCategory(), new MobSpawnSettings.SpawnerData(candidate.getEntity(), candidate.getWeight(), candidate.getMinCount(), candidate.getMaxCount()));
    }

    public void addFeature(FeatureCandidate type)
    {
        generation.addFeature(type.getDecoration(), type.getPlacedFeatureKey());
    }

    public void addPlants()
    {
        addFeature(FeatureCandidate.TALL_SNAKEROOT);
        addFeature(FeatureCandidate.SNAKEROOT);
        addFeature(FeatureCandidate.SPLITLEAF);
        addFeature(FeatureCandidate.CATNIP);
        addFeature(FeatureCandidate.PINKTAILS);
    }

    public void addCarvers()
    {
        generation.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);
        generation.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND);
        generation.addCarver(GenerationStep.Carving.AIR, Carvers.CANYON);
    }

    @Override
    public Biome create()
    {
        return candidate.create(spawns, generation);
    }
}
