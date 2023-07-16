package snaker.snakerlib.level.entity.ai;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class SnakerFlyGoal extends Goal
{
    private final Mob owner;

    public SnakerFlyGoal(Mob owner)
    {
        this.owner = owner;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse()
    {
        MoveControl control = owner.getMoveControl();

        if (!control.hasWanted()) {
            return true;
        } else {
            double x = control.getWantedX() - owner.getX();
            double y = control.getWantedY() - owner.getY();
            double z = control.getWantedZ() - owner.getZ();

            double xyz = x * x + y * y + z * z;

            return xyz < 1 || xyz > 3600;
        }
    }

    @Override
    public boolean canContinueToUse()
    {
        return false;
    }

    @Override
    public void start()
    {
        RandomSource random = owner.getRandom();

        double x = owner.getX() + ((random.nextFloat() * 2 - 1) * 16);
        double y = owner.getY() + ((random.nextFloat() * 2 - 1) * 16);
        double z = owner.getZ() + ((random.nextFloat() * 2 - 1) * 16);

        owner.getMoveControl().setWantedPosition(x, y, z, 1);
    }
}
