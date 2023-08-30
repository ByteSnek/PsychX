package xyz.snaker.tq.level.entity.boss;

import xyz.snaker.snakerlib.level.entity.Boss;
import xyz.snaker.snakerlib.utility.ResourcePath;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by SnakerBone on 18/06/2023
 **/
public class AntiCosmo extends Boss<AntiCosmo>
{
    public AntiCosmo(EntityType<? extends PathfinderMob> type, Level level)
    {
        super(type, level);
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

    @Nullable
    @Override
    public SoundEvent getAmbientSound()
    {
        return SoundEvents.SCULK_CLICKING;
    }

    @Nullable
    @Override
    public SoundEvent getHurtSound(@NotNull DamageSource source)
    {
        return SoundEvents.SCULK_SHRIEKER_SHRIEK;
    }

    @Override
    public float getVoicePitch()
    {
        return 0.5F;
    }

    @Override
    public ResourceLocation getResourceLocation()
    {
        return ResourcePath.thisClass();
    }

    @Override
    public Component getBossDisplayName()
    {
        return getDisplayName();
    }

    @Override
    public BossEvent.BossBarColor getBossBarColour()
    {
        return BossEvent.BossBarColor.WHITE;
    }

    @Override
    public AntiCosmo getBossInstance()
    {
        return this;
    }
}