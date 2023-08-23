package xyz.snaker.tq.level.world.biome;

import xyz.snaker.tq.level.world.feature.Features;
import xyz.snaker.tq.rego.Entities;
import xyz.snaker.tq.rego.Sounds;
import xyz.snaker.tq.utility.TqWorldGen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

/**
 * Created by SnakerBone on 23/08/2023
 **/
public class Illusion
{
    public static Biome create(BootstapContext<Biome> context)
    {
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder biomes = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomes);
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomes);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomes);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomes);
        BiomeDefaultFeatures.addDefaultSprings(biomes);
        BiomeDefaultFeatures.addSurfaceFreezing(biomes);

        biomes.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);
        biomes.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, Features.PlacementKey.GEOMETRIC.key());
        biomes.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PlacementKey.SPLITLEAF.key());
        biomes.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PlacementKey.CATNIP.key());

        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(Entities.COSMO.get(), 1, 1, 1));
        spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(Entities.LEET.get(), 1, 1, 1));

        return TqWorldGen.ABSTRACT_BIOME.apply(biomes.build(), spawns.build(), TqWorldGen.ABSTRACT_EFFECTS.apply(Sounds.RANDOM_FX));
    }
}
