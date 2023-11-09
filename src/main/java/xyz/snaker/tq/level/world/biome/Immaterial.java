package xyz.snaker.tq.level.world.biome;

import xyz.snaker.tq.level.world.candidate.BiomeCandidate;
import xyz.snaker.tq.level.world.candidate.FeatureCandidate;
import xyz.snaker.tq.level.world.candidate.SpawnCandidate;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.Biome;

/**
 * Created by SnakerBone on 23/08/2023
 **/
public class Immaterial extends TourniquetedBiome
{
    public Immaterial(BootstapContext<Biome> context)
    {
        super(BiomeCandidate.IMMATERIAL, context);
    }

    @Override
    public Biome create()
    {
        addFeature(FeatureCandidate.GEOMETRIC_RUBBLE);
        addFeature(FeatureCandidate.MULTICOLOUR_RUBBLE);

        addPlants();
        addCarvers();

        addSpawn(SpawnCandidate.SNIPE);
        addSpawn(SpawnCandidate.COSMIC_CREEPER);

        return super.create();
    }
}
