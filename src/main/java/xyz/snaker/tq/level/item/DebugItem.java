package xyz.snaker.tq.level.item;

import net.neoforged.fml.loading.FMLEnvironment;

/**
 * Created by SnakerBone on 27/11/2023
 **/
public interface DebugItem
{
    default boolean getExtraConditions()
    {
        return !FMLEnvironment.production;
    }
}
