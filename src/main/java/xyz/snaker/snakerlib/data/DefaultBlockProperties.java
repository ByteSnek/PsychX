package xyz.snaker.snakerlib.data;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

/**
 * Created by SnakerBone on 16/08/2023
 **/
@FunctionalInterface
public interface DefaultBlockProperties
{
    /**
     * Empty block properties instance
     **/
    DefaultBlockProperties EMPTY = colour -> BlockBehaviour.Properties.of();

    /**
     * Default properties for normal blocks
     **/
    DefaultBlockProperties NORMAL = colour -> BlockBehaviour.Properties.of().mapColor(colour).sound(SoundType.STONE).pushReaction(PushReaction.NORMAL);

    /**
     * Default properties for nether blocks
     **/
    DefaultBlockProperties NETHER = colour -> BlockBehaviour.Properties.of().mapColor(colour).strength(0.5F).sound(SoundType.NETHER_ORE);

    /**
     * Default properties for plant blocks
     **/
    DefaultBlockProperties PLANT = colour -> BlockBehaviour.Properties.of().mapColor(colour).sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY).instabreak().noCollission().ignitedByLava();

    /**
     * Default properties for grass blocks
     **/
    DefaultBlockProperties GRASS = colour -> BlockBehaviour.Properties.of().mapColor(colour).sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY).instabreak().noCollission().ignitedByLava().replaceable();

    /**
     * Default properties for wood
     **/
    DefaultBlockProperties WOOD = colour -> BlockBehaviour.Properties.of().mapColor(colour).sound(SoundType.WOOD).pushReaction(PushReaction.NORMAL).strength(2, 3).ignitedByLava();

    /**
     * Default properties for blocks using special rendering
     **/
    DefaultBlockProperties PERSPECTIVE = colour -> BlockBehaviour.Properties.of().mapColor(colour).sound(SoundType.STONE).pushReaction(PushReaction.IGNORE /*Rendering precautions*/).strength(5, 8).noOcclusion().dynamicShape();

    /**
     * Default properties for blocks using cutout rendering
     **/
    DefaultBlockProperties CUTOUT = colour -> BlockBehaviour.Properties.of().mapColor(colour).strength(0.5F).sound(SoundType.NETHER_ORE).pushReaction(PushReaction.DESTROY).isViewBlocking(DefaultBlockConditions.NEVER::apply).noOcclusion();

    BlockBehaviour.Properties apply(MapColor colour);
}
