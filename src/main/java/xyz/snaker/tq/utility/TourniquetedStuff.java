package xyz.snaker.tq.utility;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.tq.level.world.feature.Features;

import java.util.List;

/**
 * Created by SnakerBone on 4/08/2023
 **/
public class TourniquetedStuff
{
    public static final PlacementModifier[] STANDARD_PLACEMENTS = {RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()};

    public static ResourceKey<PlacedFeature> placedKey(String name)
    {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourcePath(name));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> configKey(String name)
    {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourcePath(name));
    }

    public static ResourceKey<BiomeModifier> modifierKey(String name)
    {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourcePath(name));
    }

    public static <T extends Block> RandomPatchConfiguration whenEmpty(RegistryObject<T> block, int tries, int xz, int y)
    {
        return new RandomPatchConfiguration(tries, xz, y, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(block.get()))));
    }

    public static <T extends Block> RandomPatchConfiguration whenEmpty(RegistryObject<T> block)
    {
        return whenEmpty(block, 32, 10, 2);
    }

    public static <T extends Block> RandomPatchConfiguration grass(RegistryObject<T> block, int tries)
    {
        return FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(block.get()))));
    }

    public static <T extends Block> RandomPatchConfiguration grass(RegistryObject<T> block)
    {
        return grass(block, 32);
    }

    public static void registerPlacement(BootstapContext<PlacedFeature> context, Features.PlacementKey placement, Features.ConfigKey config, List<PlacementModifier> modifiers)
    {
        var featureSearch = context.lookup(Registries.CONFIGURED_FEATURE);
        context.register(placement.key(), new PlacedFeature(featureSearch.getOrThrow(config.key()), List.copyOf(modifiers)));
    }

    public static void registerPlacement(BootstapContext<PlacedFeature> context, Features.PlacementKey placement, Features.ConfigKey config, PlacementModifier... modifiers)
    {
        registerPlacement(context, placement, config, List.of(modifiers));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> void registerConfiguredFeature(BootstapContext<ConfiguredFeature<?, ?>> context, Features.ConfigKey config, F feature, FC configuration)
    {
        context.register(config.key(), new ConfiguredFeature<>(feature, configuration));
    }

    public static void registerBiomeModifier(BootstapContext<BiomeModifier> context, Features.BiomeModifierKey modifier, TagKey<Biome> biome, Features.PlacementKey placement, GenerationStep.Decoration step)
    {
        var biomeSearch = context.lookup(Registries.BIOME);
        var featureSearch = context.lookup(Registries.PLACED_FEATURE);
        context.register(modifier.key(), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biomeSearch.getOrThrow(biome), HolderSet.direct(featureSearch.getOrThrow(placement.key())), step));
    }

    public static List<PlacementModifier> surface(int count)
    {
        return List.of(CountPlacement.of(count), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    }
}
