package snaker.tq.level.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import snaker.tq.rego.Rego;
import snaker.tq.utility.TourniquetedStuff;

import java.util.List;

/**
 * Created by SnakerBone on 3/08/2023
 **/
public class CatnipFeature
{
    public static final ResourceKey<PlacedFeature> PLACEMENT = TourniquetedStuff.placedKey("catnip");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CFG = TourniquetedStuff.configKey("catnip");

    public static void placement(BootstapContext<PlacedFeature> context)
    {
        var feature = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, PLACEMENT, feature.getOrThrow(CFG), TourniquetedStuff.STANDARD_PLACEMENTS);
    }

    public static void config(BootstapContext<ConfiguredFeature<?, ?>> context)
    {
        register(context, CFG, Feature.FLOWER, TourniquetedStuff.whenEmpty(Rego.BLOCK_CATNIP));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers)
    {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration)
    {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers)
    {
        register(context, key, configuration, List.of(modifiers));
    }

    public static class Modifier
    {
        public static final ResourceKey<BiomeModifier> MODIFIER = TourniquetedStuff.modifierKey("catnip");

        public static void modifier(BootstapContext<BiomeModifier> context)
        {
            var biome = context.lookup(Registries.BIOME);
            var feature = context.lookup(Registries.PLACED_FEATURE);
            register(context, biome.getOrThrow(Rego.Tags.COMATOSE_VEGETAL), feature.getOrThrow(PLACEMENT), GenerationStep.Decoration.VEGETAL_DECORATION);
        }

        private static void register(BootstapContext<BiomeModifier> context, HolderSet<Biome> biomes, Holder<PlacedFeature> features, GenerationStep.Decoration step)
        {
            context.register(MODIFIER, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biomes, HolderSet.direct(features), step));
        }
    }
}
