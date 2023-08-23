package xyz.snaker.tq.level.world.feature;

import xyz.snaker.tq.rego.Blocks;
import xyz.snaker.tq.utility.TqWorldGen;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;

/**
 * Created by SnakerBone on 3/08/2023
 **/
public class Features
{
    public static void placements(BootstapContext<PlacedFeature> context)
    {
        TqWorldGen.registerPlacement(context, PlacementKey.CATNIP, ConfigKey.CATNIP, TqWorldGen.surface(6));
        TqWorldGen.registerPlacement(context, PlacementKey.SPLITLEAF, ConfigKey.SPLITLEAF, TqWorldGen.surface(6));
        TqWorldGen.registerPlacement(context, PlacementKey.SNAKEROOT, ConfigKey.SNAKEROOT, TqWorldGen.surface(2));
        TqWorldGen.registerPlacement(context, PlacementKey.TALL_SNAKEROOT, ConfigKey.TALL_SNAKEROOT, TqWorldGen.surface(1));
        TqWorldGen.registerPlacement(context, PlacementKey.GEOMETRIC, ConfigKey.GEOMETRIC, TqWorldGen.tree(Blocks.GEOMETRIC_SAPLING, 1, 0.1F, 1));
    }

    public static void configs(BootstapContext<ConfiguredFeature<?, ?>> context)
    {
        TqWorldGen.registerConfiguredFeature(context, ConfigKey.CATNIP, Feature.RANDOM_PATCH, TqWorldGen.grass(Blocks.PINKTAILS, 4));
        TqWorldGen.registerConfiguredFeature(context, ConfigKey.SPLITLEAF, Feature.RANDOM_PATCH, TqWorldGen.grass(Blocks.SPLITLEAF, 4));
        TqWorldGen.registerConfiguredFeature(context, ConfigKey.SNAKEROOT, Feature.RANDOM_PATCH, TqWorldGen.grass(Blocks.SNAKEROOT));
        TqWorldGen.registerConfiguredFeature(context, ConfigKey.TALL_SNAKEROOT, Feature.RANDOM_PATCH, TqWorldGen.grass(Blocks.TALL_SNAKEROOT));
        TqWorldGen.registerConfiguredFeature(context, ConfigKey.GEOMETRIC, Feature.TREE, TqWorldGen.SIMPLE_TREE.apply(Blocks.GEOMETRIC_LOG.get(), Blocks.GEOMETRIC.get(), Blocks.COMASTONE.get()));
    }

    public static void modifiers(BootstapContext<BiomeModifier> context)
    {
        TqWorldGen.registerBiomeModifier(context, BiomeModifierKey.CATNIP, PlacementKey.CATNIP, GenerationStep.Decoration.VEGETAL_DECORATION);
        TqWorldGen.registerBiomeModifier(context, BiomeModifierKey.SPLITLEAF, PlacementKey.SPLITLEAF, GenerationStep.Decoration.VEGETAL_DECORATION);
        TqWorldGen.registerBiomeModifier(context, BiomeModifierKey.SNAKEROOT, PlacementKey.SNAKEROOT, GenerationStep.Decoration.VEGETAL_DECORATION);
        TqWorldGen.registerBiomeModifier(context, BiomeModifierKey.TALL_SNAKEROOT, PlacementKey.TALL_SNAKEROOT, GenerationStep.Decoration.VEGETAL_DECORATION);
        TqWorldGen.registerBiomeModifier(context, BiomeModifierKey.GEOMETRIC, PlacementKey.GEOMETRIC, GenerationStep.Decoration.VEGETAL_DECORATION);
    }

    public enum PlacementKey
    {
        CATNIP(),
        SPLITLEAF(),
        SNAKEROOT(),
        TALL_SNAKEROOT(),
        GEOMETRIC();

        private final ResourceKey<PlacedFeature> key;

        PlacementKey()
        {
            this.key = TqWorldGen.placedKey(name().toLowerCase());
        }

        public ResourceKey<PlacedFeature> key()
        {
            return key;
        }
    }

    public enum ConfigKey
    {
        CATNIP(),
        SPLITLEAF(),
        SNAKEROOT(),
        TALL_SNAKEROOT(),
        GEOMETRIC();

        private final ResourceKey<ConfiguredFeature<?, ?>> key;

        ConfigKey()
        {
            this.key = TqWorldGen.configKey(name().toLowerCase());
        }

        public ResourceKey<ConfiguredFeature<?, ?>> key()
        {
            return key;
        }
    }

    public enum BiomeModifierKey
    {
        CATNIP(),
        SPLITLEAF(),
        SNAKEROOT(),
        TALL_SNAKEROOT(),
        GEOMETRIC();

        private final ResourceKey<BiomeModifier> key;

        BiomeModifierKey()
        {
            this.key = TqWorldGen.modifierKey(name().toLowerCase());
        }

        public ResourceKey<BiomeModifier> key()
        {
            return key;
        }
    }
}
