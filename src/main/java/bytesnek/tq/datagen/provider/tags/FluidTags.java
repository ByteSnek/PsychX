package bytesnek.tq.datagen.provider.tags;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import bytesnek.tq.Tourniqueted;
import bytesnek.tq.rego.Fluids;

import static net.minecraft.tags.FluidTags.LAVA;

/**
 * Created by SnakerBone on 24/09/2023
 **/
public class FluidTags extends FluidTagsProvider
{
    public FluidTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper)
    {
        super(output, provider, Tourniqueted.MODID, helper);
    }

    @Override
    public void addTags(@NotNull HolderLookup.Provider provider)
    {
        tag(LAVA).add(Fluids.COMASOTE.get()).add(Fluids.FLOWING_COMASOTE.get());
    }
}
