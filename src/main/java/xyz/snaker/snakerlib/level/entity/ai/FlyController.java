package xyz.snaker.snakerlib.level.entity.ai;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class FlyController extends MoveControl
{
    /**
     * The mob using this flying controller
     **/
    private final Mob owner;

    /**
     * The duration left until the {@link MoveControl#operation} gets set to {@link MoveControl.Operation#WAIT}
     **/
    private int duration;

    public FlyController(Mob owner)
    {
        super(owner);
        this.owner = owner;
    }

    @Override
    public void tick()
    {
        if (operation == Operation.MOVE_TO) {
            if (duration-- <= 0) {
                duration += owner.getRandom().nextInt(5) + 2;
                Vec3 pos = new Vec3(this.wantedX - owner.getX(), wantedY - owner.getY(), wantedZ - owner.getZ());
                double length = pos.length();
                pos = pos.normalize();
                if (canReach(pos, Mth.ceil(length))) {
                    owner.setDeltaMovement(owner.getDeltaMovement().add(pos.scale(0.1D)));
                } else {
                    operation = Operation.WAIT;
                }
            }
        }
    }

    /**
     * Checks for collisions around the mob
     *
     * @param pos    The mob's wanted position
     * @param length The amount of checks to do
     * @return True if there's no collisions present around the mob
     **/
    private boolean canReach(Vec3 pos, int length)
    {
        AABB aabb = owner.getBoundingBox();
        for (int i = 1;
             i < length;
             ++i) {
            aabb = aabb.move(pos);
            if (!owner.level().noCollision(owner, aabb)) {
                return false;
            }
        }
        return true;
    }
}
