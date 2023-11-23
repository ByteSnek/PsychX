package bytesnek.tq.datagen.provider;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.data.ExistingFileHelper;

import bytesnek.hiss.utility.Annotations;
import bytesnek.snakerlib.resources.ResourceLocations;
import bytesnek.tq.Tourniqueted;
import bytesnek.tq.level.item.CosmoSpine;
import bytesnek.tq.rego.Blocks;
import bytesnek.tq.rego.Items;
import bytesnek.tq.utility.NoTexture;
import bytesnek.tq.utility.RegistryMapper;

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
