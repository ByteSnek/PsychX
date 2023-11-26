package bytesnek.tq.datagen.provider.tags;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import bytesnek.tq.Tourniqueted;

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
        tag(bytesnek.tq.rego.Biomes.Tags.SPAWNS_FLUTTERFLY).add(Biomes.BAMBOO_JUNGLE);
        tag(bytesnek.tq.rego.Biomes.Tags.SPAWNS_FROLICKER).add(Biomes.FLOWER_FOREST, Biomes.SUNFLOWER_PLAINS);
    }
}
