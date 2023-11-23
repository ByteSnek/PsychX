package bytesnek.tq.level.item;

import net.minecraft.world.item.Item;

import bytesnek.snakerlib.utility.item.ItemProperties;

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
