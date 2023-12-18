package xyz.snaker.tq.rego;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import xyz.snaker.hiss.sneaky.Sneaky;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.levelgen.candidate.FeatureCandidate;
import xyz.snaker.tq.level.levelgen.candidate.SpawnCandidate;
import xyz.snaker.tq.level.levelgen.feature.*;
import xyz.snaker.tq.level.levelgen.tree.IllusiveFoliagePlacer;
import xyz.snaker.tq.level.levelgen.tree.IllusiveTrunkPlacer;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.minecraft.world.level.levelgen.feature.Feature.RANDOM_PATCH;
import static net.minecraft.world.level.levelgen.feature.Feature.TREE;

/**
 * Created by SnakerBone on 25/08/2023
 **/
public class Features
{
    public static final DeferredRegister<Feature<?>> REGISTER = DeferredRegister.create(Registries.FEATURE, Tourniqueted.MODID);

    public static final Supplier<SwirlRubbleFeature> SWIRL_RUBBLE = registerRubble("swirl", () -> new SwirlRubbleFeature(BlockStateConfiguration.CODEC));
    public static final Supplier<FlareRubbleFeature> FLARE_RUBBLE = registerRubble("flare", () -> new FlareRubbleFeature(BlockStateConfiguration.CODEC));
    public static final Supplier<WaterColourRubbleFeature> WATERCOLOUR_RUBBLE = registerRubble("watercolour", () -> new WaterColourRubbleFeature(BlockStateConfiguration.CODEC));
    public static final Supplier<BurningRubbleFeature> BURNING_RUBBLE = registerRubble("burning", () -> new BurningRubbleFeature(BlockStateConfiguration.CODEC));
    public static final Supplier<GeometricRubbleFeature> GEOMETRIC_RUBBLE = registerRubble("geometric", () -> new GeometricRubbleFeature(BlockStateConfiguration.CODEC));
    public static final Supplier<MultiColourRubbleFeature> MULTICOLOUR_RUBBLE = registerRubble("multicolour", () -> new MultiColourRubbleFeature(BlockStateConfiguration.CODEC));
    public static final Supplier<SnowflakeRubbleFeature> SNOWFLAKE_RUBBLE = registerRubble("snowflake", () -> new SnowflakeRubbleFeature(BlockStateConfiguration.CODEC));
    public static final Supplier<StarryRubbleFeature> STARRY_RUBBLE = registerRubble("starry", () -> new StarryRubbleFeature(BlockStateConfiguration.CODEC));
    public static final Supplier<FoggyRubbleFeature> FOGGY_RUBBLE = registerRubble("foggy", () -> new FoggyRubbleFeature(BlockStateConfiguration.CODEC));
    public static final Supplier<ComaSpikeFeature> COMA_SPIKE = register("coma_spike", () -> new ComaSpikeFeature(NoneFeatureConfiguration.CODEC));

    public static void placedFeatures(BootstapContext<PlacedFeature> context)
    {
        registerPlacedFeature(context, FeatureCandidate.CATNIP, surfacePlacement(1));
        registerPlacedFeature(context, FeatureCandidate.SPLITLEAF, surfacePlacement(1));
        registerPlacedFeature(context, FeatureCandidate.SNAKEROOT, surfacePlacement(1));
        registerPlacedFeature(context, FeatureCandidate.TALL_SNAKEROOT, surfacePlacement(1));
        registerPlacedFeature(context, FeatureCandidate.PINKTAILS, surfacePlacement(1));
        registerPlacedFeature(context, FeatureCandidate.SWIRL_RUBBLE, surfacePlacement(5));
        registerPlacedFeature(context, FeatureCandidate.FLARE_RUBBLE, surfacePlacement(5));
        registerPlacedFeature(context, FeatureCandidate.WATERCOLOUR_RUBBLE, surfacePlacement(5));
        registerPlacedFeature(context, FeatureCandidate.BURNING_RUBBLE, surfacePlacement(5));
        registerPlacedFeature(context, FeatureCandidate.GEOMETRIC_RUBBLE, surfacePlacement(5));
        registerPlacedFeature(context, FeatureCandidate.MULTICOLOUR_RUBBLE, surfacePlacement(5));
        registerPlacedFeature(context, FeatureCandidate.SNOWFLAKE_RUBBLE, surfacePlacement(5));
        registerPlacedFeature(context, FeatureCandidate.STARRY_RUBBLE, surfacePlacement(5));
        registerPlacedFeature(context, FeatureCandidate.FOGGY_RUBBLE, surfacePlacement(5));
        registerPlacedFeature(context, FeatureCandidate.ILLUSIVE_TREE, treePlacement(Blocks.ILLUSIVE_SAPLING, 2));
        registerPlacedFeature(context, FeatureCandidate.DELUSIVE_TREE, treePlacement(Blocks.DELUSIVE_SAPLING, 2));
        registerPlacedFeature(context, FeatureCandidate.COMA_SPIKE, Collections.emptyList());
    }

