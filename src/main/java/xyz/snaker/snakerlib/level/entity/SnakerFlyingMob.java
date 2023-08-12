package xyz.snaker.snakerlib.level.entity;

import xyz.snaker.snakerlib.data.SnakerConstants;
import xyz.snaker.snakerlib.level.entity.ai.SnakerFlyControl;
import xyz.snaker.snakerlib.level.entity.ai.SnakerFlyGoal;
import xyz.snaker.snakerlib.level.entity.ai.SnakerLookGoal;
import xyz.snaker.snakerlib.level.entity.ai.SnakerSwitchGameModeGoal;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public abstract class SnakerFlyingMob extends FlyingMob implements Enemy
{
    public SnakerFlyingMob(EntityType<? extends SnakerFlyingMob> type, Level level, int xpReward)
    {
        super(type, level);
        this.xpReward = xpReward;
        this.moveControl = new SnakerFlyControl(this);
    }

    public SnakerFlyingMob(EntityType<? extends SnakerFlyingMob> type, Level level)
    {
        this(type, level, SnakerConstants.EntityAttributes.MOB_XP_REWARD);
    }

    public boolean isCranky()
    {
        return isAlive() && isEffectiveAi() && isAggressive() && getTarget() != null;
    }

    @Override
    protected boolean shouldDespawnInPeaceful()
    {
        return true;
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, @NotNull DamageSource source)
    {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos)
    {

    }

    protected void registerGoals()
    {
        goalSelector.addGoal(4, new SnakerFlyGoal(this));
        goalSelector.addGoal(6, new SnakerLookGoal(this));
        goalSelector.addGoal(1, new SnakerSwitchGameModeGoal(this));
        goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
}