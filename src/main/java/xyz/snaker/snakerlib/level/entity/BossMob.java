package xyz.snaker.snakerlib.level.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Mob;

/**
 * Created by SnakerBone on 16/08/2023
 **/
public interface BossMob<T extends Mob>
{
    /**
     * The boss event resource location
     **/
    ResourceLocation getResourceLocation();

    /**
     * The boss event display name
     **/
    Component getBossDisplayName();

    /**
     * The boss event bar colour
     **/
    BossEvent.BossBarColor getBossBarColour();

    /**
     * The boss instance
     **/
    T getBossInstance();
}
