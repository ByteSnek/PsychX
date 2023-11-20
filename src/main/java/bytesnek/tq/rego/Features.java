package bytesnek.tq.rego;

import java.util.List;
import java.util.function.Supplier;

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
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import com.google.common.collect.ImmutableList;

import bytesnek.hiss.sneaky.Sneaky;
import bytesnek.tq.Tourniqueted;
import bytesnek.tq.level.world.candidate.FeatureCandidate;
import bytesnek.tq.level.world.candidate.SpawnCandidate;
import bytesnek.tq.level.world.feature.*;
import bytesnek.tq.level.world.feature.configuration.UtterflyShowgroundConfiguration;
import bytesnek.tq.level.world.tree.IllusiveFoliagePlacer;
import bytesnek.tq.level.world.tree.IllusiveTrunkPlacer;

import static bytesnek.tq.rego.Biomes.Tags.SPAWNS_FLUTTERFLY;
import static bytesnek.tq.rego.Biomes.Tags.SPAWNS_FROLICKER;
import static bytesnek.tq.rego.Blocks.*;
import static net.minecraft.world.level.levelgen.feature.Feature.RANDOM_PATCH;
import static net.minecraft.world.level.levelgen.feature.Feature.TREE;

/**
 * Created by SnakerBone on 25/08/2023
 **/
public class Features
{
    public static final DeferredRegister<Feature<?>> REGISTER = DeferredRegister.create(Registries.FEATURE, Tourniqueted.MODID);

