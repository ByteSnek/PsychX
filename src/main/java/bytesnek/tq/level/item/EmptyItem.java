package bytesnek.tq.level.item;

import xyz.snaker.snakerlib.utility.item.ItemProperties;

import net.minecraft.world.item.Item;

/**
 * Created by SnakerBone on 29/08/2023
 **/
public class EmptyItem extends Item
{
    public EmptyItem()
    {
        super(ItemProperties.EMPTY);
    }

    public EmptyItem(Properties properties)
    {
        super(properties);
    }
}
