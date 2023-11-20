package bytesnek.tq.level.world.biome;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.Biome;

import bytesnek.tq.level.world.candidate.BiomeCandidate;
import bytesnek.tq.level.world.candidate.FeatureCandidate;
import bytesnek.tq.level.world.candidate.SpawnCandidate;

/**
 * Created by SnakerBone on 23/08/2023
 **/
public class Delusion extends TourniquetedBiome
{
    public Delusion(BootstapContext<Biome> context)
    {
        super(BiomeCandidate.DELUSION, context);
    }

    @Override
    public Biome create()
    {
        addFeature(FeatureCandidate.DELUSIVE_TREE);
        addFeature(FeatureCandidate.BURNING_RUBBLE);
        addFeature(FeatureCandidate.UTTERFLY_SHOWGROUND);

        addPlants();
        addCarvers();

        addSpawn(SpawnCandidate.COSMIC_CREEPER);
        addSpawn(SpawnCandidate.SNIPE);
        addSpawn(SpawnCandidate.FROLICKER);

        return super.create();
    }
}
