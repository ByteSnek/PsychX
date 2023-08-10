package xyz.snaker.tq.level.world.feature;

import xyz.snaker.tq.rego.Rego;
import xyz.snaker.tq.utility.TourniquetedStuff;

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
        TourniquetedStuff.registerPlacement(context, PlacementKey.CATNIP, ConfigKey.CATNIP, TourniquetedStuff.surface(6));
        TourniquetedStuff.registerPlacement(context, PlacementKey.SPLITLEAF, ConfigKey.SPLITLEAF, TourniquetedStuff.surface(6));
        TourniquetedStuff.registerPlacement(context, PlacementKey.SNAKEROOT, ConfigKey.SNAKEROOT, TourniquetedStuff.surface(2));
        TourniquetedStuff.registerPlacement(context, PlacementKey.TALL_SNAKEROOT, ConfigKey.TALL_SNAKEROOT, TourniquetedStuff.surface(1));
    }

    public static void configs(BootstapContext<ConfiguredFeature<?, ?>> context)
    {
        TourniquetedStuff.registerConfiguredFeature(context, ConfigKey.CATNIP, Feature.RANDOM_PATCH, TourniquetedStuff.grass(Rego.BLOCK_CATNIP, 4));
        TourniquetedStuff.registerConfiguredFeature(context, ConfigKey.SPLITLEAF, Feature.RANDOM_PATCH, TourniquetedStuff.grass(Rego.BLOCK_SPLITLEAF, 4));
        TourniquetedStuff.registerConfiguredFeature(context, ConfigKey.SNAKEROOT, Feature.RANDOM_PATCH, TourniquetedStuff.grass(Rego.BLOCK_SNAKEROOT));
        TourniquetedStuff.registerConfiguredFeature(context, ConfigKey.TALL_SNAKEROOT, Feature.RANDOM_PATCH, TourniquetedStuff.grass(Rego.BLOCK_TALL_SNAKEROOT));
    }

    public static void modifiers(BootstapContext<BiomeModifier> context)
    {
        TourniquetedStuff.registerBiomeModifier(context, BiomeModifierKey.CATNIP, Rego.Tags.COMATOSE_VEGETAL, PlacementKey.CATNIP, GenerationStep.Decoration.VEGETAL_DECORATION);
        TourniquetedStuff.registerBiomeModifier(context, BiomeModifierKey.SPLITLEAF, Rego.Tags.COMATOSE_VEGETAL, PlacementKey.SPLITLEAF, GenerationStep.Decoration.VEGETAL_DECORATION);
        TourniquetedStuff.registerBiomeModifier(context, BiomeModifierKey.SNAKEROOT, Rego.Tags.COMATOSE_VEGETAL, PlacementKey.SNAKEROOT, GenerationStep.Decoration.VEGETAL_DECORATION);
        TourniquetedStuff.registerBiomeModifier(context, BiomeModifierKey.TALL_SNAKEROOT, Rego.Tags.COMATOSE_VEGETAL, PlacementKey.TALL_SNAKEROOT, GenerationStep.Decoration.VEGETAL_DECORATION);
    }

    public enum PlacementKey
    {
        CATNIP(),
        SPLITLEAF(),
        SNAKEROOT(),
        TALL_SNAKEROOT();

        private final ResourceKey<PlacedFeature> key;

        PlacementKey()
        {
            this.key = TourniquetedStuff.placedKey(name().toLowerCase());
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
        TALL_SNAKEROOT();

        private final ResourceKey<ConfiguredFeature<?, ?>> key;

        ConfigKey()
        {
            this.key = TourniquetedStuff.configKey(name().toLowerCase());
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
        TALL_SNAKEROOT();

        private final ResourceKey<BiomeModifier> key;

        BiomeModifierKey()
        {
            this.key = TourniquetedStuff.modifierKey(name().toLowerCase());
        }

        public ResourceKey<BiomeModifier> key()
        {
            return key;
        }
    }
}
