package xyz.snaker.tq.level.entity.boss;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.bossevents.CustomBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.snaker.snakerlib.level.entity.SnakerBoss;
import xyz.snaker.snakerlib.utility.ResourcePath;

import java.util.UUID;

/**
 * Created by SnakerBone on 18/06/2023
 **/
public class AntiCosmo extends SnakerBoss
{
    private final CustomBossEvent bossEvent = new CustomBossEvent(new ResourcePath("anti_cosmo"), getDisplayName());

    public AntiCosmo(EntityType<? extends PathfinderMob> type, Level level)
    {
        super(type, level);
    }

    public UUID getBossEventId()
    {
        return bossEvent.getId();
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance instance, @NotNull MobSpawnType reason, @Nullable SpawnGroupData data, @Nullable CompoundTag tag)
    {
        BOSS_INSTANCES.add(this);
        return super.finalizeSpawn(level, instance, reason, data, tag);
    }

    @Override
    public void remove(@NotNull Entity.RemovalReason reason)
    {
        BOSS_INSTANCES.remove(this);
        super.remove(reason);
    }

    @Override
    public void startSeenByPlayer(@NotNull ServerPlayer player)
    {
        super.startSeenByPlayer(player);
        bossEvent.setColor(BossEvent.BossBarColor.WHITE);
        bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(@NotNull ServerPlayer player)
    {
        super.stopSeenByPlayer(player);
        bossEvent.removePlayer(player);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source)
    {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.SCULK_CLICKING;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source)
    {
        return SoundEvents.SCULK_SHRIEKER_SHRIEK;
    }

    @Override
    public float getVoicePitch()
    {
        return 0.5F;
    }

    public static AttributeSupplier attributes()
    {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 570)
                .add(Attributes.ATTACK_DAMAGE, 35)
                .add(Attributes.ATTACK_SPEED, 1)
                .add(Attributes.FOLLOW_RANGE, 64)
                .add(Attributes.MOVEMENT_SPEED, 0.25).build();
    }

    @Override
    public void tick()
    {
        super.tick();
        bossEvent.setProgress(getHealth() / getMaxHealth());
    }
}