package xyz.snaker.tq.datagen.provider.tags;

import java.util.concurrent.CompletableFuture;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.rego.Fluids;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.tags.FluidTags.WATER;

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
    protected void addTags(@NotNull HolderLookup.Provider provider)
    {
        tag(WATER).add(Fluids.COMASOTE.get()).add(Fluids.FLOWING_COMASOTE.get());
    }
}
