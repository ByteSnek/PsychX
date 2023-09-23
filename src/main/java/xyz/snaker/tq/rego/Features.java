package xyz.snaker.tq.rego;

import java.util.function.Supplier;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.world.feature.*;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraft.world.level.levelgen.GenerationStep.Decoration.LOCAL_MODIFICATIONS;
import static net.minecraft.world.level.levelgen.GenerationStep.Decoration.VEGETAL_DECORATION;
import static net.minecraft.world.level.levelgen.feature.Feature.RANDOM_PATCH;
import static net.minecraft.world.level.levelgen.feature.Feature.TREE;
import static xyz.snaker.tq.rego.Blocks.*;
import static xyz.snaker.tq.utility.WorldGenStuff.*;

/**
 * Created by SnakerBone on 25/08/2023
 **/
public class Features
{
    public static final DeferredRegister<Feature<?>> REGISTRAR = DeferredRegister.create(Registries.FEATURE, Tourniqueted.MODID);

    public static final RegistryObject<SwirlRubbleFeature> SWIRL_RUBBLE = registerRubble("swirl", () -> new SwirlRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<FlareRubbleFeature> FLARE_RUBBLE = registerRubble("flare", () -> new FlareRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<WaterColourRubbleFeature> WATERCOLOUR_RUBBLE = registerRubble("watercolour", () -> new WaterColourRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<BurningRubbleFeature> BURNING_RUBBLE = registerRubble("burning", () -> new BurningRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<GeometricRubbleFeature> GEOMETRIC_RUBBLE = registerRubble("geometric", () -> new GeometricRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<MultiColourRubbleFeature> MULTICOLOUR_RUBBLE = registerRubble("multicolour", () -> new MultiColourRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<SnowflakeRubbleFeature> SNOWFLAKE_RUBBLE = registerRubble("snowflake", () -> new SnowflakeRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<StarryRubbleFeature> STARRY_RUBBLE = registerRubble("starry", () -> new StarryRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<FoggyRubbleFeature> FOGGY_RUBBLE = registerRubble("foggy", () -> new FoggyRubbleFeature(BlockStateConfiguration.CODEC));

    public static void placedFeatures(BootstapContext<PlacedFeature> context)
    {
        registerPlacement(context, "catnip", simpleSurfacePlacement(1));
        registerPlacement(context, "splitleaf", simpleSurfacePlacement(1));
        registerPlacement(context, "snakeroot", simpleSurfacePlacement(1));
        registerPlacement(context, "tall_snakeroot", simpleSurfacePlacement(1));
        registerPlacement(context, "pinktails", simpleSurfacePlacement(1));
        registerPlacement(context, "swirl_rubble", simpleSurfacePlacement(5));
        registerPlacement(context, "flare_rubble", simpleSurfacePlacement(5));
        registerPlacement(context, "watercolour_rubble", simpleSurfacePlacement(5));
        registerPlacement(context, "burning_rubble", simpleSurfacePlacement(5));
        registerPlacement(context, "geometric_rubble", simpleSurfacePlacement(5));
        registerPlacement(context, "multicolour_rubble", simpleSurfacePlacement(5));
        registerPlacement(context, "snowflake_rubble", simpleSurfacePlacement(5));
        registerPlacement(context, "starry_rubble", simpleSurfacePlacement(5));
        registerPlacement(context, "foggy_rubble", simpleSurfacePlacement(5));
        registerPlacement(context, "illusive_tree", simpleTreePlacement(ILLUSIVE_SAPLING, 2));
        registerPlacement(context, "delusive_tree", simpleTreePlacement(DELUSIVE_SAPLING, 2));
    }

    public static void configuredFeatures(BootstapContext<ConfiguredFeature<?, ?>> context)
    {
        registerConfiguredFeature(context, "catnip", RANDOM_PATCH, simpleRandomConfig(PINKTAILS, 4));
        registerConfiguredFeature(context, "splitleaf", RANDOM_PATCH, simpleRandomConfig(SPLITLEAF, 4));
        registerConfiguredFeature(context, "snakeroot", RANDOM_PATCH, simpleRandomConfig(SNAKEROOT, 32));
        registerConfiguredFeature(context, "tall_snakeroot", RANDOM_PATCH, simpleRandomConfig(TALL_SNAKEROOT, 32));
        registerConfiguredFeature(context, "pinktails", RANDOM_PATCH, simpleRandomConfig(PINKTAILS, 32));
        registerConfiguredFeature(context, "swirl_rubble", SWIRL_RUBBLE, SWIRL);
        registerConfiguredFeature(context, "flare_rubble", FLARE_RUBBLE, FLARE);
        registerConfiguredFeature(context, "watercolour_rubble", WATERCOLOUR_RUBBLE, WATERCOLOUR);
        registerConfiguredFeature(context, "burning_rubble", BURNING_RUBBLE, BURNING);
        registerConfiguredFeature(context, "geometric_rubble", GEOMETRIC_RUBBLE, GEOMETRIC);
        registerConfiguredFeature(context, "multicolour_rubble", MULTICOLOUR_RUBBLE, MULTICOLOUR);
        registerConfiguredFeature(context, "snowflake_rubble", SNOWFLAKE_RUBBLE, SNOWFLAKE);
        registerConfiguredFeature(context, "starry_rubble", STARRY_RUBBLE, STARRY);
        registerConfiguredFeature(context, "foggy_rubble", FOGGY_RUBBLE, FOGGY);
        registerConfiguredFeature(context, "illusive_tree", TREE, createIllusiveTree(ILLUSIVE_LOG, ILLUSIVE_LEAVES, COMASTONE));
        registerConfiguredFeature(context, "delusive_tree", TREE, createDelusiveTree(DELUSIVE_LOG, DELUSIVE_LEAVES, COMASTONE));
    }

    public static void biomeModifiers(BootstapContext<BiomeModifier> context)
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
        registerBiomeModifier(context, "illusive_tree", VEGETAL_DECORATION);
        registerBiomeModifier(context, "delusive_tree", VEGETAL_DECORATION);
    }

    static <F extends Feature<?>> RegistryObject<F> register(String name, Supplier<F> feature)
    {
        return REGISTRAR.register(name, feature);
    }

    static <F extends Feature<?>> RegistryObject<F> registerRubble(String name, Supplier<F> feature)
    {
        return register(name + "_rubble", feature);
    }
}
