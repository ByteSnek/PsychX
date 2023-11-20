package bytesnek.tq.datagen.provider.tags;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FlatLevelGeneratorPresetTagsProvider;
import net.minecraft.tags.FlatLevelGeneratorPresetTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import bytesnek.tq.Tourniqueted;
import bytesnek.tq.rego.FlatLevelPresets;

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
                .addOptional(FlatLevelPresets.CONCRETE.location());
    }
}