    public static void configuredFeatures(BootstapContext<ConfiguredFeature<?, ?>> context)
    {
        registerConfiguredFeature(context, FeatureCandidate.CATNIP, RANDOM_PATCH, randomPatch(Blocks.PINKTAILS, 4));
        registerConfiguredFeature(context, FeatureCandidate.SPLITLEAF, RANDOM_PATCH, randomPatch(Blocks.SPLITLEAF, 4));
        registerConfiguredFeature(context, FeatureCandidate.SNAKEROOT, RANDOM_PATCH, randomPatch(Blocks.SNAKEROOT, 32));
        registerConfiguredFeature(context, FeatureCandidate.TALL_SNAKEROOT, RANDOM_PATCH, randomPatch(Blocks.TALL_SNAKEROOT, 32));
        registerConfiguredFeature(context, FeatureCandidate.PINKTAILS, RANDOM_PATCH, randomPatch(Blocks.PINKTAILS, 32));
        registerConfiguredFeature(context, FeatureCandidate.SWIRL_RUBBLE, SWIRL_RUBBLE, Blocks.SWIRL);
        registerConfiguredFeature(context, FeatureCandidate.FLARE_RUBBLE, FLARE_RUBBLE, Blocks.FLARE);
        registerConfiguredFeature(context, FeatureCandidate.WATERCOLOUR_RUBBLE, WATERCOLOUR_RUBBLE, Blocks.WATERCOLOUR);
        registerConfiguredFeature(context, FeatureCandidate.BURNING_RUBBLE, BURNING_RUBBLE, Blocks.BURNING);
        registerConfiguredFeature(context, FeatureCandidate.GEOMETRIC_RUBBLE, GEOMETRIC_RUBBLE, Blocks.GEOMETRIC);
        registerConfiguredFeature(context, FeatureCandidate.MULTICOLOUR_RUBBLE, MULTICOLOUR_RUBBLE, Blocks.MULTICOLOUR);
        registerConfiguredFeature(context, FeatureCandidate.SNOWFLAKE_RUBBLE, SNOWFLAKE_RUBBLE, Blocks.SNOWFLAKE);
        registerConfiguredFeature(context, FeatureCandidate.STARRY_RUBBLE, STARRY_RUBBLE, Blocks.STARRY);
        registerConfiguredFeature(context, FeatureCandidate.FOGGY_RUBBLE, FOGGY_RUBBLE, Blocks.FOGGY);
        registerConfiguredFeature(context, FeatureCandidate.ILLUSIVE_TREE, TREE, illusiveTree(Blocks.ILLUSIVE_LOG, Blocks.ILLUSIVE_LEAVES, Blocks.COMA_SOIL));
        registerConfiguredFeature(context, FeatureCandidate.DELUSIVE_TREE, TREE, delusiveTree(Blocks.DELUSIVE_LOG, Blocks.DELUSIVE_LEAVES, Blocks.COMA_SOIL));
        registerConfiguredFeature(context, FeatureCandidate.COMA_SPIKE, COMA_SPIKE.get(), NoneFeatureConfiguration.INSTANCE);
    }

    public static void biomeModifiers(BootstapContext<BiomeModifier> context)
    {
        registerBiomeSpawnModifier(context, SpawnCandidate.FLUTTERFLY, Biomes.Tags.SPAWNS_FLUTTERFLY);
        registerBiomeSpawnModifier(context, SpawnCandidate.FROLICKER, Biomes.Tags.SPAWNS_FROLICKER);
    }

    static <F extends Feature<?>> Supplier<F> register(String name, Supplier<F> feature)
    {
        return REGISTER.register(name, feature);
    }

    static <F extends Feature<?>> Supplier<F> registerRubble(String name, Supplier<F> feature)
    {
        return register(name + "_rubble", feature);
    }

    static <FC extends FeatureConfiguration, F extends Feature<FC>> void registerConfiguredFeature(BootstapContext<ConfiguredFeature<?, ?>> context, FeatureCandidate key, F feature, FC configuration)
    {
        context.register(key.getConfiguredFeatureKey(),
                new ConfiguredFeature<>(feature, configuration)
        );
    }

