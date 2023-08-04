package snaker.tq.utility;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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
}
