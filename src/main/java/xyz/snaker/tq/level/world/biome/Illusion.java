package xyz.snaker.tq.level.world.biome;

import xyz.snaker.tq.level.world.candidate.BiomeCandidate;
import xyz.snaker.tq.level.world.candidate.FeatureCandidate;
import xyz.snaker.tq.level.world.candidate.SpawnCandidate;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.Biome;

/**
 * Created by SnakerBone on 23/08/2023
 **/
public class Illusion extends TourniquetedBiome
{
    public Illusion(BootstapContext<Biome> context)
    {
        super(BiomeCandidate.ILLUSION, context);
    }

    @Override
    public Biome create()
    {
        addFeature(FeatureCandidate.ILLUSIVE_TREE);
        addFeature(FeatureCandidate.SNOWFLAKE_RUBBLE);
        addFeature(FeatureCandidate.STARRY_RUBBLE);

        addPlants();
        addCarvers();

        addSpawn(SpawnCandidate.COSMO);
        addSpawn(SpawnCandidate.FROLICKER);

        return super.create();
    }
}
