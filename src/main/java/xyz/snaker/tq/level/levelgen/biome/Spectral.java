package xyz.snaker.tq.level.levelgen.biome;

import xyz.snaker.tq.level.levelgen.candidate.BiomeCandidate;
import xyz.snaker.tq.level.levelgen.candidate.FeatureCandidate;
import xyz.snaker.tq.level.levelgen.candidate.SpawnCandidate;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.Biome;

/**
 * Created by SnakerBone on 23/08/2023
 **/
public class Spectral extends TourniquetedBiome
{
    public Spectral(BootstapContext<Biome> context)
    {
        super(BiomeCandidate.SPECTRAL, context);
    }

    @Override
    public Biome create()
    {
        addFeature(FeatureCandidate.SWIRL_RUBBLE);
        addFeature(FeatureCandidate.WATERCOLOUR_RUBBLE);

        addPlants();
        addCarvers();

        addSpawn(SpawnCandidate.COSMO);
        addSpawn(SpawnCandidate.SNIPE);
        addSpawn(SpawnCandidate.FROLICKER);

        return super.create();
    }
}
