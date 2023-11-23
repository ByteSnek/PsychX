package bytesnek.tq.level.entity.creature;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;

import bytesnek.snakerlib.level.entity.FlyingPassive;
import bytesnek.snakerlib.level.entity.ai.goal.FollowSpecificMobGoal;

/**
 * Created by SnakerBone on 10/11/2023
 **/
public abstract class Butterfly extends FlyingPassive
{
    public Butterfly(EntityType<? extends Animal> type, Level level)
    {
        super(type, level);
    }

    @Override
    public void registerGoals()
    {
        super.registerGoals();
        goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Spider.class, 6, 1, 1.2));
        goalSelector.addGoal(3, new FollowSpecificMobGoal(this, Bee.class, 1, 3, 7));
        goalSelector.addGoal(1, new PanicGoal(this, 1.25));
    }
}
