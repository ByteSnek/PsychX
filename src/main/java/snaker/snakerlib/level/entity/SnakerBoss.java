package snaker.snakerlib.level.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import snaker.snakerlib.data.SnakerConstants;
import snaker.snakerlib.level.entity.ai.SnakerFlyControl;
import snaker.snakerlib.level.entity.ai.SnakerSwitchGameModeGoal;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by SnakerBone on 4/01/2023
 **/
public abstract class SnakerBoss extends PathfinderMob
{
    public static final Set<SnakerBoss> BOSS_INSTANCES = new HashSet<>();

    public SnakerBoss(EntityType<? extends PathfinderMob> type, Level level, int xpReward)
    {
        super(type, level);
        this.xpReward = xpReward;
        this.moveControl = new SnakerFlyControl(this);
    }

    public SnakerBoss(EntityType<? extends PathfinderMob> type, Level level)
    {
        this(type, level, SnakerConstants.BOSS_XP_REWARD.asInt());
    }

    public void extraHealth(int amount, AttributeModifier.Operation operation)
    {
        Objects.requireNonNull(getAttribute(Attributes.MAX_HEALTH)).addTransientModifier(new AttributeModifier("ExtraHealth", amount, operation));
    }

    @Nullable
    @Override
    @SuppressWarnings({"deprecation", "OverrideOnly"})
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance instance, @NotNull MobSpawnType reason, @Nullable SpawnGroupData data, @Nullable CompoundTag tag)
    {
        return super.finalizeSpawn(level, instance, reason, data, tag);
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
