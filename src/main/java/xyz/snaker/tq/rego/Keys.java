package xyz.snaker.tq.rego;

import xyz.snaker.snakerlib.utility.ResourcePath;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

/**
 * Created by SnakerBone on 15/08/2023
 **/
public class Keys
{
    public static final TagKey<Block> GROUNDRICH = TagKey.create(Registries.BLOCK, new ResourcePath("groundrich"));

    public static final ResourceKey<Level> COMATOSE = ResourceKey.create(Registries.DIMENSION, new ResourcePath("comatose"));
}
