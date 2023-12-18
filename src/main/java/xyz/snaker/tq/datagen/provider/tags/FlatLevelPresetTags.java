package xyz.snaker.tq.datagen.provider.tags;

import java.util.concurrent.CompletableFuture;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.rego.FlatLevelPresets;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FlatLevelGeneratorPresetTagsProvider;
import net.minecraft.tags.FlatLevelGeneratorPresetTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by SnakerBone on 18/10/2023
 **/
public class FlatLevelPresetTags extends FlatLevelGeneratorPresetTagsProvider
{
    public FlatLevelPresetTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper)
    {
        super(output, provider, Tourniqueted.MODID, helper);
    }

    @Override
    public void addTags(@NotNull HolderLookup.Provider provider)
    {
        tag(FlatLevelGeneratorPresetTags.VISIBLE)
                .addOptional(FlatLevelPresets.IRON.location())
                .addOptional(FlatLevelPresets.CONCRETE.location())
                .addOptional(FlatLevelPresets.OBSIDIAN.location())
                .addOptional(FlatLevelPresets.CRYING_OBSIDIAN.location());
    }
}
