package xyz.snaker.tq.utility;

import xyz.snaker.tq.level.world.feature.manager.FeatureManager;

import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

/**
 * Created by SnakerBone on 24/08/2023
 **/
public class DefaultFeatures
{
    public static void addDefaultTrees(BiomeGenerationSettings.Builder builder)
    {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureManager.PlacementKey.GEOMETRIC.key());
    }

    public static void addDefaultPlants(BiomeGenerationSettings.Builder builder)
    {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureManager.PlacementKey.SNAKEROOT.key());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureManager.PlacementKey.TALL_SNAKEROOT.key());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureManager.PlacementKey.SPLITLEAF.key());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureManager.PlacementKey.CATNIP.key());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureManager.PlacementKey.PINKTAILS.key());
    }

    public static void addSwirlRubble(BiomeGenerationSettings.Builder builder)
    {
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FeatureManager.PlacementKey.SWIRL_RUBBLE.key());
    }
}
