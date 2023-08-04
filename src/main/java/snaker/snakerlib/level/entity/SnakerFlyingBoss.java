package snaker.snakerlib.level.entity;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import snaker.snakerlib.config.SnakerConfig;
import snaker.snakerlib.data.SnakerConstants;
import snaker.snakerlib.level.entity.ai.SnakerFlyControl;
import snaker.snakerlib.level.entity.ai.SnakerSwitchGameModeGoal;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Created by SnakerBone on 26/05/2023
 **/
public abstract class SnakerFlyingBoss extends FlyingMob
{
    public SnakerFlyingBoss(EntityType<? extends FlyingMob> type, Level level, int xpReward)
    {
        super(type, level);
        this.xpReward = xpReward;
        this.moveControl = new SnakerFlyControl(this);
    }

    public SnakerFlyingBoss(EntityType<? extends FlyingMob> type, Level level)
    {
        this(type, level, SnakerConstants.EntityAttributes.BOSS_XP_REWARD);
    }

    public boolean isCranky()
    {
        return isAlive() && isEffectiveAi() && isAggressive() && getTarget() != null;
    }

    public void extraHealth(int amount, AttributeModifier.Operation operation)
    {
        Objects.requireNonNull(getAttribute(Attributes.MAX_HEALTH)).addTransientModifier(new AttributeModifier("ExtraHealth", amount, operation));
    }

    @Override
    public void tick()
    {
        super.tick();
        Predicate<ServerPlayer> isPlayerCreative = ServerPlayer::isCreative;
        LivingEntity lastHurtByMob = getLastHurtByMob();
        if (lastHurtByMob instanceof ServerPlayer player) {
            if (!isPlayerCreative.test(player)) {
                setTarget(player);
            } else if (isPlayerCreative.test(player) && SnakerConfig.COMMON.playerVulnerableInCreative.get()) {
                setTarget(player);
            }
        }
    }

    @Override
    protected void registerGoals()
    {
        goalSelector.addGoal(1, new SnakerSwitchGameModeGoal(this));
    }

    @Override
    public boolean removeWhenFarAway(double distance)
    {
        return false;
    }

    @Override
    public boolean shouldDespawnInPeaceful()
    {
        return true;
    }
}
