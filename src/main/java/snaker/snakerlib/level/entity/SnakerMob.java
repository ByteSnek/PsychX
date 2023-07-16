package snaker.snakerlib.level.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import snaker.snakerlib.data.SnakerConstants;
import snaker.snakerlib.level.entity.ai.SnakerSwitchGameModeGoal;

import java.util.Objects;

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
        this(type, level, SnakerConstants.MOB_XP_REWARD.asInt());
    }

    public void extraHealth(int amount, AttributeModifier.Operation operation)
    {
        Objects.requireNonNull(getAttribute(Attributes.MAX_HEALTH)).addTransientModifier(new AttributeModifier("ExtraHealth", amount, operation));
    }

    public void extraAttackDamage(int amount, AttributeModifier.Operation operation)
    {
        Objects.requireNonNull(getAttribute(Attributes.ATTACK_DAMAGE)).addTransientModifier(new AttributeModifier("ExtraAttackDamage", amount, operation));
    }

    public void extraAttackSpeed(int amount, AttributeModifier.Operation operation)
    {
        Objects.requireNonNull(getAttribute(Attributes.ATTACK_SPEED)).addTransientModifier(new AttributeModifier("ExtraAttackSpeed", amount, operation));
    }

    public void extraMovementSpeed(int amount, AttributeModifier.Operation operation)
    {
        Objects.requireNonNull(getAttribute(Attributes.MOVEMENT_SPEED)).addTransientModifier(new AttributeModifier("ExtraMovementSpeed", amount, operation));
    }

    public void extraFlyingSpeed(int amount, AttributeModifier.Operation operation)
    {
        Objects.requireNonNull(getAttribute(Attributes.FLYING_SPEED)).addTransientModifier(new AttributeModifier("ExtraFlyingSpeed", amount, operation));
    }

    public void extraFollowRange(int amount, AttributeModifier.Operation operation)
    {
        Objects.requireNonNull(getAttribute(Attributes.FOLLOW_RANGE)).addTransientModifier(new AttributeModifier("ExtraFollowRange", amount, operation));
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
