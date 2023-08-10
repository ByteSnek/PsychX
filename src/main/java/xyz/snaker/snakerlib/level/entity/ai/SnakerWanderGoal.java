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
public class SnakerWanderGoal extends Goal
{
    private Animal owner;

    public SnakerWanderGoal(Animal owner)
    {
        this.owner = owner;
    }

    public boolean canUse()
    {
        return owner.getNavigation().isDone() && owner.getRandom().nextInt(10) == 0;
    }

    public boolean canContinueToUse()
    {
        return owner.getNavigation().isInProgress();
    }

    public void start()
    {
        Vec3 pos = this.findPos();
        if (pos != null) {
            owner.getNavigation().moveTo(owner.getNavigation().createPath(BlockPos.containing(pos), 1), 1.0D);
        }

    }

    @Nullable
    private Vec3 findPos()
    {
        Vec3 view = owner.getViewVector(0);
        Vec3 pos = HoverRandomPos.getPos(owner, 8, 7, view.x, view.z, Maths.HALF_PI, 3, 1);
        return pos != null ? pos : AirAndWaterRandomPos.getPos(owner, 8, 4, -2, view.x, view.z, Maths.HALF_PI);
    }
}
