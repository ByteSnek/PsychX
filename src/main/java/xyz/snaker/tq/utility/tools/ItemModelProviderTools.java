package xyz.snaker.tq.utility.tools;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by SnakerBone on 21/08/2023
 **/
public interface ItemModelProviderTools<T extends ItemModelProvider>
{
    T getInstance();

    default void egg(RegistryObject<Item> item)
    {
        getInstance().withExistingParent(item.getId().getPath(),
                getInstance().modLoc("egg"));
    }

    default void perspective(RegistryObject<Item> item)
    {
        getInstance().withExistingParent(item.getId().getPath(),
                getInstance().modLoc("perspective"));
    }

    default void cosmoSpine(RegistryObject<Item> item)
    {
        getInstance().withExistingParent(item.getId().getPath(),
                getInstance().modLoc("cosmo_spine"));
    }

    default void blockItem(RegistryObject<Block> block)
    {
        getInstance().withExistingParent(block.getId().getPath(),
                getInstance().modLoc("block/" + block.getId().getPath()));
    }

    default <I extends ItemLike> void item(RegistryObject<I> item)
    {
        getInstance().withExistingParent(item.getId().getPath(),
                getInstance().mcLoc("item/generated")).texture("layer0",
                getInstance().modLoc("item/" + item.getId().getPath()));
    }

    default <I extends ItemLike> void blockCustom(RegistryObject<I> block)
    {
        getInstance().withExistingParent(block.getId().getPath(),
                getInstance().mcLoc("item/generated")).texture("layer0",
                getInstance().modLoc("block/" + block.getId().getPath()));
    }
}
