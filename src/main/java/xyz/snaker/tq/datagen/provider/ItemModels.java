package xyz.snaker.tq.datagen.provider;

import xyz.snaker.snakerlib.client.Icon;
import xyz.snaker.tq.Tourniqueted;
import xyz.snaker.tq.level.item.CosmoSpine;
import xyz.snaker.tq.rego.Blocks;
import xyz.snaker.tq.rego.Items;
import xyz.snaker.tq.utility.tools.ItemModelProviderTools;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
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
        for (var obj : Items.REGISTRAR.getEntries()) {
            var current = obj.get();
            if (!(current instanceof BlockItem)) {
                if (current instanceof CosmoSpine) {
                    cosmoSpine(current);
                } else if (current instanceof ForgeSpawnEggItem) {
                    spawnEgg(current);
                } else if (current instanceof Icon) {
                    perspective(current);
                } else {
                    item(current);
                }
            }
        }
        for (var obj : Blocks.REGISTRAR.getEntries()) {
            var current = obj.get();
            if (current instanceof BushBlock) {
                blockCustom(current);
            } else {
                blockItem(current);
            }
        }
    }

    @Override
    public ItemModels getInstance()
    {
        return this;
    }
}
