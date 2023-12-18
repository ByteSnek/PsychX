package xyz.snaker.tq.datagen.provider;

import xyz.snaker.hiss.utility.Annotations;
import xyz.snaker.snakerlib.resources.ResourceLocations;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.item.ComaCrystalSword;
import xyz.snaker.tq.level.item.CosmoSpine;
import xyz.snaker.tq.rego.Blocks;
import xyz.snaker.tq.rego.Items;
import xyz.snaker.tq.utility.NoTexture;
import xyz.snaker.tq.utility.RegistryMapper;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public class ItemModels extends ItemModelProvider implements RegistryMapper
{
    public ItemModels(PackOutput output, ExistingFileHelper helper)
    {
        super(output, Tourniqueted.MODID, helper);
    }

    @Override
    public void registerModels()
    {
        map(Items.REGISTER, Item[]::new).forEach(item ->
        {
            if (!(item instanceof BlockItem)) {
                if (item instanceof ComaCrystalSword) {
                    addPerspectiveItem(item);
                    return;
                }

                if (item instanceof CosmoSpine cosmoSpine) {
                    addCosmoSpineItem(cosmoSpine);
                    return;
                }

                if (item instanceof DeferredSpawnEggItem spawnEggItem) {
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

        map(Blocks.REGISTER, Block[]::new).forEach(block ->
        {
            if (block instanceof BushBlock bushBlock) {
                addAdvancedBlockItem(bushBlock);
                return;
            }

            addSimpleBlockItem(block);
        });
    }

    private void addCosmoSpineItem(Item item)
    {
        withExistingParent(ResourceLocations.ITEM.getPath(item), modLoc("cosmo_spine"));
    }

    private void addSpawnEggItem(Item item)
    {
        withExistingParent(ResourceLocations.ITEM.getPath(item), modLoc("egg"));
    }

    private void addPerspectiveItem(Item item)
    {
        withExistingParent(ResourceLocations.ITEM.getPath(item), modLoc("perspective"));
    }

    private <I extends ItemLike> void addSimpleItem(I item)
    {
        withExistingParent(ResourceLocations.ITEM.getPath((Item) item), mcLoc("item/generated")).texture("layer0", modLoc("item/" + ResourceLocations.ITEM.getPath((Item) item)));
    }

    private void addSimpleBlockItem(Block block)
    {
        withExistingParent(ResourceLocations.BLOCK.getPath(block), modLoc("block/" + ResourceLocations.BLOCK.getPath(block)));
    }

    private <B extends ItemLike> void addAdvancedBlockItem(B block)
    {
        withExistingParent(ResourceLocations.BLOCK.getPath((Block) block), mcLoc("item/generated")).texture("layer0", modLoc("block/" + ResourceLocations.BLOCK.getPath((Block) block)));
    }
}
