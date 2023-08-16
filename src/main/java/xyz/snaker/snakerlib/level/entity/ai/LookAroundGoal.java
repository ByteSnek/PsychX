package xyz.snaker.snakerlib.level.entity.ai;

import java.util.EnumSet;

import xyz.snaker.snakerlib.math.Maths;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class LookAroundGoal extends Goal
{
    /**
     * The mob using this goal
     **/
    private final Mob owner;

    public LookAroundGoal(Mob owner)
    {
        this.owner = owner;
        setFlags(EnumSet.of(Flag.LOOK));
    }

    @Override
    public boolean canUse()
    {
        return true;
    }

    @Override
    public boolean requiresUpdateEveryTick()
    {
        return true;
    }

    @Override
    public void tick()
    {
        if (owner.getTarget() == null) {
            Vec3 movement = owner.getDeltaMovement();
            owner.setYRot(Maths.rotateTowards(movement.x, movement.z));
            owner.yBodyRot = owner.getYRot();

        } else {
            LivingEntity target = owner.getTarget();
            if (target.distanceToSqr(owner) < 4096) {
                double x = target.getX() - owner.getX();
                double z = target.getZ() - owner.getZ();
                owner.setYRot(Maths.rotateTowards(x, z));
                owner.yBodyRot = owner.getYRot();
            }
        }
    }
}
