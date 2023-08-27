package xyz.snaker.tq.utility;

import xyz.snaker.tq.level.world.feature.FeatureKey;

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
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureKey.GEOMETRIC.getPlacedKey());
    }

    public static void addDefaultPlants(BiomeGenerationSettings.Builder builder)
    {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureKey.SNAKEROOT.getPlacedKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureKey.TALL_SNAKEROOT.getPlacedKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureKey.SPLITLEAF.getPlacedKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureKey.CATNIP.getPlacedKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureKey.PINKTAILS.getPlacedKey());
    }

    public static void addSwirlRubble(BiomeGenerationSettings.Builder builder)
    {
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FeatureKey.SWIRL_RUBBLE.getPlacedKey());
    }

    public static void addFlareRubble(BiomeGenerationSettings.Builder builder)
    {
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FeatureKey.FLARE_RUBBLE.getPlacedKey());
    }

    public static void addWaterColourRubble(BiomeGenerationSettings.Builder builder)
    {
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FeatureKey.WATERCOLOUR_RUBBLE.getPlacedKey());
    }
}
