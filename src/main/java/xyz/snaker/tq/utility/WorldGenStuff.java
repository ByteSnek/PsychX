package xyz.snaker.tq.utility;

import java.util.List;

import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.snakerlib.utility.tools.UnsafeStuff;
import xyz.snaker.tq.level.world.feature.FeatureKey;
import xyz.snaker.tq.level.world.manager.BiomeManager;
import xyz.snaker.tq.level.world.tree.GeometricTrunkPlacer;
import xyz.snaker.tq.rego.Sounds;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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

    public static List<PlacementModifier> simpleTreePlacement(RegistryObject<Block> sapling, int initialCount, float bonusRollChance, int bonusCount)
    {
        return VegetationPlacements.treePlacement(
                countExtra(initialCount, bonusRollChance, bonusCount),
                sapling.get()
        );
    }

    public static List<PlacementModifier> simpleSurfacePlacement(int count)
    {
        return List.of(
                placement(count),
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

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> void registerConfiguredFeature(BootstapContext<ConfiguredFeature<?, ?>> context, String name, RegistryObject<F> feature, FC configuration)
    {
        FeatureKey key = safeGetKey(name);
        context.register(key.getConfigKey(), new ConfiguredFeature<>(feature.get(), configuration));
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
                                biomeSearch.getOrThrow(BiomeManager.DELUSION),
                                biomeSearch.getOrThrow(BiomeManager.ILLUSION),
                                biomeSearch.getOrThrow(BiomeManager.IMMATERIAL),
                                biomeSearch.getOrThrow(BiomeManager.SPECTRAL),
                                biomeSearch.getOrThrow(BiomeManager.SURREAL)),
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

    static PlacementModifier countExtra(int initialCount, float bonusRollChance, int bonusCount)
    {
        return PlacementUtils.countExtra(initialCount, bonusRollChance, bonusCount);
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
