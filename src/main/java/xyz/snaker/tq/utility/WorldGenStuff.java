package xyz.snaker.tq.utility;

import java.util.List;

import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.snakerlib.utility.tools.UnsafeStuff;
import xyz.snaker.snakerlib.utility.tools.WorldStuff;
import xyz.snaker.tq.level.world.feature.FeatureKey;
import xyz.snaker.tq.level.world.tree.GeometricTrunkPlacer;
import xyz.snaker.tq.rego.Biomes;
import xyz.snaker.tq.rego.Entities;
import xyz.snaker.tq.rego.Keys;
import xyz.snaker.tq.rego.Sounds;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraft.world.level.block.Blocks.AIR;

/**
 * Created by SnakerBone on 4/08/2023
 **/
public class WorldGenStuff
{
    public static final Holder<SoundEvent> RANDOM_SOUND_FX = Holder.direct(Sounds.RANDOM_FX.get());
    public static final float PARTICLE_SPAWN_CHANCE = 0.001F;

    public static <T extends Block> RandomPatchConfiguration simpleRandomConfig(RegistryObject<T> block, int tries)
    {
        return FeatureUtils.simpleRandomPatchConfiguration(tries, onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(block.get()))));
    }

    public static <T extends Block> RandomPatchConfiguration simpleRandomConfig(RegistryObject<T> block)
    {
        return simpleRandomConfig(block, 32);
    }

    public static <T extends Block> TreeConfiguration createGeometricTreeConfig(RegistryObject<T> stem, RegistryObject<T> foliage, RegistryObject<T> dirt, int baseHeight, int heightA, int heightB, int foliageRadius, int foliageOffset, int foliageHeight, int sizeLimit, int sizeLowerBound, int sizeUpperBound)
    {
        return new TreeConfiguration.TreeConfigurationBuilder(
                simple(stem.get()),
                new GeometricTrunkPlacer(baseHeight, heightA, heightB),
                simple(foliage.get()),
                new BlobFoliagePlacer(constant(foliageRadius), constant(foliageOffset), foliageHeight),
                new TwoLayersFeatureSize(sizeLimit, sizeLowerBound, sizeUpperBound)
        ).dirt(simple(dirt.get())).ignoreVines().build();
    }

    public static <T extends Block> TreeConfiguration createGeometricTreeConfig(RegistryObject<T> stem, RegistryObject<T> foliage, RegistryObject<T> dirt)
    {
        return createGeometricTreeConfig(stem, foliage, dirt, 5, 4, 3, 3, 2, 3, 1, 0, 2);
    }

    public static <T extends Block> TreeConfiguration createFoggyTreeConfig(RegistryObject<T> stem, RegistryObject<T> dirt, int foliageRadius, int foliageOffset, int foliageHeight)
    {
        return new TreeConfiguration.TreeConfigurationBuilder(
                simple(stem.get()),
                new CherryTrunkPlacer(7, 1, 0,
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(1), 1)
                                .add(ConstantInt.of(2), 1)
                                .add(ConstantInt.of(3), 1)
                                .build()
                        ),
                        UniformInt.of(2, 4),
                        UniformInt.of(-4, -3),
                        UniformInt.of(-1, 0)),
                simple(AIR),
                new CherryFoliagePlacer(
                        ConstantInt.of(foliageRadius),
                        ConstantInt.of(foliageOffset),
                        ConstantInt.of(foliageHeight),
                        0.25F,
                        0.5F,
                        0.16666667F,
                        0.33333334F),
                new TwoLayersFeatureSize(1, 0, 2)
        ).dirt(simple(dirt.get())).ignoreVines().build();
    }

    public static <T extends Block> TreeConfiguration createFoggyTreeConfig(RegistryObject<T> stem, RegistryObject<T> dirt)
    {
        return createFoggyTreeConfig(stem, dirt, 4, 0, 5);
    }

    public static <T extends Block> TreeConfiguration createAcaciaTreeConfig(RegistryObject<T> stem, RegistryObject<T> foliage, RegistryObject<T> dirt, int baseHeight, int heightA, int heightB, int foliageRadius, int foliageOffset, int foliageLimit, int foliageLowerBound, int foliageUpperBound)
    {
        return new TreeConfiguration.TreeConfigurationBuilder(
                simple(stem.get()),
                new ForkingTrunkPlacer(baseHeight, heightA, heightB),
                simple(foliage.get()),
                new AcaciaFoliagePlacer(constant(foliageRadius), constant(foliageOffset)),
                new TwoLayersFeatureSize(foliageLimit, foliageLowerBound, foliageUpperBound)
        ).dirt(simple(dirt.get())).ignoreVines().build();
    }

    public static <T extends Block> TreeConfiguration createAcaciaTreeConfig(RegistryObject<T> stem, RegistryObject<T> foliage, RegistryObject<T> dirt)
    {
        return createAcaciaTreeConfig(stem, foliage, dirt, 5, 2, 2, 2, 0, 1, 0, 2);
    }

    public static List<PlacementModifier> simpleTreePlacement(RegistryObject<Block> sapling, int count)
    {
        return VegetationPlacements.treePlacement(
                RarityFilter.onAverageOnceEvery(count),
                sapling.get()
        );
    }

    public static List<PlacementModifier> simpleSurfacePlacement(int count)
    {
        return List.of(
                RarityFilter.onAverageOnceEvery(count),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        );
    }

    public static void registerPlacement(BootstapContext<PlacedFeature> context, String name, List<PlacementModifier> modifiers)
    {
        var featureSearch = context.lookup(Registries.CONFIGURED_FEATURE);
        FeatureKey key = safeGetKey(name);
        context.register(key.getPlacedKey(), new PlacedFeature(featureSearch.getOrThrow(key.getConfigKey()), List.copyOf(modifiers)));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> void registerConfiguredFeature(BootstapContext<ConfiguredFeature<?, ?>> context, String name, F feature, FC configuration)
    {
        FeatureKey key = safeGetKey(name);
        context.register(key.getConfigKey(), new ConfiguredFeature<>(feature, configuration));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> void registerConfiguredFeature(BootstapContext<ConfiguredFeature<?, ?>> context, String name, RegistryObject<F> feature, RegistryObject<Block> block)
    {
        FeatureKey key = safeGetKey(name);
        context.register(key.getConfigKey(), new ConfiguredFeature<>(feature.get(), UnsafeStuff.cast(new BlockStateConfiguration(block.get().defaultBlockState()))));
    }

    public static void registerBiomeModifier(BootstapContext<BiomeModifier> context, String name, GenerationStep.Decoration step)
    {
        var biomeSearch = context.lookup(Registries.BIOME);
        var featureSearch = context.lookup(Registries.PLACED_FEATURE);
        FeatureKey key = safeGetKey(name);
        context.register(key.getModifierKey(),
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(
                                biomeSearch.getOrThrow(Biomes.DELUSION),
                                biomeSearch.getOrThrow(Biomes.ILLUSION),
                                biomeSearch.getOrThrow(Biomes.IMMATERIAL),
                                biomeSearch.getOrThrow(Biomes.SPECTRAL),
                                biomeSearch.getOrThrow(Biomes.SURREAL)
                        ),
                        HolderSet.direct(featureSearch.getOrThrow(key.getPlacedKey())), step));
    }

    public static ResourceKey<PlacedFeature> createPlacedKey(String name)
    {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourcePath(name));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> createConfigKey(String name)
    {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourcePath(name));
    }

    public static ResourceKey<BiomeModifier> createModifierKey(String name)
    {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourcePath(name));
    }

    public static <T extends Entity> void addCreatureSpawn(MobSpawnSettings.Builder builder, RegistryObject<EntityType<T>> creature, int weight, int minCount, int maxCount)
    {
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(creature.get(), weight, minCount, maxCount));
    }

    public static <T extends Entity> void addMonsterSpawn(MobSpawnSettings.Builder builder, RegistryObject<EntityType<T>> creature, int weight, int minCount, int maxCount)
    {
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(creature.get(), weight, minCount, maxCount));
    }

    public static boolean checkComatoseSpawnRules(ServerLevelAccessor level, RandomSource random, int bound)
    {
        return WorldStuff.isDimension(level, Keys.COMATOSE) && WorldStuff.random(random, bound);
    }

    public static boolean checkComatoseSpawnRules(ServerLevelAccessor level, RandomSource random)
    {
        return checkComatoseSpawnRules(level, random, 75);
    }

    public static boolean checkOverworldSpawnRules(ServerLevelAccessor level, BlockPos pos, RandomSource random, int bound)
    {
        return WorldStuff.canSeeSky(level, pos) && WorldStuff.isDay(level) && WorldStuff.random(random, bound);
    }

    public static boolean checkOverworldSpawnRules(ServerLevelAccessor level, BlockPos pos, RandomSource random)
    {
        return checkOverworldSpawnRules(level, pos, random, 75);
    }

    public static void addGeometricTree(BiomeGenerationSettings.Builder builder)
    {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureKey.GEOMETRIC_TREE.getPlacedKey());
    }

    public static void addFoggyTree(BiomeGenerationSettings.Builder builder)
    {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureKey.FOGGY_TREE.getPlacedKey());
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
        WorldGenStuff.addCreatureSpawn(builder, Entities.FROLICKER, 15, 1, 1);
        WorldGenStuff.addMonsterSpawn(builder, Entities.SNIPE, 20, 1, 1);
    }

    static BlockStateProvider simple(Block block)
    {
        return BlockStateProvider.simple(block);
    }

    static IntProvider constant(int value)
    {
        return ConstantInt.of(value);
    }

    static CountPlacement placement(int value)
    {
        return CountPlacement.of(value);
    }

    static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<PlacedFeature> onlyWhenEmpty(F feature, FC config)
    {
        return PlacementUtils.onlyWhenEmpty(feature, config);
    }

    static FeatureKey safeGetKey(String name)
    {
        try {
            return FeatureKey.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(String.format("Config key '%s' does not exist", name));
        }
    }
}
