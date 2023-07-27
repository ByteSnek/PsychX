package snaker.snakerlib.data;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

/**
 * Created by SnakerBone on 25/05/2023
 **/
public enum SnakerConstants
{
    MOVE_SPEED(0.25),
    FLY_SPEED(0.5),
    ATTACK_SPEED(1),
    FLY_FOLLOW_RANGE(64),
    FOLLOW_RANGE(16),
    SPEED_MODIFIER(1.35),
    CREATURE_XP_REWARD(8),
    MOB_XP_REWARD(16),
    BOSS_XP_REWARD(8000);

    final double value;

    SnakerConstants(double value)
    {
        this.value = value;
    }

    public int asInt()
    {
        return (int) value;
    }

    public float asFloat()
    {
        return (float) value;
    }

    public double asDouble()
    {
        return value;
    }

    public static class BlockProperties
    {
        public static final BlockBehaviour.Properties EMPTY = BlockBehaviour.Properties.of();
        public static final BlockBehaviour.Properties NORMAL = BlockBehaviour.Properties.of().mapColor(MapColor.NONE).sound(SoundType.STONE).pushReaction(PushReaction.NORMAL);
        public static final BlockBehaviour.Properties PLANT = BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY).instabreak().noCollission();
        public static final BlockBehaviour.Properties PERSPECTIVE = BlockBehaviour.Properties.of().mapColor(MapColor.NONE).sound(SoundType.STONE).pushReaction(PushReaction.IGNORE /*Rendering precautions*/).strength(5).noOcclusion().dynamicShape();
    }

    public static class ItemProperties
    {
        public static final Item.Properties EMPTY = new Item.Properties();
        public static final Item.Properties TOOL = new Item.Properties().stacksTo(1);
        public static final Item.Properties LIMITED = new Item.Properties().stacksTo(1).durability(16).defaultDurability(16).setNoRepair();
    }
}
