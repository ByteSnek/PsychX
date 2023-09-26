package xyz.snaker.tq.datagen.provider;

import xyz.snaker.snakerlib.client.Icon;
import xyz.snaker.snakerlib.utility.tools.CollectionStuff;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.item.CosmoSpine;
import xyz.snaker.tq.rego.Blocks;
import xyz.snaker.tq.rego.Items;
import xyz.snaker.tq.utility.tools.ItemModelProviderTools;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public class ItemModels extends ItemModelProvider implements ItemModelProviderTools<ItemModels>
{
    public ItemModels(PackOutput output, ExistingFileHelper helper)
    {
        super(output, Tourniqueted.MODID, helper);
    }

    @Override
    public void registerModels()
    {
        CollectionStuff.mapDeferredRegistries(Items.REGISTRAR, Item[]::new).forEach(item -> {
            if (!(item instanceof BlockItem)) {
                if (item instanceof CosmoSpine cosmoSpine) {
                    cosmoSpine(cosmoSpine);
                } else if (item instanceof ForgeSpawnEggItem spawnEggItem) {
                    spawnEgg(spawnEggItem);
                } else if (item instanceof Icon) {
                    perspective(item);
                } else {
                    item(item);
                }
            }
        });

        CollectionStuff.mapDeferredRegistries(Blocks.REGISTRAR, Block[]::new).forEach(block -> {
            if (block == Blocks.COMASOTE.get()) {
                return;
            }
            if (block instanceof BushBlock bushBlock) {
                blockCustom(bushBlock);
            } else {
                blockItem(block);
            }
        });
    }

    @Override
    public ItemModels getInstance()
    {
        return this;
    }
}
