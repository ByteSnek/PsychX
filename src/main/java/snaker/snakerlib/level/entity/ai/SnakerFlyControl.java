package snaker.snakerlib.level.entity.ai;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class SnakerFlyControl extends MoveControl
{
    private final Mob owner;
    private int duration;

    public SnakerFlyControl(Mob owner)
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

    private boolean canReach(Vec3 pos, int length)
    {
        AABB aabb = owner.getBoundingBox();
        for (int i = 1; i < length; ++i) {
            aabb = aabb.move(pos);
            if (!owner.level().noCollision(owner, aabb)) {
                return false;
            }
        }
        return true;
    }
}
