package xyz.snaker.tq.level.world.feature;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;

import static xyz.snaker.tq.utility.WorldGenStuff.*;

/**
 * Created by SnakerBone on 27/08/2023
 **/
public enum FeatureKey
{
    CATNIP,
    SPLITLEAF,
    SNAKEROOT,
    TALL_SNAKEROOT,
    PINKTAILS,
    SWIRL_RUBBLE,
    FLARE_RUBBLE,
    WATERCOLOUR_RUBBLE,
    BURNING_RUBBLE,
    GEOMETRIC_RUBBLE,
    MULTICOLOUR_RUBBLE,
    SNOWFLAKE_RUBBLE,
    STARRY_RUBBLE,
    FOGGY_RUBBLE,
    ILLUSIVE_TREE,
    DELUSIVE_TREE,
    NO_LAVA;

    private final String name;
    private final ResourceKey<PlacedFeature> placedKey;
    private final ResourceKey<ConfiguredFeature<?, ?>> configKey;
    private final ResourceKey<BiomeModifier> modifierKey;

    FeatureKey()
    {
        this.name = name().toLowerCase();
        this.placedKey = createPlacedKey(name);
        this.configKey = createConfigKey(name);
        this.modifierKey = createModifierKey(name);
    }

    public ResourceKey<PlacedFeature> getPlacedKey()
    {
        return placedKey;
    }

    public ResourceKey<ConfiguredFeature<?, ?>> getConfigKey()
    {
        return configKey;
    }

    public ResourceKey<BiomeModifier> getModifierKey()
    {
        return modifierKey;
    }
}
