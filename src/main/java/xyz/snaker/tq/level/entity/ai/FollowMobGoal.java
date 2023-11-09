package xyz.snaker.tq.level.entity.ai;

import java.util.List;
import java.util.Optional;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;

/**
 * Created by SnakerBone on 9/11/2023
 **/
public class FollowMobGoal extends Goal
{
    private final PathfinderMob owner;
    private final Class<? extends PathfinderMob> toFollow;

    private final double speedModifier;
    private final double searchRange;

    public FollowMobGoal(PathfinderMob owner, Class<? extends PathfinderMob> toFollow, double speedModifier, double searchRange)
    {
        this.owner = owner;
        this.toFollow = toFollow;
        this.speedModifier = speedModifier;
        this.searchRange = searchRange;
    }

    @Override
    public boolean canUse()
    {
        return owner.getNavigation().isDone() && owner.getTarget() == null && owner.level().random.nextDouble() < 0.7;
    }

    @Override
    public void tick()
    {
        List<? extends PathfinderMob> mobs = owner.level().getEntitiesOfClass(toFollow, new AABB(owner.getOnPos()).inflate(searchRange), entity -> true);
        Optional<? extends PathfinderMob> firstMob = mobs.stream().findFirst();

        firstMob.ifPresent(mob ->
        {
            if (owner.hasLineOfSight(mob)) {
                owner.getNavigation().moveTo(mob, speedModifier);
            }
        });
    }
}
