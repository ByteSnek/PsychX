package xyz.snaker.tq.level.entity.mob;

import java.util.List;
import java.util.function.Predicate;

import xyz.snaker.snakerlib.data.SnakerConstants;
import xyz.snaker.snakerlib.math.Maths;
import xyz.snaker.snakerlib.utility.tools.EntityStuff;
import xyz.snaker.snakerlib.utility.tools.WorldStuff;
import xyz.snaker.tq.rego.Keys;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkHooks;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 16/03/2023
 **/
public class CosmicCreeper extends Creeper
{
    private int teleportTime;
    private final int radius = 6;

    public CosmicCreeper(EntityType<? extends Creeper> type, Level level)
    {
        super(type, level);
        moveControl = new MoveCtrl(this);
    }

    public static boolean spawnRules(EntityType<CosmicCreeper> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random)
    {
        return (WorldStuff.isDimension(level, Level.OVERWORLD) && random.nextInt(64) == 0 && checkMonsterSpawnRules(type, level, reason, pos, random)) || WorldStuff.isDimension(level, Keys.COMATOSE) || WorldStuff.isDimension(level, Level.END);
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

    @Override
    public void tick()
    {
        Predicate<ServerPlayer> isPlayerCreative = ServerPlayer::isCreative;
        LivingEntity target = getTarget();
        LivingEntity lastHurtByMob = getLastHurtByMob();
        LookControl lookCtrl = getLookControl();
        MoveControl moveCtrl = getMoveControl();
        if (target == null) {
            teleportTime++;
            if (teleportTime >= random.nextInt(80, 440) && !EntityStuff.isEntityMovingXZ(this)) {
                double x = getRandomX(random.nextInt(radius, (radius * 2)) * Maths.clamp(random.nextDouble(), 0.875, 3.475)) - 0.5;
                double y = getY();
                double z = getRandomZ(random.nextInt(radius, (radius * 2)) * Maths.clamp(random.nextDouble(), 0.875, 3.475)) - 0.5;
                randomTeleport(x, y, z);
                teleportTime = 0;
            }
        } else if (target instanceof ServerPlayer player) {
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            double speed = getSpeed();
            moveCtrl.setWantedPosition(x, y, z, speed);
            lookCtrl.setLookAt(player);
            if (hasLineOfSight(player) && distanceToSqr(player) < 256) {
                teleportTargetToCreeper();
            }
        }
        if (lastHurtByMob instanceof ServerPlayer player) {
            if (!isPlayerCreative.test(player)) {
                setTarget(player);
            }
        }
        if (swell > 0) {
            if (target instanceof ServerPlayer) {
                if (!isPlayerCreative.test((ServerPlayer) target) || lastHurtByMob != null) {
                    explodeCreeper();
                }
            }
        }
        super.tick();
    }

    private void randomTeleport(double x, double y, double z)
    {
        randomTeleport(x, y, z, true);
        level().playSound(null, xo, yo, zo, SoundEvents.FOX_TELEPORT, getSoundSource(), 1, 1);
    }

    private void teleportTargetToCreeper()
    {
        if (!level().isClientSide) {
            BlockPos pos = new BlockPos((int) getX(), (int) getY(), (int) getZ());
            AABB aabb = new AABB(pos).inflate(radius);
            List<LivingEntity> targets = level().getEntitiesOfClass(LivingEntity.class, aabb);
            if (!targets.isEmpty()) {
                for (LivingEntity target : targets) {
                    double x = getX();
                    double y = getY();
                    double z = getZ();
                    if (target instanceof ServerPlayer) {
                        if (!((ServerPlayer) target).isCreative()) {
                            target.teleportTo(x, y, z);
                        }
                    } else {
                        target.teleportTo(x, y, z);
                    }
                }
            }
        }
    }

    private void markDead(boolean isDead)
    {
        dead = isDead;
    }

    private void setDead(boolean isDead, DamageSource source)
    {
        if (isDead) {
            super.kill();
            super.die(source);
            markDead(true);
        }
    }

    @Override
    public void explodeCreeper()
    {
        if (!level().isClientSide) {
            double x = getX();
            double y = getY();
            double z = getZ();
            double randX = getX() + random.nextInt(20) - 10;
            double randY = getY() + random.nextInt(5);
            double randZ = getZ() + random.nextInt(20) - 10;
            BlockPos exact = new BlockPos((int) x, (int) y, (int) z);
            BlockPos pos = new BlockPos((int) randX, (int) randY, (int) randZ);
            AABB aabb = new AABB(exact).inflate(radius);
            AreaEffectCloud cloud = new AreaEffectCloud(level(), exact.getX(), exact.getY(), exact.getZ());
            List<LivingEntity> targets = level().getEntitiesOfClass(LivingEntity.class, aabb);
            cloud.setParticle(ParticleTypes.DRAGON_BREATH);
            cloud.setRadius(2);
            cloud.setDuration(200);
            cloud.setRadiusPerTick((radius - cloud.getRadius()) / cloud.getDuration());
            cloud.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 1));
            if (!targets.isEmpty()) {
                for (LivingEntity target : targets) {
                    target.randomTeleport(pos.getX(), pos.getY(), pos.getZ(), true);
                    level().explode(this, pos.getX(), pos.getY(), pos.getZ(), radius, Level.ExplosionInteraction.NONE);
                    level().playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.FOX_TELEPORT, getSoundSource(), 1, 1);
                    cloud.setPos(target.getX(), target.getY(), target.getZ());
                    level().addFreshEntity(cloud);
                }
            }
            setDead(true, level().damageSources().explosion(null));
            discard();
            spawnLingeringCloud();
        }
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    static class MoveCtrl extends MoveControl
    {
        private final CosmicCreeper entity;

        public MoveCtrl(CosmicCreeper creeper)
        {
            super(creeper);
            entity = creeper;
        }

        @Override
        public void tick()
        {
            if (entity.getTarget() != null) {
                speedModifier = SnakerConstants.EntityAttributes.SPEED_MODIFIER;
            }
            super.tick();
        }
    }
}
