package xyz.snaker.tq.level.entity.mob;

import xyz.snaker.snakerlib.level.entity.SnakerFlyingMob;
import xyz.snaker.snakerlib.level.entity.ai.SnakerFlyGoal;
import xyz.snaker.snakerlib.utility.tools.WorldStuff;
import xyz.snaker.tq.level.entity.projectile.CosmicRay;
import xyz.snaker.tq.rego.Entities;
import xyz.snaker.tq.rego.Keys;
import xyz.snaker.tq.rego.Sounds;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 15/07/2023
 **/
public class Leet extends SnakerFlyingMob implements RangedAttackMob
{
    public Leet(EntityType<Leet> type, Level level)
    {
        super(type, level);
    }

    @Override
    protected void registerGoals()
    {
        goalSelector.addGoal(0, new SnakerFlyGoal(this));
    }

    public static AttributeSupplier attributes()
    {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.FOLLOW_RANGE, 64)
                .add(Attributes.ATTACK_SPEED, 1)
                .add(Attributes.FLYING_SPEED, 0.25)
                .add(Attributes.MOVEMENT_SPEED, 0.25).build();
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public SoundEvent getHurtSound(@NotNull DamageSource source)
    {
        return SoundEvents.GENERIC_HURT;
    }

    @Override
    public SoundEvent getDeathSound()
    {
        return Sounds.ENTITY_DEATH.get();
    }

    @Override
    public void performRangedAttack(LivingEntity target, float flval)
    {
        CosmicRay ray = new CosmicRay(Entities.COSMIC_RAY.get(), level());

        double x = target.getX() - getX();
        double y = target.getY() + target.getEyeHeight() - 1;
        double z = target.getZ() - getZ();

        ray.shoot(x, y - ray.getY() + Math.sqrt(x * x + z * z) * 0.2, z, 1.6F, 12);

        level().addFreshEntity(ray);
    }

    public static <T extends Entity> boolean spawnRules(EntityType<T> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random)
    {
        return WorldStuff.isDimension(level, Keys.COMATOSE);
    }
}
