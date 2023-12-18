package xyz.snaker.tq.datagen.provider.tags;

import java.util.concurrent.CompletableFuture;

import xyz.snaker.tq.rego.Items;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import org.jetbrains.annotations.NotNull;

import xyz.snaker.tq.Tourniqueted;

/**
 * Created by SnakerBone on 14/10/2023
 **/
public class ItemTags extends ItemTagsProvider
{
    public ItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagProvider, ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, blockTagProvider, Tourniqueted.MODID, existingFileHelper);
    }

    @Override
    public void addTags(@NotNull HolderLookup.Provider provider)
    {
        tag(Items.Tags.COSMO_SPINES).add(Items.RED_COSMO_SPINE.get(), Items.GREEN_COSMO_SPINE.get(), Items.BLUE_COSMO_SPINE.get(), Items.YELLOW_COSMO_SPINE.get(), Items.PINK_COSMO_SPINE.get(), Items.PURPLE_COSMO_SPINE.get(), Items.ALPHA_COSMO_SPINE.get());
        tag(Items.Tags.DROPPABLE).add(Items.RED_COSMO_SPINE.get(), Items.GREEN_COSMO_SPINE.get(), Items.BLUE_COSMO_SPINE.get(), Items.YELLOW_COSMO_SPINE.get(), Items.PINK_COSMO_SPINE.get(), Items.PURPLE_COSMO_SPINE.get(), Items.ALPHA_COSMO_SPINE.get(), Items.FLUTTERFLY_KERATIN.get());
    }
}
