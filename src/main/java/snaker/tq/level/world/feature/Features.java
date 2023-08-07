package snaker.tq.level.world.feature;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import snaker.tq.rego.Rego;
import snaker.tq.utility.TourniquetedStuff;

/**
 * Created by SnakerBone on 3/08/2023
 **/
public class Features
{
    public static void placements(BootstapContext<PlacedFeature> context)
    {
        TourniquetedStuff.registerPlacement(context, PlacementKey.CATNIP, ConfigKey.CATNIP, TourniquetedStuff.surface(2));
        TourniquetedStuff.registerPlacement(context, PlacementKey.SHRUB, ConfigKey.SHRUB, TourniquetedStuff.surface(2));
    }

    public static void configs(BootstapContext<ConfiguredFeature<?, ?>> context)
    {
        TourniquetedStuff.registerConfiguredFeature(context, ConfigKey.CATNIP, Feature.RANDOM_PATCH, TourniquetedStuff.grass(Rego.BLOCK_CATNIP));
        TourniquetedStuff.registerConfiguredFeature(context, ConfigKey.SHRUB, Feature.RANDOM_PATCH, TourniquetedStuff.grass(Rego.BLOCK_SHRUB));
    }

    public static void modifiers(BootstapContext<BiomeModifier> context)
    {
        TourniquetedStuff.registerBiomeModifier(context, BiomeModifierKey.CATNIP, Rego.Tags.COMATOSE_VEGETAL, PlacementKey.CATNIP, GenerationStep.Decoration.VEGETAL_DECORATION);
        TourniquetedStuff.registerBiomeModifier(context, BiomeModifierKey.SHRUB, Rego.Tags.COMATOSE_VEGETAL, PlacementKey.SHRUB, GenerationStep.Decoration.VEGETAL_DECORATION);
    }

    public enum PlacementKey
    {
        CATNIP,
        SHRUB;

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
        CATNIP,
        SHRUB;

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
        CATNIP,
        SHRUB;

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