    static void registerPlacedFeature(BootstapContext<PlacedFeature> context, FeatureCandidate key, List<PlacementModifier> modifiers)
    {
        var featureSearch = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(key.getPlacedFeatureKey(),
                new PlacedFeature(
                        featureSearch.getOrThrow(key.getConfiguredFeatureKey()),
                        List.copyOf(modifiers)
                )
        );
    }

    static <FC extends FeatureConfiguration, F extends Feature<FC>> void registerConfiguredFeature(BootstapContext<ConfiguredFeature<?, ?>> context, FeatureCandidate key, Supplier<F> feature, Supplier<Block> block)
    {
        context.register(key.getConfiguredFeatureKey(),
                new ConfiguredFeature<>(feature.get(),
                        Sneaky.cast(
                                new BlockStateConfiguration(block.get().defaultBlockState())
                        )
                )
        );
    }

    static void registerBiomeModifier(BootstapContext<BiomeModifier> context, FeatureCandidate candidate)
    {
        var biomeSearch = context.lookup(Registries.BIOME);
        var featureSearch = context.lookup(Registries.PLACED_FEATURE);

        context.register(candidate.getBiomeModifierKey(),
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(
                                biomeSearch.getOrThrow(Biomes.DELUSION),
                                biomeSearch.getOrThrow(Biomes.ILLUSION),
                                biomeSearch.getOrThrow(Biomes.IMMATERIAL),
                                biomeSearch.getOrThrow(Biomes.SPECTRAL),
                                biomeSearch.getOrThrow(Biomes.SURREAL)
                        ),
                        HolderSet.direct(featureSearch.getOrThrow(candidate.getPlacedFeatureKey())),
                        candidate.getStep()
                )
        );
    }

    static void registerBiomeSpawnModifier(BootstapContext<BiomeModifier> context, SpawnCandidate candidate, TagKey<Biome> biomeKey)
    {
        var biomeSearch = context.lookup(Registries.BIOME);

        context.register(candidate.getModifierKey(),
                new BiomeModifiers.AddSpawnsBiomeModifier(
                        biomeSearch.getOrThrow(biomeKey),
                        List.of(candidate.getSpawnerData())
                )
        );
    }

    static List<PlacementModifier> surfacePlacement(int chance)
    {
        return List.of(
                RarityFilter.onAverageOnceEvery(chance),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        );
    }

    static List<PlacementModifier> treePlacement(Supplier<Block> sapling, int count)
    {
        return VegetationPlacements.treePlacement(
                RarityFilter.onAverageOnceEvery(count),
                sapling.get()
        );
    }

    static <T extends Block> RandomPatchConfiguration randomPatch(Supplier<T> block, int tries)
    {
        return FeatureUtils.simpleRandomPatchConfiguration(tries,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                BlockStateProvider.simple(block.get())
                        )
                )
        );
    }

    static <T extends Block> TreeConfiguration delusiveTree(Supplier<T> stem, Supplier<T> foliage, Supplier<T> dirt)
    {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(stem.get()),
                new IllusiveTrunkPlacer(8, 1, 2,
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(1), 1)
                                .add(ConstantInt.of(2), 1)
                                .add(ConstantInt.of(3), 1)
                                .build()
                        ),
                        UniformInt.of(2, 4),
                        UniformInt.of(-4, -3),
                        UniformInt.of(-1, 0)
                ),
                BlockStateProvider.simple(foliage.get()),
                new IllusiveFoliagePlacer(
                        ConstantInt.of(4),
                        ConstantInt.of(0),
                        ConstantInt.of(5),
                        0.25F,
                        0.5F,
                        0.16666667F,
                        0.33333334F
                ),
                new TwoLayersFeatureSize(1, 0, 2)
        ).dirt(BlockStateProvider.simple(dirt.get())).ignoreVines().build();
    }

    static <T extends Block> TreeConfiguration illusiveTree(Supplier<T> stem, Supplier<T> foliage, Supplier<T> dirt)
    {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(stem.get()),
                new ForkingTrunkPlacer(5, 2, 2),
                BlockStateProvider.simple(foliage.get()),
                new AcaciaFoliagePlacer(
                        ConstantInt.of(2),
                        ConstantInt.of(0)
                ),
                new TwoLayersFeatureSize(1, 0, 2)
        ).dirt(BlockStateProvider.simple(dirt.get())).ignoreVines().build();
    }

    static List<PlacementModifier> comaSpike(int chance, int minHeight, int maxHeight)
    {
        return List.of(
                RarityFilter.onAverageOnceEvery(chance),
                PlacementUtils.countExtra(1, 0.25F, 1),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(
                        VerticalAnchor.absolute(minHeight),
                        VerticalAnchor.absolute(maxHeight)
                ),
                BiomeFilter.biome()
        );
    }
}
