package xyz.snaker.tq.level.entity.mob;

import java.util.function.Predicate;

import xyz.snaker.snakerlib.level.entity.FlyingHostile;
import xyz.snaker.snakerlib.math.Maths;
import xyz.snaker.tq.level.entity.Comatosian;
import xyz.snaker.tq.level.entity.projectile.CosmicRay;
import xyz.snaker.tq.level.world.EntitySpawner;
import xyz.snaker.tq.rego.Entities;
import xyz.snaker.tq.rego.Sounds;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class Snipe extends FlyingHostile implements Comatosian
{
    private final Predicate<LivingEntity> distance = entity -> Math.abs(entity.getY() - getY()) <= 4;

    public Snipe(EntityType<? extends FlyingHostile> type, Level level)
    {
        super(type, level);
    }

    public static boolean spawnRules(EntityType<Snipe> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random)
    {
        return EntitySpawner.COMATOSE.check(level, pos, random, 75);
    }

    public static AttributeSupplier attributes()
    {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.ATTACK_SPEED, 1)
                .add(Attributes.FOLLOW_RANGE, 64)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FLYING_SPEED, 0.25).build();
    }

    @Override
    public void registerGoals()
    {
        super.registerGoals();
        goalSelector.addGoal(4, new SnipeAttackGoal(this, 20, 4, 0));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 1, true, false, distance));
        targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Cosmo.class, true));
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
    public int getMaxHeadXRot()
    {
        return 500;
    }

    @Override
    public @NotNull SoundEvent getAmbientSound()
    {
        return Sounds.SNIPE_AMBIENT.get();
    }

    @Override
    public SoundEvent getHurtSound(@NotNull DamageSource source)
    {
        return Sounds.SNIPE_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound()
    {
        return Sounds.ENTITY_DEATH.get();
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

    static class SnipeAttackGoal extends Goal
    {
        private final Snipe snipe;
        private int delay;
        private float velocity;
        private float inaccuracy;

        public SnipeAttackGoal(Snipe snipe, int delay, float velocity, float inaccuracy)
        {
            this.snipe = snipe;
            this.delay = delay;
            this.velocity = velocity;
            this.inaccuracy = inaccuracy;
        }

        public boolean canUse()
        {
            return snipe.getTarget() != null;
        }

        public boolean requiresUpdateEveryTick()
        {
            return true;
        }

        public void tick()
        {
            LivingEntity target = snipe.getTarget();
            RandomSource random = RandomSource.create();

            if (target != null) {
                snipe.getLookControl().setLookAt(target, 10, snipe.getMaxHeadXRot());

                if (target.distanceToSqr(snipe) < 4096 && snipe.hasLineOfSight(target)) {
                    Level level = snipe.level();

                    double x = target.getX() - snipe.getX();
                    double y = target.getY() - snipe.getY();
                    double z = target.getZ() - snipe.getZ();

                    snipe.setXRot(Maths.rotateTowards(y, (x * x + z * z)));
                    snipe.xRotO = snipe.getXRot();

                    if (snipe.tickCount % delay == 0) {
                        CosmicRay ray = new CosmicRay(Entities.COSMIC_RAY.get(), snipe, level);
                        Vector3d xyz = new Vector3d(x, y, z);

                        ray.shoot(xyz.x, xyz.y, xyz.z, velocity, inaccuracy);
                        level.addFreshEntity(ray);
                        level.playSound(null, target.getX(), target.getY(), target.getZ(), Sounds.BULLET.get(), SoundSource.BLOCKS, 0.5F, (random.nextFloat() - random.nextFloat()) * 0.5F + 1);
                    }
                }
            }
        }
    }
}