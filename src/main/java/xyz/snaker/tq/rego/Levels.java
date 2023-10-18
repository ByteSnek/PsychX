package xyz.snaker.tq.rego;

import xyz.snaker.snakerlib.utility.ResourcePath;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class Levels
{
    public static final ResourceKey<Level> COMATOSE = key("comatose");

    static ResourceKey<Level> key(String name)
    {
        return ResourceKey.create(Registries.DIMENSION, new ResourcePath(name));
    }
}
