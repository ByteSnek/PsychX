package xyz.snaker.tq.utility;

import net.minecraft.world.level.Level;

/**
 * Created by SnakerBone on 3/12/2023
 **/
public record ExplosionData(float radius, boolean fire, Level.ExplosionInteraction interaction) {}
