package bytesnek.tq.level.world.biome;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.Biome;

import bytesnek.tq.level.world.candidate.BiomeCandidate;
import bytesnek.tq.level.world.candidate.FeatureCandidate;
import bytesnek.tq.level.world.candidate.SpawnCandidate;

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