    public static final RegistryObject<SwirlRubbleFeature> SWIRL_RUBBLE = registerRubble("swirl", () -> new SwirlRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<FlareRubbleFeature> FLARE_RUBBLE = registerRubble("flare", () -> new FlareRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<WaterColourRubbleFeature> WATERCOLOUR_RUBBLE = registerRubble("watercolour", () -> new WaterColourRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<BurningRubbleFeature> BURNING_RUBBLE = registerRubble("burning", () -> new BurningRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<GeometricRubbleFeature> GEOMETRIC_RUBBLE = registerRubble("geometric", () -> new GeometricRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<MultiColourRubbleFeature> MULTICOLOUR_RUBBLE = registerRubble("multicolour", () -> new MultiColourRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<SnowflakeRubbleFeature> SNOWFLAKE_RUBBLE = registerRubble("snowflake", () -> new SnowflakeRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<StarryRubbleFeature> STARRY_RUBBLE = registerRubble("starry", () -> new StarryRubbleFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<FoggyRubbleFeature> FOGGY_RUBBLE = registerRubble("foggy", () -> new FoggyRubbleFeature(BlockStateConfiguration.CODEC));

    public static final RegistryObject<UtterflyShowgroundFeature> UTTERFLY_SHOWGROUND = register("utterfly_showground", () -> new UtterflyShowgroundFeature(UtterflyShowgroundConfiguration.CODEC));

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
        registerPlacedFeature(context, FeatureCandidate.ILLUSIVE_TREE, treePlacement(ILLUSIVE_SAPLING, 2));
        registerPlacedFeature(context, FeatureCandidate.DELUSIVE_TREE, treePlacement(DELUSIVE_SAPLING, 2));
        registerPlacedFeature(context, FeatureCandidate.UTTERFLY_SHOWGROUND, List.of(BiomeFilter.biome()));
    }

    public static void configuredFeatures(BootstapContext<ConfiguredFeature<?, ?>> context)
    {
        registerConfiguredFeature(context, FeatureCandidate.CATNIP, RANDOM_PATCH, randomPatch(PINKTAILS, 4));
        registerConfiguredFeature(context, FeatureCandidate.SPLITLEAF, RANDOM_PATCH, randomPatch(SPLITLEAF, 4));
        registerConfiguredFeature(context, FeatureCandidate.SNAKEROOT, RANDOM_PATCH, randomPatch(SNAKEROOT, 32));
        registerConfiguredFeature(context, FeatureCandidate.TALL_SNAKEROOT, RANDOM_PATCH, randomPatch(TALL_SNAKEROOT, 32));
        registerConfiguredFeature(context, FeatureCandidate.PINKTAILS, RANDOM_PATCH, randomPatch(PINKTAILS, 32));
        registerConfiguredFeature(context, FeatureCandidate.SWIRL_RUBBLE, SWIRL_RUBBLE, SWIRL);
        registerConfiguredFeature(context, FeatureCandidate.FLARE_RUBBLE, FLARE_RUBBLE, FLARE);
        registerConfiguredFeature(context, FeatureCandidate.WATERCOLOUR_RUBBLE, WATERCOLOUR_RUBBLE, WATERCOLOUR);
        registerConfiguredFeature(context, FeatureCandidate.BURNING_RUBBLE, BURNING_RUBBLE, BURNING);
        registerConfiguredFeature(context, FeatureCandidate.GEOMETRIC_RUBBLE, GEOMETRIC_RUBBLE, GEOMETRIC);
        registerConfiguredFeature(context, FeatureCandidate.MULTICOLOUR_RUBBLE, MULTICOLOUR_RUBBLE, MULTICOLOUR);
        registerConfiguredFeature(context, FeatureCandidate.SNOWFLAKE_RUBBLE, SNOWFLAKE_RUBBLE, SNOWFLAKE);
        registerConfiguredFeature(context, FeatureCandidate.STARRY_RUBBLE, STARRY_RUBBLE, STARRY);
        registerConfiguredFeature(context, FeatureCandidate.FOGGY_RUBBLE, FOGGY_RUBBLE, FOGGY);
        registerConfiguredFeature(context, FeatureCandidate.ILLUSIVE_TREE, TREE, illusiveTree(ILLUSIVE_LOG, ILLUSIVE_LEAVES, COMASTONE));
        registerConfiguredFeature(context, FeatureCandidate.DELUSIVE_TREE, TREE, delusiveTree(DELUSIVE_LOG, DELUSIVE_LEAVES, COMASTONE));
        registerConfiguredFeature(context, FeatureCandidate.UTTERFLY_SHOWGROUND, UTTERFLY_SHOWGROUND.get(), new UtterflyShowgroundConfiguration(false, ImmutableList.of(), null));
    }

    public static void biomeModifiers(BootstapContext<BiomeModifier> context)
    {
        registerBiomeModifier(context, FeatureCandidate.CATNIP);
        registerBiomeModifier(context, FeatureCandidate.SPLITLEAF);
        registerBiomeModifier(context, FeatureCandidate.SNAKEROOT);
        registerBiomeModifier(context, FeatureCandidate.TALL_SNAKEROOT);
        registerBiomeModifier(context, FeatureCandidate.PINKTAILS);
        registerBiomeModifier(context, FeatureCandidate.SWIRL_RUBBLE);
        registerBiomeModifier(context, FeatureCandidate.FLARE_RUBBLE);
        registerBiomeModifier(context, FeatureCandidate.WATERCOLOUR_RUBBLE);
        registerBiomeModifier(context, FeatureCandidate.BURNING_RUBBLE);
        registerBiomeModifier(context, FeatureCandidate.GEOMETRIC_RUBBLE);
        registerBiomeModifier(context, FeatureCandidate.MULTICOLOUR_RUBBLE);
        registerBiomeModifier(context, FeatureCandidate.SNOWFLAKE_RUBBLE);
        registerBiomeModifier(context, FeatureCandidate.STARRY_RUBBLE);
        registerBiomeModifier(context, FeatureCandidate.FOGGY_RUBBLE);
        registerBiomeModifier(context, FeatureCandidate.ILLUSIVE_TREE);
        registerBiomeModifier(context, FeatureCandidate.DELUSIVE_TREE);
        registerBiomeModifier(context, FeatureCandidate.UTTERFLY_SHOWGROUND);
        registerBiomeSpawnModifier(context, SpawnCandidate.FLUTTERFLY, SPAWNS_FLUTTERFLY);
        registerBiomeSpawnModifier(context, SpawnCandidate.FROLICKER, SPAWNS_FROLICKER);
    }

    static <F extends Feature<?>> RegistryObject<F> register(String name, Supplier<F> feature)
    {
        return REGISTER.register(name, feature);
    }

    static <F extends Feature<?>> RegistryObject<F> registerRubble(String name, Supplier<F> feature)
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

    static <FC extends FeatureConfiguration, F extends Feature<FC>> void registerConfiguredFeature(BootstapContext<ConfiguredFeature<?, ?>> context, FeatureCandidate key, RegistryObject<F> feature, RegistryObject<Block> block)
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
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
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
                new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
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

    static List<PlacementModifier> treePlacement(RegistryObject<Block> sapling, int count)
    {
        return VegetationPlacements.treePlacement(
                RarityFilter.onAverageOnceEvery(count),
                sapling.get()
        );
    }

    static <T extends Block> RandomPatchConfiguration randomPatch(RegistryObject<T> block, int tries)
    {
        return FeatureUtils.simpleRandomPatchConfiguration(tries,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                BlockStateProvider.simple(block.get())
                        )
                )
        );
    }

    static <T extends Block> TreeConfiguration delusiveTree(RegistryObject<T> stem, RegistryObject<T> foliage, RegistryObject<T> dirt)
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

    static <T extends Block> TreeConfiguration illusiveTree(RegistryObject<T> stem, RegistryObject<T> foliage, RegistryObject<T> dirt)
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
}
