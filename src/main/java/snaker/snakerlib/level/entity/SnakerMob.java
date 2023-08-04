package snaker.snakerlib.level.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import snaker.snakerlib.data.SnakerConstants;
import snaker.snakerlib.level.entity.ai.SnakerSwitchGameModeGoal;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public abstract class SnakerMob extends Monster
{
    public SnakerMob(EntityType<? extends Monster> type, Level level, int xpReward)
    {
        super(type, level);
        this.xpReward = xpReward;
    }

    public SnakerMob(EntityType<? extends Monster> type, Level level)
    {
        this(type, level, SnakerConstants.EntityAttributes.MOB_XP_REWARD);
    }

    public boolean isCranky()
    {
        return isAlive() && isEffectiveAi() && isAggressive() && getTarget() != null;
    }

    @Override
    protected void registerGoals()
    {
        goalSelector.addGoal(1, new SnakerSwitchGameModeGoal(this));
        goalSelector.addGoal(6, new RandomStrollGoal(this, 1));
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.3, false));
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6));
        goalSelector.addGoal(5, new HurtByTargetGoal(this));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
}
