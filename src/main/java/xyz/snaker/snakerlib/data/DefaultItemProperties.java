package xyz.snaker.snakerlib.data;

import net.minecraft.world.item.Item;

/**
 * Created by SnakerBone on 16/08/2023
 **/
public class DefaultItemProperties
{
    /**
     * Empty item properties instance
     **/
    public static final Item.Properties EMPTY = new Item.Properties();

    /**
     * Default properties for a tool item
     **/
    public static final Item.Properties TOOL = new Item.Properties().stacksTo(1);

    /**
     * Default properties for a special tool item (e.g. ender pearl)
     **/
    public static final Item.Properties SPECIAL = new Item.Properties().stacksTo(1).durability(16).defaultDurability(16).setNoRepair();
}
