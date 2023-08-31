package xyz.snaker.tq.utility;

import xyz.snaker.tq.level.world.feature.FeatureKey;
import xyz.snaker.tq.rego.Entities;

import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

/**
 * Created by SnakerBone on 24/08/2023
 **/
public class DefaultFeatures
{
    public static void addDefaultTrees(BiomeGenerationSettings.Builder builder)
    {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureKey.GEOMETRIC_TREE.getPlacedKey());
    }

    public static void addDefaultPlants(BiomeGenerationSettings.Builder builder)
    {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureKey.SNAKEROOT.getPlacedKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureKey.TALL_SNAKEROOT.getPlacedKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureKey.SPLITLEAF.getPlacedKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureKey.CATNIP.getPlacedKey());
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureKey.PINKTAILS.getPlacedKey());
    }

    public static void addDefaultRubble(BiomeGenerationSettings.Builder builder)
    {
        addSwirlRubble(builder);
        addFlareRubble(builder);
        addWaterColourRubble(builder);
        addBurningRubble(builder);
        addGeometricRubble(builder);
        addMultiColourRubble(builder);
        addSnowflakeRubble(builder);
        addStarryRubble(builder);
        addFoggyRubble(builder);
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

    public static void addBurningRubble(BiomeGenerationSettings.Builder builder)
    {
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FeatureKey.BURNING_RUBBLE.getPlacedKey());
    }

    public static void addGeometricRubble(BiomeGenerationSettings.Builder builder)
    {
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FeatureKey.GEOMETRIC_RUBBLE.getPlacedKey());
    }

    public static void addMultiColourRubble(BiomeGenerationSettings.Builder builder)
    {
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FeatureKey.MULTICOLOUR_RUBBLE.getPlacedKey());
    }

    public static void addSnowflakeRubble(BiomeGenerationSettings.Builder builder)
    {
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FeatureKey.SNOWFLAKE_RUBBLE.getPlacedKey());
    }

    public static void addStarryRubble(BiomeGenerationSettings.Builder builder)
    {
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FeatureKey.STARRY_RUBBLE.getPlacedKey());
    }

    public static void addFoggyRubble(BiomeGenerationSettings.Builder builder)
    {
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FeatureKey.FOGGY_RUBBLE.getPlacedKey());
    }

    public static void addDefaultEntitySpawns(MobSpawnSettings.Builder builder)
    {
        WorldGenStuff.addSpawn(builder, Entities.FROLICKER, 5, 1, 4);
        WorldGenStuff.addSpawn(builder, Entities.SNIPE, 7, 1, 3);
    }
}
