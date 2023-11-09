package xyz.snaker.tq.level.entity.mob;

import java.util.List;
import java.util.function.Predicate;

import xyz.snaker.snakerlib.level.entity.Hostile;
import xyz.snaker.snakerlib.math.Maths;
import xyz.snaker.tq.config.Config;
import xyz.snaker.tq.level.entity.Comatosian;
import xyz.snaker.tq.level.world.EntitySpawner;
import xyz.snaker.tq.rego.Effects;
import xyz.snaker.tq.rego.Sounds;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 02/01/2023
 **/
public class Flare extends Hostile implements Comatosian
{
    public Flare(EntityType<? extends Flare> type, Level level)
    {
        super(type, level);
    }

    public static AttributeSupplier attributes()
    {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.FOLLOW_RANGE, 64)
                .add(Attributes.ATTACK_SPEED, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.25).build();
    }

    public static boolean spawnRules(EntityType<Flare> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random)
    {
        return EntitySpawner.COMATOSE.check(level, pos, random, 75);
    }

    @Override
    public void die(@NotNull DamageSource source)
    {
        super.die(source);

        if (Config.COMMON.flashBangOverlay.get()) {
            AABB aabb = new AABB(getOnPos()).inflate(10);
            List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class, aabb);

            float effectDuration = Config.COMMON.flashBangDuration.get().floatValue();

            for (LivingEntity entity : entities) {
                if (isFacingInGeneralDirection(entity)) {
                    entity.addEffect(new MobEffectInstance(Effects.FLASHBANG.get(), Maths.secondsToTicks((int) effectDuration), 0, false, false));
                }
            }
        }
    }

    public boolean isFacingInGeneralDirection(LivingEntity entity)
    {
        Vec3 playerView = entity.getViewVector(1).normalize();

        double distX = getX() - entity.getX();
        double distY = getEyeY() - entity.getEyeY();
        double distZ = getZ() - entity.getZ();

        Vec3 distBetween = new Vec3(distX, distY, distZ);

        double length = distBetween.length();
        double playerViewY = playerView.y;

        distBetween = distBetween.scale(0.1);

        double dot = playerView.dot(distBetween);
        double threshold = 0.025;

        return dot > 0 - threshold / length && playerViewY < 1 - threshold && entity.hasLineOfSight(this);
    }

    @Override
    public void registerGoals()
    {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(6, new RandomStrollGoal(this, 1));
        goalSelector.addGoal(5, new HurtByTargetGoal(this));
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.3, false));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity)
    {
        entity.setSecondsOnFire(5);
        return super.doHurtTarget(entity);
    }

    @Override
    public SoundEvent getAmbientSound()
    {
        return SoundEvents.BLAZE_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(@NotNull DamageSource source)
    {
        return SoundEvents.BLAZE_HURT;
    }

    @Override
    public SoundEvent getDeathSound()
    {
        return Sounds.ENTITY_DEATH.get();
    }

    @Override
    public boolean fireImmune()
    {
        return true;
    }

    @Override
    public void tick()
    {
        Predicate<ServerPlayer> isPlayerCreative = ServerPlayer::isCreative;
        LivingEntity lastHurtByMob = getLastHurtByMob();
        if (lastHurtByMob instanceof ServerPlayer player) {
            if (!isPlayerCreative.test(player)) {
                setTarget(player);
            }
        }
        super.tick();
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean isAdaptive()
    {
        return true;
    }
}
