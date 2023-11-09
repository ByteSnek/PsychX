package xyz.snaker.tq.datagen.provider;

import xyz.snaker.snakerlib.resources.ResourceLocations;
import xyz.snaker.snakerlib.utility.Annotations;
import xyz.snaker.snakerlib.utility.Streams;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.item.CosmoSpine;
import xyz.snaker.tq.rego.Blocks;
import xyz.snaker.tq.rego.Items;
import xyz.snaker.tq.utility.NoTexture;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public class ItemModels extends ItemModelProvider
{
    public ItemModels(PackOutput output, ExistingFileHelper helper)
    {
        super(output, Tourniqueted.MODID, helper);
    }

    @Override
    public void registerModels()
    {
        Streams.mapDeferredRegistries(Items.REGISTER, Item[]::new).forEach(item ->
        {
            if (!(item instanceof BlockItem)) {
                if (item instanceof CosmoSpine cosmoSpine) {
                    addCosmoSpineItem(cosmoSpine);
                    return;
                }

                if (item instanceof ForgeSpawnEggItem spawnEggItem) {
                    addSpawnEggItem(spawnEggItem);
                    return;
                }

                if (Annotations.isPresent(item, NoTexture.class)) {
                    addPerspectiveItem(item);
                    return;
                }

                addSimpleItem(item);
            }
        });

        Streams.mapDeferredRegistries(Blocks.REGISTER, Block[]::new).forEach(block ->
        {
            if (block instanceof LiquidBlock) {
                return;
            }

            if (block instanceof BushBlock bushBlock) {
                addAdvancedBlockItem(bushBlock);
                return;
            }

            addSimpleBlockItem(block);
        });
    }

    private void addCosmoSpineItem(Item item)
    {
        withExistingParent(ResourceLocations.getPath(item), modLoc("cosmo_spine"));
    }

    private void addSpawnEggItem(Item item)
    {
        withExistingParent(ResourceLocations.getPath(item), modLoc("egg"));
    }

    private void addPerspectiveItem(Item item)
    {
        withExistingParent(ResourceLocations.getPath(item), modLoc("perspective"));
    }

    private <I extends ItemLike> void addSimpleItem(I item)
    {
        withExistingParent(ResourceLocations.getPath((Item) item), mcLoc("item/generated")).texture("layer0", modLoc("item/" + ResourceLocations.getPath((Item) item)));
    }

    private void addSimpleBlockItem(Block block)
    {
        withExistingParent(ResourceLocations.getPath(block), modLoc("block/" + ResourceLocations.getPath(block)));
    }

    private <B extends ItemLike> void addAdvancedBlockItem(B block)
    {
        withExistingParent(ResourceLocations.getPath((Block) block), mcLoc("item/generated")).texture("layer0", modLoc("block/" + ResourceLocations.getPath((Block) block)));
    }
}
