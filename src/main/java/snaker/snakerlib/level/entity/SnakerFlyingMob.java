package snaker.snakerlib.level.entity;

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
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.data.SnakerConstants;
import snaker.snakerlib.level.entity.ai.SnakerFlyControl;
import snaker.snakerlib.level.entity.ai.SnakerFlyGoal;
import snaker.snakerlib.level.entity.ai.SnakerLookGoal;
import snaker.snakerlib.level.entity.ai.SnakerSwitchGameModeGoal;

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
        this(type, level, SnakerConstants.MOB_XP_REWARD.asInt());
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
