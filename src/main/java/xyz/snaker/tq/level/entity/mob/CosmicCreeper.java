package xyz.snaker.tq.level.entity.mob;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

import xyz.snaker.snakerlib.data.DefaultEntityAttributes;
import xyz.snaker.snakerlib.math.Maths;
import xyz.snaker.snakerlib.utility.TriBool;
import xyz.snaker.snakerlib.utility.tools.EntityStuff;
import xyz.snaker.tq.level.entity.Comatosian;
import xyz.snaker.tq.utility.WorldGenStuff;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkHooks;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 16/03/2023
 **/
public class CosmicCreeper extends Monster implements Comatosian, PowerableMob
{
    public static final EntityDataAccessor<Integer> SWELL_DIRECTION = SynchedEntityData.defineId(CosmicCreeper.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> POWERED = SynchedEntityData.defineId(CosmicCreeper.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> IGNITED = SynchedEntityData.defineId(CosmicCreeper.class, EntityDataSerializers.BOOLEAN);

    private int oldSwell;
    private int swell;
    private short maxSwell = 30;
    private byte explosionRadius = 3;
    private int droppedSkulls;
    private int teleportTime;

    private final int targetScanRadius = 6;

    public CosmicCreeper(EntityType<? extends Monster> type, Level level)
    {
        super(type, level);
        this.moveControl = new MoveCtrl(this);
    }

    public static boolean spawnRules(EntityType<CosmicCreeper> type, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random)
    {
        return WorldGenStuff.checkComatoseSpawnRules(level, random);
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
    public int getMaxFallDistance()
    {
        return getTarget() == null ? 3 : 3 + (int) getHealth() - 1;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source)
    {
        boolean fallDamage = super.causeFallDamage(fallDistance, multiplier, source);

        swell += (int) (fallDistance * 1.5F);

        if (swell > maxSwell - 5) {
            swell = maxSwell - 5;
        }

        return fallDamage;
    }

    @Override
    public void defineSynchedData()
    {
        super.defineSynchedData();

        entityData.define(SWELL_DIRECTION, -1);
        entityData.define(POWERED, false);
        entityData.define(IGNITED, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag)
    {
        super.addAdditionalSaveData(tag);

        if (entityData.get(POWERED)) {
            tag.putBoolean("Powered", true);
        }

        tag.putShort("Fuse", maxSwell);
        tag.putByte("ExplosionRadius", explosionRadius);
        tag.putBoolean("Ignited", isIgnited());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag)
    {
        super.readAdditionalSaveData(tag);

        entityData.set(POWERED, tag.getBoolean("Powered"));

        if (tag.contains("Fuse", 99)) {
            maxSwell = tag.getShort("Fuse");
        }

        if (tag.contains("ExplosionRadius", 99)) {
            explosionRadius = tag.getByte("ExplosionRadius");
        }

        if (tag.getBoolean("Ignited")) {
            ignite();
        }
    }

    @Override
    public void registerGoals()
    {
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(2, new SwellGoal(this));
        goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Ocelot.class, 6, 1, 1.2));
        goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Cat.class, 6, 1, 1.2));
        goalSelector.addGoal(4, new MeleeAttackGoal(this, 1, false));
        goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    @Override
    public void setTarget(@Nullable LivingEntity target)
    {
        if (!(target instanceof Goat)) {
            super.setTarget(target);
        }
    }

    @Override
    public void dropCustomDeathLoot(@NotNull DamageSource source, int looting, boolean recentlyHit)
    {
        super.dropCustomDeathLoot(source, looting, recentlyHit);

        Entity entity = source.getEntity();

        if (entity instanceof CosmicCreeper creeper) {
            if (creeper.canDropMobsSkull()) {
                creeper.increaseDroppedSkulls();
                spawnAtLocation(Items.CREEPER_HEAD);
            }
        }
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity)
    {
        return true;
    }

    @Override
    public boolean isPowered()
    {
        return entityData.get(POWERED);
    }

    public float getSwelling(float partialTicks)
    {
        return Mth.lerp(partialTicks, (float) oldSwell, (float) swell) / (float) (maxSwell - 2);
    }

    public int getSwellDir()
    {
        return entityData.get(SWELL_DIRECTION);
    }

    public void setSwellDir(int state)
    {
        entityData.set(SWELL_DIRECTION, state);
    }

    @Override
    public void thunderHit(@NotNull ServerLevel level, @NotNull LightningBolt bolt)
    {
        super.thunderHit(level, bolt);

        entityData.set(POWERED, true);
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(ItemTags.CREEPER_IGNITERS)) {
            SoundEvent event = stack.is(Items.FIRE_CHARGE) ? SoundEvents.FIRECHARGE_USE : SoundEvents.FLINTANDSTEEL_USE;

            level().playSound(player, getX(), getY(), getZ(), event, getSoundSource(), 1, random.nextFloat() * 0.4F + 0.8F);

            if (!level().isClientSide) {
                ignite();

                if (!stack.isDamageableItem()) {
                    stack.shrink(1);
                } else {
                    stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                }
            }

            return InteractionResult.sidedSuccess(level().isClientSide);
        } else {
            return super.mobInteract(player, hand);
        }
    }

