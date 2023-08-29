package xyz.snaker.tq.level.item;

import xyz.snaker.snakerlib.data.DefaultItemProperties;

import net.minecraft.world.item.Item;

/**
 * Created by SnakerBone on 29/08/2023
 **/
public class EmptyItem extends Item
{
    public EmptyItem()
    {
        super(DefaultItemProperties.EMPTY);
    }

    public EmptyItem(Properties properties)
    {
        super(properties);
    }
}
