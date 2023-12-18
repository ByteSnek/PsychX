package xyz.snaker.tq.level.levelgen.biome;

import java.util.LinkedList;

import xyz.snaker.tq.level.levelgen.candidate.BiomeCandidate;
import xyz.snaker.tq.level.levelgen.candidate.FeatureCandidate;
import xyz.snaker.tq.level.levelgen.candidate.SpawnCandidate;

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
    private final MobSpawnSettings.Builder spawnSettings;
    private final BiomeGenerationSettings.Builder generationSettings;

    private final LinkedList<FeatureCandidate> features = new LinkedList<>();

    public TourniquetedBiome(BiomeCandidate candidate, BootstapContext<Biome> context)
    {
        this.candidate = candidate;
        this.spawnSettings = candidate.getMobSpawnSettingsBuilder();
        this.generationSettings = candidate.getBiomeGenerationSettingsBuilder(context);
    }

    public void addSpawn(SpawnCandidate candidate)
    {
        spawnSettings.addSpawn(candidate.getCategory(), new MobSpawnSettings.SpawnerData(candidate.getEntity(), candidate.getWeight(), candidate.getMinCount(), candidate.getMaxCount()));
    }

    public void addFeature(FeatureCandidate candidate)
    {
        features.add(candidate);
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
        generationSettings.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);
        generationSettings.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND);
        generationSettings.addCarver(GenerationStep.Carving.AIR, Carvers.CANYON);
    }

    private void processFeatures()
    {
        for (FeatureCandidate feature : features) {
            generationSettings.addFeature(feature.getStep(), feature.getPlacedFeatureKey());
        }
    }

    @Override
    public Biome create()
    {
        addFeature(FeatureCandidate.COMA_SPIKE);
        processFeatures();

        return candidate.create(spawnSettings, generationSettings);
    }
}
