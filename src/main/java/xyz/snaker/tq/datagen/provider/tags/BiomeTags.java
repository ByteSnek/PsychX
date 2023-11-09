package xyz.snaker.tq.datagen.provider.tags;

import java.util.concurrent.CompletableFuture;

import xyz.snaker.tq.Tourniqueted;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static xyz.snaker.tq.rego.Biomes.Tags.SPAWNS_FLUTTERFLY;
import static xyz.snaker.tq.rego.Biomes.Tags.SPAWNS_FROLICKER;

/**
 * Created by SnakerBone on 9/11/2023
 **/
public class BiomeTags extends BiomeTagsProvider
{
    public BiomeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper)
    {
        super(output, provider, Tourniqueted.MODID, helper);
    }

    @Override
    public void addTags(HolderLookup.@NotNull Provider provider)
    {
        tag(SPAWNS_FLUTTERFLY).add(Biomes.BAMBOO_JUNGLE);
        tag(SPAWNS_FROLICKER).add(Biomes.FLOWER_FOREST, Biomes.SUNFLOWER_PLAINS);
    }
}
