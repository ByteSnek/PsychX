package bytesnek.tq.level.entity.boss;

import javax.annotation.Nullable;
import java.util.List;

import xyz.snaker.snakerlib.level.entity.FlyingHostile;
import xyz.snaker.snakerlib.level.entity.ai.goal.FlyGoal;
import xyz.snaker.snakerlib.level.entity.ai.goal.LookAroundGoal;
import xyz.snaker.snakerlib.math.Maths;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.NotNull;

import bytesnek.tq.level.entity.Comatosian;
import bytesnek.tq.level.entity.projectile.ExplosiveHommingArrow;
import bytesnek.tq.level.entity.projectile.HommingArrow;
import bytesnek.tq.rego.Sounds;

/**
 * Created by SnakerBone on 4/01/2023
 **/
public class Utterfly extends FlyingHostile implements Comatosian
{
    private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(Utterfly.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(Utterfly.class, EntityDataSerializers.BOOLEAN);

    private final ServerBossEvent bossEvent = new ServerBossEvent(getDisplayName(), BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.PROGRESS);

    private boolean triggerExplosion;

    public Utterfly(EntityType<? extends FlyingHostile> type, Level level)
    {
        super(type, level);
    }

    private boolean damageSourceMalus(DamageSource source)
    {
        return source == level().damageSources().cactus() || source == level().damageSources().sweetBerryBush() || source == level().damageSources().inWall() || source == level().damageSources().lava() || source == level().damageSources().drown();
    }

    public boolean getCharging()
    {
        return entityData.get(CHARGING);
    }

    public void setCharging(boolean charging)
    {
        entityData.set(CHARGING, charging);
    }

    public int getPhase()
    {
        return entityData.get(PHASE);
    }

    public void setPhase(int phase)
    {
        entityData.set(PHASE, phase);
    }

    public static AttributeSupplier attributes()
    {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 440)
                .add(Attributes.ATTACK_DAMAGE, 20)
                .add(Attributes.ATTACK_SPEED, 1)
                .add(Attributes.FOLLOW_RANGE, 64)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FLYING_SPEED, 0.25).build();
    }

    @Override
    public void registerGoals()
    {
        goalSelector.addGoal(5, new FlyGoal(this));
        goalSelector.addGoal(7, new LookAroundGoal(this));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void startSeenByPlayer(@NotNull ServerPlayer player)
    {
        super.startSeenByPlayer(player);
        bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(@NotNull ServerPlayer player)
    {
        super.stopSeenByPlayer(player);
        bossEvent.removePlayer(player);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount)
    {
        if (damageSourceMalus(source)) {
            return false;
        }
        if (getCharging()) {
            return false;
        }
        boolean result = super.hurt(source, amount);
        if (!level().isClientSide && getHealth() <= 1 && getPhase() < 3) {
            setCharging(true);
            setPhase(getPhase() + 1);
            getNavigation().stop();
            setHealth(1);
            dead = false;
        }
        return result;
    }

    @Override
    public void tick()
    {
        super.tick();
        if (getY() > 120) {
            getMoveControl().setWantedPosition(getX() - getMoveControl().getWantedX(), (getY() - getMoveControl().getWantedY()) / 8, getZ() - getMoveControl().getWantedZ(), getSpeed());
        }
        switch (getPhase()) {
            case 1 -> {
                if (!getCharging()) {
                    addRangedAttackAI(false, 4, 1, 8);
                }
            }
            case 2 -> {
                if (!getCharging()) {
                    addRangedAttackAI(false, 8, 1, 6);
                }
            }
            case 3 -> {
                if (!getCharging()) {
                    addRangedAttackAI(true, 16, 1, 4);
                    addDodgingAI();
                }
            }
        }
        if (!level().isClientSide) {
            if (getCharging() && !dead) {
                if (getHealth() < getMaxHealth()) {
                    heal(16F / getPhase());
                    setDeltaMovement(0, 0, 0);
                    setPos(xo, yo, zo);
                } else {
                    triggerExplosion = true;
                    setCharging(false);
                }
            } else if (!getCharging() && !dead && triggerExplosion) {
                for (int i = 0;
                     i < Maths.pow2e(getPhase());
                     i++) {
                    explode(i);
                }
                triggerExplosion = false;
            }
        }
    }

    @Override
    public boolean isDeadOrDying()
    {
        return getPhase() >= 3 && getHealth() <= 0;
    }

    @Override
    public void heal(float amount)
    {
        setHealth(getHealth() + amount);
    }

    @Override
    public boolean ignoreExplosion()
    {
        return true;
    }

    public void explode(float radius)
    {
        double x = getRandomX(1) - 0.75;
        double y = getRandomY();
        double z = getRandomZ(1) - 0.75;
        level().explode(this, x, y, z, radius, Level.ExplosionInteraction.NONE);
    }

    public void addRangedAttackAI(boolean explosive, float velocity, float inaccuracy, float delay)
    {
        LivingEntity target = getTarget();
        if (!getCharging() && target != null && tickCount % delay == 0 && target.distanceToSqr(this) < 4096 && hasLineOfSight(target)) {
            double x = target.getX() - getX();
            double y = target.getY() - getY();
            double z = target.getZ() - getZ();
            setXRot(Maths.rotateTowards(y, (x * x + z * z)));
            xRotO = getXRot();
            level().playSound(null, getX(), getY(), getZ(), Sounds.UTTERFLY_SHOOT.get(), getSoundSource(), 1, 1);
            if (explosive) {
                ExplosiveHommingArrow arrow = new ExplosiveHommingArrow(level(), this, 8);
                arrow.shoot(x, y, z, velocity * getPhase(), inaccuracy);
                level().addFreshEntity(arrow);
            } else {
                HommingArrow arrow = new HommingArrow(level(), this, 8);
                arrow.shoot(x, y, z, velocity * getPhase(), inaccuracy);
                level().addFreshEntity(arrow);
            }
        }
    }

    public void addDodgingAI()
    {
        List<AbstractArrow> arrows = level().getEntitiesOfClass(AbstractArrow.class, getBoundingBox().inflate(16), projectile -> distanceTo(projectile) <= 16 + projectile.getBbWidth() / 2);
        if (!getCharging() && !arrows.isEmpty()) {
            for (AbstractArrow arrow : arrows) {
                double x = arrow.getX() - arrow.xo;
                double y = arrow.getY() - arrow.yo;
                double z = arrow.getZ() - arrow.zo;
                Vec3 movement = arrow.getDeltaMovement();
                Vec3 position = arrow.position();
                Vec3 motion = new Vec3(x, y, z);
                if ((motion.length() < 0.1)) {
                    if (arrow instanceof HommingArrow || arrow instanceof Arrow) {
                        continue;
                    } else {
                        break;
                    }
                }
                double arrowSpeed = arrow.getDeltaMovement().normalize().dot(position().subtract(position).normalize());
                if (arrowSpeed > 0.96) {
                    double dodgeScale = 1, dodgeFactorX = 2, dodgeFactorZ = -2;
                    Vec3 dodgeAmount = new Vec3(0, 1, 0);
                    Vec3 arrowCrossVec = arrow.getDeltaMovement().cross(dodgeAmount).normalize().scale(dodgeScale);
                    Vec3 addX = position().add(position().scale(dodgeFactorX)), addZ = position().add(position().scale(dodgeFactorZ));
                    Vec3 normalizeX = addX.subtract(position), normalizeZ = addZ.subtract(position);
                    arrowCrossVec = normalizeX.dot(movement) > normalizeZ.dot(movement) ? arrowCrossVec.scale(-1) : arrowCrossVec;
                    setDeltaMovement(getDeltaMovement().add(arrowCrossVec));
                }
            }
        }
    }

    @Nullable
    @Override
    public SoundEvent getAmbientSound()
    {
        return Sounds.UTTERFLY_AMBIENT.get();
    }

    @Override
    public SoundEvent getDeathSound()
    {
        return getPhase() == 3 && getHealth() <= 0 ? Sounds.ENTITY_DEATH.get() : null;
    }

    @Override
    public SoundEvent getHurtSound(@NotNull DamageSource source)
    {
        return SoundEvents.SLIME_BLOCK_PLACE;
    }

    @Override
    public void defineSynchedData()
    {
        super.defineSynchedData();
        entityData.define(PHASE, 1);
        entityData.define(CHARGING, false);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag)
    {
        super.readAdditionalSaveData(tag);
        setPhase(tag.getInt("Phase"));
        setCharging(tag.getBoolean("Charging"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag)
    {
        super.addAdditionalSaveData(tag);
        tag.putInt("Phase", getPhase());
        tag.putBoolean("Charging", getCharging());
    }

    @Override
    public void customServerAiStep()
    {
        bossEvent.setProgress(getHealth() / getMaxHealth());
    }

    @Override
    public boolean isAdaptive()
    {
        return true;
    }
}