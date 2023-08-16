package xyz.snaker.snakerlib.level.entity.ai;

import javax.annotation.Nullable;

import xyz.snaker.snakerlib.math.Maths;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.phys.Vec3;

/**
 * Created by SnakerBone on 21/02/2023
 **/
public class WanderGoal extends Goal
{
    /**
     * The animal using this goal
     **/
    private Animal owner;

    public WanderGoal(Animal owner)
    {
        this.owner = owner;
    }

    @Override
    public boolean canUse()
    {
        return owner.getNavigation().isDone() && owner.getRandom().nextInt(10) == 0;
    }

    @Override
    public boolean canContinueToUse()
    {
        return owner.getNavigation().isInProgress();
    }

    @Override
    public void start()
    {
        Vec3 pos = this.findPos();
        if (pos != null) {
            owner.getNavigation().moveTo(owner.getNavigation().createPath(BlockPos.containing(pos), 1), 1.0D);
        }

    }

    /**
     * Finds a random position around this animal
     *
     * @return The position as a Vec3 or null if there is none
     **/
    @Nullable
    private Vec3 findPos()
    {
        Vec3 view = owner.getViewVector(0);
        Vec3 pos = HoverRandomPos.getPos(owner, 8, 7, view.x, view.z, Maths.HALF_PI, 3, 1);
        return pos != null ? pos : AirAndWaterRandomPos.getPos(owner, 8, 4, -2, view.x, view.z, Maths.HALF_PI);
    }
}
