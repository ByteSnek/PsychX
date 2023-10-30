package xyz.snaker.tq.datagen.provider.tags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.rego.Items;
import xyz.snaker.tq.utility.tools.ItemTagsProviderTools;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 14/10/2023
 **/
public class ItemTags extends ItemTagsProvider implements ItemTagsProviderTools<ItemTags>
{
    public ItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagProvider, ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, blockTagProvider, Tourniqueted.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider)
    {
        tag(Items.Tags.COSMO_SPINES, List.of(Items.RED_COSMO_SPINE, Items.GREEN_COSMO_SPINE, Items.BLUE_COSMO_SPINE, Items.YELLOW_COSMO_SPINE, Items.PINK_COSMO_SPINE, Items.PURPLE_COSMO_SPINE, Items.ALPHA_COSMO_SPINE));
        tag(Items.Tags.DROPS, List.of(Items.RED_COSMO_SPINE, Items.GREEN_COSMO_SPINE, Items.BLUE_COSMO_SPINE, Items.YELLOW_COSMO_SPINE, Items.PINK_COSMO_SPINE, Items.PURPLE_COSMO_SPINE, Items.ALPHA_COSMO_SPINE, Items.FLUTTERFLY_KERATIN));
    }

    @Override
    public ItemTags getInstance()
    {
        return this;
    }
}
