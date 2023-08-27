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
        registerPlacement(context, "geometric", simpleTreePlacement(GEOMETRIC_SAPLING, 1, 0.1F, 1));
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
        registerConfiguredFeature(context, "geometric", TREE, createGeometricTreeConfig(GEOMETRIC_LOG, GEOMETRIC, COMASTONE));
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
        registerBiomeModifier(context, "geometric", VEGETAL_DECORATION);
    }
}
