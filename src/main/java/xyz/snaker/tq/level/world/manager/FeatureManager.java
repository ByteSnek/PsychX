package xyz.snaker.tq.level.world.manager;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;

import static net.minecraft.world.level.levelgen.GenerationStep.Decoration.LOCAL_MODIFICATIONS;
import static net.minecraft.world.level.levelgen.GenerationStep.Decoration.VEGETAL_DECORATION;
import static net.minecraft.world.level.levelgen.feature.Feature.RANDOM_PATCH;
import static net.minecraft.world.level.levelgen.feature.Feature.TREE;
import static xyz.snaker.tq.rego.Blocks.*;
import static xyz.snaker.tq.rego.Features.*;
import static xyz.snaker.tq.utility.WorldGenStuff.*;

/**
 * Created by SnakerBone on 3/08/2023
 **/
public class FeatureManager
{
    public static void placements(BootstapContext<PlacedFeature> context)
    {
        registerPlacement(context, "catnip", simpleSurfacePlacement(6));
        registerPlacement(context, "splitleaf", simpleSurfacePlacement(6));
        registerPlacement(context, "snakeroot", simpleSurfacePlacement(2));
        registerPlacement(context, "tall_snakeroot", simpleSurfacePlacement(1));
        registerPlacement(context, "pinktails", simpleSurfacePlacement(2));
        registerPlacement(context, "swirl_rubble", simpleSurfacePlacement(2));
        registerPlacement(context, "flare_rubble", simpleSurfacePlacement(2));
        registerPlacement(context, "watercolour_rubble", simpleSurfacePlacement(2));
        registerPlacement(context, "burning_rubble", simpleSurfacePlacement(2));
        registerPlacement(context, "geometric_rubble", simpleSurfacePlacement(2));
        registerPlacement(context, "multicolour_rubble", simpleSurfacePlacement(2));
        registerPlacement(context, "snowflake_rubble", simpleSurfacePlacement(2));
        registerPlacement(context, "starry_rubble", simpleSurfacePlacement(2));
        registerPlacement(context, "foggy_rubble", simpleSurfacePlacement(2));
        registerPlacement(context, "geometric_tree", simpleTreePlacement(GEOMETRIC_SAPLING, 1, 0.1F, 1));
    }

    public static void configs(BootstapContext<ConfiguredFeature<?, ?>> context)
    {
        registerConfiguredFeature(context, "catnip", RANDOM_PATCH, simpleRandomConfig(PINKTAILS, 4));
        registerConfiguredFeature(context, "splitleaf", RANDOM_PATCH, simpleRandomConfig(SPLITLEAF, 4));
        registerConfiguredFeature(context, "snakeroot", RANDOM_PATCH, simpleRandomConfig(SNAKEROOT));
        registerConfiguredFeature(context, "tall_snakeroot", RANDOM_PATCH, simpleRandomConfig(TALL_SNAKEROOT));
        registerConfiguredFeature(context, "pinktails", RANDOM_PATCH, simpleRandomConfig(PINKTAILS));
        registerConfiguredFeature(context, "swirl_rubble", SWIRL_RUBBLE, SWIRL);
        registerConfiguredFeature(context, "flare_rubble", FLARE_RUBBLE, FLARE);
        registerConfiguredFeature(context, "watercolour_rubble", WATERCOLOUR_RUBBLE, WATERCOLOUR);
        registerConfiguredFeature(context, "burning_rubble", BURNING_RUBBLE, BURNING);
        registerConfiguredFeature(context, "geometric_rubble", GEOMETRIC_RUBBLE, GEOMETRIC);
        registerConfiguredFeature(context, "multicolour_rubble", MULTICOLOUR_RUBBLE, MULTICOLOUR);
        registerConfiguredFeature(context, "snowflake_rubble", SNOWFLAKE_RUBBLE, SNOWFLAKE);
        registerConfiguredFeature(context, "starry_rubble", STARRY_RUBBLE, STARRY);
        registerConfiguredFeature(context, "foggy_rubble", FOGGY_RUBBLE, FOGGY);
        registerConfiguredFeature(context, "geometric_tree", TREE, createGeometricTreeConfig(GEOMETRIC_LOG, GEOMETRIC, COMASTONE));
    }

    public static void modifiers(BootstapContext<BiomeModifier> context)
    {
        registerBiomeModifier(context, "catnip", VEGETAL_DECORATION);
        registerBiomeModifier(context, "splitleaf", VEGETAL_DECORATION);
        registerBiomeModifier(context, "snakeroot", VEGETAL_DECORATION);
        registerBiomeModifier(context, "tall_snakeroot", VEGETAL_DECORATION);
        registerBiomeModifier(context, "pinktails", VEGETAL_DECORATION);
        registerBiomeModifier(context, "swirl_rubble", LOCAL_MODIFICATIONS);
        registerBiomeModifier(context, "flare_rubble", LOCAL_MODIFICATIONS);
        registerBiomeModifier(context, "watercolour_rubble", LOCAL_MODIFICATIONS);
        registerBiomeModifier(context, "burning_rubble", LOCAL_MODIFICATIONS);
        registerBiomeModifier(context, "geometric_rubble", LOCAL_MODIFICATIONS);
        registerBiomeModifier(context, "multicolour_rubble", LOCAL_MODIFICATIONS);
        registerBiomeModifier(context, "snowflake_rubble", LOCAL_MODIFICATIONS);
        registerBiomeModifier(context, "starry_rubble", LOCAL_MODIFICATIONS);
        registerBiomeModifier(context, "foggy_rubble", LOCAL_MODIFICATIONS);
        registerBiomeModifier(context, "geometric_tree", VEGETAL_DECORATION);
    }
}
