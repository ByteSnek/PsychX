package xyz.snaker.tq.utility.tools;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * Created by SnakerBone on 14/10/2023
 **/
public interface ItemTagsProviderTools<T extends ItemTagsProvider>
{
    T getInstance();

    default void tag(TagKey<Item> key, List<Supplier<Item>> items)
    {
        for (var item : items) {
            getInstance().tag(key).add(item.get());
        }
    }
}
