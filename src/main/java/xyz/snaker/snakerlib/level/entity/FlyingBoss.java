package xyz.snaker.snakerlib.level.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import xyz.snaker.snakerlib.data.DefaultEntityAttributes;
import xyz.snaker.snakerlib.level.entity.ai.FlyController;
import xyz.snaker.snakerlib.level.entity.ai.SwitchGameModeGoal;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.bossevents.CustomBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by SnakerBone on 26/05/2023
 **/
public abstract class FlyingBoss<T extends FlyingMob> extends FlyingMob implements BossMob<T>
{
    /**
     * The boss instances currently active
     **/
    public static final Set<FlyingBoss<?>> BOSS_INSTANCES = new HashSet<>();

    /**
     * The current boss event
     **/
    protected final CustomBossEvent bossEvent = new CustomBossEvent(getResourceLocation(), getBossDisplayName());

    public FlyingBoss(EntityType<? extends FlyingMob> type, Level level, int xpReward)
    {
        super(type, level);
        this.xpReward = xpReward;
        this.moveControl = new FlyController(this);
    }

    public FlyingBoss(EntityType<? extends FlyingMob> type, Level level)
    {
        this(type, level, DefaultEntityAttributes.BOSS_XP_REWARD);
    }

    /**
     * Gets the current boss event UUID
     *
     * @return The current boss event UUID
     **/
    public UUID getBossEventId()
    {
        return bossEvent.getId();
    }

    /**
     * Checks if this mob is actually aggressive
     *
     * @return True if this mob is actually aggressive
     **/
    public boolean isCranky()
    {
        return isAlive() && isEffectiveAi() && isAggressive() && getTarget() != null;
    }

    @Override
    protected void registerGoals()
    {
        goalSelector.addGoal(1, new SwitchGameModeGoal(getBossInstance()));
    }

    @Nullable
    @Override
    @SuppressWarnings({"OverrideOnly", "deprecation"})
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason, @Nullable SpawnGroupData data, @Nullable CompoundTag tag)
    {
        if (getBossInstance() instanceof FlyingBoss<?> boss) {
            BOSS_INSTANCES.add(boss);
        }
        return super.finalizeSpawn(level, difficulty, reason, data, tag);
    }

    @Override
    public void remove(@NotNull RemovalReason reason)
    {
        if (getBossInstance() instanceof FlyingBoss<?> boss) {
            BOSS_INSTANCES.remove(boss);
        }
        super.remove(reason);
    }

    @Override
    public void startSeenByPlayer(@NotNull ServerPlayer player)
    {
        super.startSeenByPlayer(player);
        bossEvent.setColor(getBossBarColour());
        bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(@NotNull ServerPlayer player)
    {
        super.stopSeenByPlayer(player);
        bossEvent.removePlayer(player);
    }

    @Override
    public void tick()
    {
        super.tick();
        bossEvent.setProgress(getHealth() / getMaxHealth());
    }

    @Override
    protected boolean shouldDespawnInPeaceful()
    {
        return true;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer)
    {
        return false;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source)
    {
        return false;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state)
    {

    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos)
    {
        fallDistance = 0;
    }
}