    public void explodeCreeper()
    {
        if (!level().isClientSide) {
            double x = getX();
            double y = getY();
            double z = getZ();

            double randX = getX() + random.nextInt(20) - 10;
            double randY = getY() + random.nextInt(5);
            double randZ = getZ() + random.nextInt(20) - 10;

            BlockPos exact = new BlockPos.MutableBlockPos(x, y, z);
            BlockPos pos = new BlockPos.MutableBlockPos(randX, randY, randZ);

            AABB aabb = new AABB(exact).inflate(targetScanRadius);
            AreaEffectCloud cloud = new AreaEffectCloud(level(), exact.getX(), exact.getY(), exact.getZ());
            List<LivingEntity> targets = level().getEntitiesOfClass(LivingEntity.class, aabb);

            cloud.setParticle(ParticleTypes.DRAGON_BREATH);
            cloud.setRadius(2);
            cloud.setDuration(200);
            cloud.setRadiusPerTick((targetScanRadius - cloud.getRadius()) / cloud.getDuration());
            cloud.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 1));

            if (!targets.isEmpty()) {
                for (LivingEntity target : targets) {
                    target.randomTeleport(pos.getX(), pos.getY(), pos.getZ(), true);
                    level().explode(this, pos.getX(), pos.getY(), pos.getZ(), targetScanRadius, Level.ExplosionInteraction.NONE);
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

    public void spawnLingeringCloud()
    {
        Collection<MobEffectInstance> activeEffects = getActiveEffects();

        if (!activeEffects.isEmpty()) {
            AreaEffectCloud cloud = new AreaEffectCloud(level(), getX(), getY(), getZ());

            cloud.setRadius(2.5F);
            cloud.setRadiusOnUse(-0.5F);
            cloud.setWaitTime(10);
            cloud.setDuration(cloud.getDuration() / 2);
            cloud.setRadiusPerTick(-cloud.getRadius() / cloud.getDuration());

            for (MobEffectInstance effectInstance : activeEffects) {
                cloud.addEffect(new MobEffectInstance(effectInstance));
            }

            level().addFreshEntity(cloud);
        }
    }

    public boolean isIgnited()
    {
        return entityData.get(IGNITED);
    }

    public void ignite()
    {
        entityData.set(IGNITED, true);
    }

    public boolean canDropMobsSkull()
    {
        return isPowered() && droppedSkulls < 1;
    }

    public void increaseDroppedSkulls()
    {
        droppedSkulls++;
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
                double x = getRandomX(random.nextInt(targetScanRadius, (targetScanRadius * 2)) * Maths.clamp(random.nextDouble(), 0.875, 3.475)) - 0.5;
                double y = getY();
                double z = getRandomZ(random.nextInt(targetScanRadius, (targetScanRadius * 2)) * Maths.clamp(random.nextDouble(), 0.875, 3.475)) - 0.5;

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

        if (isAlive()) {
            oldSwell = swell;

            if (isIgnited()) {
                setSwellDir(1);
            }

            int i = getSwellDir();

            if (i > 0 && swell == 0) {
                playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
                gameEvent(GameEvent.PRIME_FUSE);
            }

            swell += i;

            if (swell < 0) {
                swell = 0;
            }

            if (swell >= maxSwell) {
                swell = maxSwell;
                explodeCreeper();
            }
        }

        super.tick();
    }

    public void randomTeleport(double x, double y, double z)
    {
        randomTeleport(x, y, z, true);

        level().playSound(null, xo, yo, zo, SoundEvents.FOX_TELEPORT, getSoundSource(), 1, 1);
    }

    public void teleportTargetToCreeper()
    {
        if (!level().isClientSide) {
            BlockPos pos = new BlockPos((int) getX(), (int) getY(), (int) getZ());
            AABB aabb = new AABB(pos).inflate(targetScanRadius);
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

    public void markDead(boolean isDead)
    {
        dead = isDead;
    }

    public void setDead(boolean isDead, DamageSource source)
    {
        if (isDead) {
            super.kill();
            super.die(source);
            markDead(true);
        }
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public TriBool hasSpecialRendering()
    {
        return TriBool.MAYBE;
    }

    @Override
    public TriBool isAdaptive()
    {
        return TriBool.YES;
    }

    static class SwellGoal extends Goal
    {
        private final CosmicCreeper owner;
        private LivingEntity target;

        public SwellGoal(CosmicCreeper owner)
        {
            this.owner = owner;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse()
        {
            LivingEntity entity = owner.getTarget();
            return owner.getSwellDir() > 0 || entity != null && owner.distanceToSqr(entity) < 9;
        }

        @Override
        public void start()
        {
            owner.getNavigation().stop();
            target = owner.getTarget();
        }

        @Override
        public void stop()
        {
            target = null;
        }

        @Override
        public boolean requiresUpdateEveryTick()
        {
            return true;
        }

        @Override
        public void tick()
        {
            if (target == null) {
                owner.setSwellDir(-1);
            } else if (owner.distanceToSqr(target) > 49) {
                owner.setSwellDir(-1);
            } else if (!owner.getSensing().hasLineOfSight(target)) {
                owner.setSwellDir(-1);
            } else {
                owner.setSwellDir(1);
            }
        }
    }

    static class MoveCtrl extends MoveControl
    {
        final CosmicCreeper owner;

        public MoveCtrl(CosmicCreeper owner)
        {
            super(owner);
            this.owner = owner;
        }

        @Override
        public void tick()
        {
            if (owner.getTarget() != null) {
                speedModifier = DefaultEntityAttributes.SPEED_MODIFIER;
            }
            super.tick();
        }
    }
}
