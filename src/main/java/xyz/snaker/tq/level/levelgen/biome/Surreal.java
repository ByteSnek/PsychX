package xyz.snaker.tq.level.levelgen.biome;

import xyz.snaker.tq.level.levelgen.candidate.BiomeCandidate;
import xyz.snaker.tq.level.levelgen.candidate.FeatureCandidate;
import xyz.snaker.tq.level.levelgen.candidate.SpawnCandidate;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.Biome;

/**
 * Created by SnakerBone on 23/08/2023
 **/
public class Surreal extends TourniquetedBiome
{
    public Surreal(BootstapContext<Biome> context)
    {
        super(BiomeCandidate.SURREAL, context);
    }

    @Override
    public Biome create()
    {
        addFeature(FeatureCandidate.FLARE_RUBBLE);
        addFeature(FeatureCandidate.BURNING_RUBBLE);

        addPlants();
        addCarvers();

        addSpawn(SpawnCandidate.FLARE);
        addSpawn(SpawnCandidate.COSMO);

        return super.create();
    }
}
