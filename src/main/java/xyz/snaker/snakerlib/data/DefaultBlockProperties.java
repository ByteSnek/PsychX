package xyz.snaker.snakerlib.data;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

/**
 * Created by SnakerBone on 16/08/2023
 **/
public class DefaultBlockProperties
{
    /**
     * Empty block properties instance
     **/
    public static final BlockBehaviour.Properties EMPTY = BlockBehaviour.Properties.of();

    /**
     * Default properties for normal blocks
     **/
    public static final BlockBehaviour.Properties NORMAL = BlockBehaviour.Properties.of().mapColor(MapColor.NONE).sound(SoundType.STONE).pushReaction(PushReaction.NORMAL);

    /**
     * Default properties for plant blocks
     **/
    public static final BlockBehaviour.Properties PLANT = BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY).instabreak().noCollission().ignitedByLava();

    /**
     * Default properties for grass blocks
     **/
    public static final BlockBehaviour.Properties GRASS = BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY).instabreak().noCollission().ignitedByLava().replaceable();

    /**
     * Default properties for blocks using special rendering
     **/
    public static final BlockBehaviour.Properties PERSPECTIVE = BlockBehaviour.Properties.of().mapColor(MapColor.NONE).sound(SoundType.STONE).pushReaction(PushReaction.IGNORE /*Rendering precautions*/).strength(5).noOcclusion().dynamicShape();
}
