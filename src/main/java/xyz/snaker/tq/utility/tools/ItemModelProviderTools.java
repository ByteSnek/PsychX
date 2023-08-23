package xyz.snaker.tq.utility.tools;

import xyz.snaker.snakerlib.utility.tools.ResourceStuff;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;

/**
 * Created by SnakerBone on 21/08/2023
 **/
public interface ItemModelProviderTools<T extends ItemModelProvider>
{
    T getInstance();

    default void spawnEgg(Item item)
    {
        getInstance().withExistingParent(ResourceStuff.getPath(item),
                getInstance().modLoc("egg"));
    }

    default void perspective(Item item)
    {
        getInstance().withExistingParent(ResourceStuff.getPath(item),
                getInstance().modLoc("perspective"));
    }

    default void cosmoSpine(Item item)
    {
        getInstance().withExistingParent(ResourceStuff.getPath(item),
                getInstance().modLoc("cosmo_spine"));
    }

    default void blockItem(Block block)
    {
        getInstance().withExistingParent(ResourceStuff.getPath(block),
                getInstance().modLoc("block/" + ResourceStuff.getPath(block)));
    }

    default <I extends ItemLike> void item(I item)
    {
        getInstance().withExistingParent(ResourceStuff.getPath((Item) item),
                getInstance().mcLoc("item/generated")).texture("layer0",
                getInstance().modLoc("item/" + ResourceStuff.getPath((Item) item)));
    }

    default <B extends ItemLike> void blockCustom(B block)
    {
        getInstance().withExistingParent(ResourceStuff.getPath((Block) block),
                getInstance().mcLoc("item/generated")).texture("layer0",
                getInstance().modLoc("block/" + ResourceStuff.getPath((Block) block)));
    }
}
